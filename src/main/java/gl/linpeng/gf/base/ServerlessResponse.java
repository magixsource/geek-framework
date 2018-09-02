package gl.linpeng.gf.base;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

/**
 * Serverless response
 *
 * @author lin.peng
 * @since 1.0
 **/
public class ServerlessResponse implements Serializable {

    private Map<String, Object> errors = Collections.emptyMap();


    public Map<String, Object> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, Object> errors) {
        this.errors = errors;
    }
}
