package gl.linpeng.gf.translator;

import com.alibaba.fastjson.JSON;
import gl.linpeng.gf.base.ServerlessDTO;
import gl.linpeng.gf.base.ServerlessRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Json serverless request translator
 * translate request body to json
 *
 * @author lin.peng
 * @since 1.0
 **/
public class JsonServerlessRequestTranslator<T extends ServerlessDTO> implements ServerlessRequestTranslator {

    public static final Logger LOGGER = LoggerFactory.getLogger(JsonServerlessRequestTranslator.class);

    // tClass
    private Class tClass;

    @Override
    public T translate(ServerlessRequest request) {
        LOGGER.debug("Request {}", JSON.toJSONString(request));
        // translate body to dto
        String body = request.getBody();
        return (T) JSON.parseObject(body, tClass);
    }

    public Class gettClass() {
        return tClass;
    }

    public void settClass(Class tClass) {
        this.tClass = tClass;
    }
}
