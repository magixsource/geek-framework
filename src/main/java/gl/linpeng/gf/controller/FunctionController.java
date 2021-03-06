package gl.linpeng.gf.controller;

import com.alibaba.fastjson.JSON;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import gl.linpeng.gf.C;
import gl.linpeng.gf.Function;
import gl.linpeng.gf.annotation.JsonRequest;
import gl.linpeng.gf.annotation.NoValidate;
import gl.linpeng.gf.annotation.PlainTextRequest;
import gl.linpeng.gf.base.ServerlessRequest;
import gl.linpeng.gf.base.ServerlessResponse;
import gl.linpeng.gf.config.FunctionConfig;
import gl.linpeng.gf.config.FunctionConfigPlugin;
import gl.linpeng.gf.config.FunctionDIConfig;
import gl.linpeng.gf.plugin.PluginManager;
import gl.linpeng.gf.translator.JsonServerlessRequestTranslator;
import gl.linpeng.gf.translator.ServerlessRequestTranslator;
import gl.linpeng.gf.utils.DateTimeUtil;
import gl.linpeng.gf.validation.FunctionValidatorFactory;
import org.apache.bval.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Function controller
 *
 * @author lin.peng
 * @since 1.0
 **/
@JsonRequest
public abstract class FunctionController<T extends Object, Q extends ServerlessRequest, R extends ServerlessResponse> {

    public static final Logger logger = LoggerFactory.getLogger(FunctionController.class);

    /**
     * Function
     */
    private Function function;

    /**
     * Bean validator
     */
    private Validator validator;
    /**
     * DI Injector
     */
    private Injector injector;

    private AbstractModule diModule;

    /**
     * Plugin Manager
     */
    private PluginManager pluginManager;

    /**
     * request translator
     */
    private ServerlessRequestTranslator translator;

    private T dto;

    /**
     * Determine is Function init
     *
     * @return true if function isn't null
     */
    public boolean isFunctionInit() {
        return this.function == null;
    }

    /**
     * Function controller construct: it's work while subclass handle method invoked by function compute API gateway
     * construct try to init function context and keep function works
     */
    public FunctionController() {
        if (isFunctionInit()) {
            initFunction();
        }
        Class tClass = getTClass();
        logger.debug("tClass {} ", tClass);

        if (this.getClass().isAnnotationPresent(PlainTextRequest.class)) {
        } else if (this.getClass().isAnnotationPresent(JsonRequest.class)) {
            this.translator = new JsonServerlessRequestTranslator<T>();
            ((JsonServerlessRequestTranslator) this.translator).settClass(tClass);
        } else {
            throw new UnsupportedOperationException("Only JSON & PlainText request supported,sorry.");
        }
    }

    /**
     * Get generic T class
     *
     * @return T class
     */
    public Class<T> getTClass() {
        Class<T> tClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return tClass;
    }

    public Class<R> getRClass() {
        Class<R> rClass = (Class<R>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[2];
        return rClass;
    }

    /**
     * Function initial
     * init function context
     */
    private void initFunction() {
        // show slogan
        // this.showSlogan();
        // init function
        this.function = new Function();

        // init bean validator
        this.validator = FunctionValidatorFactory.SINGLE_INSTANCE.getValidator();
        // init config
        FunctionConfigPlugin configPlugin = new FunctionConfigPlugin();
        configPlugin.scan();
        configPlugin.mergeTo(this.function.getConfig());

        // init di
        diModule = setDIModule();
        if (diModule == null) {
            diModule = new FunctionDIConfig();
        }
        this.injector = Guice.createInjector(diModule);

        // plugins
        pluginManager = new PluginManager();
        String plugins = this.getFunction().getConfig().getPlugins();
        if (!StringUtils.isBlank(plugins)) {
            // load plugin needed
            pluginManager.loadPlugin(plugins);
        }

    }

    private void showSlogan() {
        String version = getClass().getPackage().getImplementationVersion();
        System.out.println("   _____        __     ____                                   __  ");
        System.out.println("  / ___/__ ___ / /__  / __/______ ___ _  ___ _    _____  ____/ /__");
        System.out.println(" / (_ / -_) -_)  '_/ / _// __/ _ `/  ' \\/ -_) |/|/ / _ \\/ __/  '_/");
        System.out.println(" \\___/\\__/\\__/_/\\_\\ /_/ /_/  \\_,_/_/_/_/\\__/|__,__/\\___/_/ /_/\\_\\ ");
        System.out.println(" :: A lightweight framework focus on function compute :: ("+version+")");
    }

    /**
     * Set DIModule to create injector
     * You need override this method to setup your di plugin
     *
     * @return abstract di plugin
     */
    protected AbstractModule setDIModule() {
        return new FunctionDIConfig();
    }


    /**
     * Function Common handle pipeline
     *
     * @param request serverless request
     * @return serverless response
     */
    public R handler(Q request) {
        if (dto == null) {
            dto = (T) this.translator.translate(request);
        }
        //validation jsr303
        Set<ConstraintViolation<T>> validateMessages = validation(dto);
        R response = null;

        if (isValid(validateMessages)) {
            response = internalHandle(dto);
        } else {
            Map<String, Object> errors = Collections.emptyMap();
            if (validateMessages != null && !validateMessages.isEmpty()) {
                errors = new HashMap<>(validateMessages.size());
                for (ConstraintViolation<T> c : validateMessages) {
                    errors.put(c.getPropertyPath().toString(), c.getMessage());
                }
            }
            Map<String, Object> payload = new HashMap<>(1);
            payload.put("errors", errors);
            try {
                response = getRClass().newInstance();
                response.setErrors(payload);
                response.setStatusCode(C.Http.StatusCode.BAD_REQUEST.v());
                response.setBase64Encoded(false);
                response.setErrors(errors);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            // response = ServerlessResponse.builder().setStatusCode(C.Http.StatusCode.BAD_REQUEST.v()).setObjectBody(payload).build();
        }
        wrapper(response);
        return response;
    }

    /**
     * Determine is validate ok
     *
     * @param validateMessages validate message
     * @return true if validate pass
     */
    private boolean isValid(Set<ConstraintViolation<T>> validateMessages) {
        return validateMessages == null || validateMessages.isEmpty();
    }

    /**
     * JSR303 bean Validate
     *
     * @param dto validate dto
     * @return validate result
     */
    private Set<ConstraintViolation<T>> validation(T dto) {
        if (this.getClass().isAnnotationPresent(NoValidate.class)) {
            logger.debug("novalidate enabled.");
            return null;
        }
        Set<ConstraintViolation<T>> validateMessages = this.validator.validate(dto);
        for (ConstraintViolation<T> r : validateMessages) {
            logger.debug("validation result {},{}", r.getPropertyPath(), r.getMessage());
        }
        return validateMessages;
    }

    /**
     * Auto enhance response object
     *
     * @param response serverless response
     */
    private void wrapper(ServerlessResponse response) {
        Map<String, String> headers = response.getHeaders();
        if (headers == null) {
            response.setHeaders(new HashMap());
        }
        logger.debug("response {}", JSON.toJSONString(response, false));

        // enhance content type
        warpperContentType(headers);
        // enhance  cors
        warpperCors(headers);
        warpperTimestamp(headers);

    }

    /**
     * add cors config to response headers
     *
     * @param headers http headers
     */
    private void warpperCors(Map<String, String> headers) {
        FunctionConfig.Cors cors = this.getFunction().getConfig().getCors();
        if (!cors.isEnabled()) {
            return;
        }

        if (!StringUtils.isBlank(cors.origin)) {
            headers.put("Access-Control-Allow-Origin", cors.origin.trim());
        }
        if (!StringUtils.isBlank(cors.methods)) {
            headers.put("Access-Control-Allow-Methods", cors.methods.trim());
        }
        if (!StringUtils.isBlank(cors.headers)) {
            headers.put("Access-Control-Allow-Headers", cors.headers.trim());
        }
    }

    /**
     * add timestamp to response headers
     *
     * @param headers http headers
     */
    private void warpperTimestamp(Map<String, String> headers) {
        headers.put(C.Http.TIMESTAMP, DateTimeUtil.nowTimeStamp().toString());
    }

    /**
     * Auto add content type into headers determine by request annotation
     *
     * @param headers serverless response headers
     */
    private void warpperContentType(Map<String, String> headers) {
        if (this.getClass().isAnnotationPresent(PlainTextRequest.class)) {
            headers.put(C.Http.contentType, chartsetWarpper(C.Http.ContentType.TEXT, FunctionConfig.Charset));
        } else {
            headers.put(C.Http.contentType, chartsetWarpper(C.Http.ContentType.JSON, FunctionConfig.Charset));
        }
    }

    /**
     * Append charset to headers
     *
     * @param body   header body
     * @param append charset
     * @return header body appended charset
     */
    private String chartsetWarpper(String body, String append) {
        return body.concat(";charset=").concat(append);
    }

    /**
     * Get function object
     *
     * @return function object
     */
    public Function getFunction() {
        return this.function;
    }


    public Injector getInjector() {
        return injector;
    }

    public void setInjector(Injector injector) {
        this.injector = injector;
    }

    /**
     * internal controller handler
     *
     * @param dto serverless data translate object
     * @return serverless response object
     */
    public abstract R internalHandle(T dto);
}
