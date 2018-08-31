package gl.linpeng.gf;

import gl.linpeng.gf.config.FunctionConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Function single instance
 *
 * @author lin.peng
 * @since 1.0
 **/
public class Function {

    /**
     * Function context wrapper
     */
    private Map<String, Object> functionContext;

    /**
     * Function config
     */
    private FunctionConfig config;

    public Function() {
        super();
        this.config = new FunctionConfig();
    }

    /**
     * determine is function run in dev mode
     *
     * @return true if run in dev mode
     */
    public boolean isDev() {
        return this.config.isDev();
    }

    public Map<String, Object> getFunctionContext() {
        if(functionContext == null){
            functionContext = new HashMap<>();
        }
        return functionContext;
    }

    public void setFunctionContext(Map<String, Object> functionContext) {
        this.functionContext = functionContext;
    }

    public FunctionConfig getConfig() {
        return config;
    }

    public void setConfig(FunctionConfig config) {
        this.config = config;
    }
}
