package gl.linpeng.gf.translator;

import com.alibaba.fastjson.JSON;
import gl.linpeng.gf.base.ServerlessRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * translate serverless request body to target object in JSON format
 *
 * @author lin.peng
 * @since 1.0
 **/
public class JsonServerlessRequestTranslator<T extends Object> implements ServerlessRequestTranslator {
    public static final Logger LOGGER = LoggerFactory.getLogger(JsonServerlessRequestTranslator.class);
    // tClass
    private Class tClass;

    @Override
    public T translate(ServerlessRequest request) {
        return (T) JSON.parseObject(request.getBody(), tClass);
    }

    public Class gettClass() {
        return tClass;
    }

    public void settClass(Class tClass) {
        this.tClass = tClass;
    }
}
