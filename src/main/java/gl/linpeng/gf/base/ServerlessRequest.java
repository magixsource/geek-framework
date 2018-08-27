package gl.linpeng.gf.base;

import com.alibaba.fastjson.JSON;

import java.util.Collections;
import java.util.Map;

/**
 * Serverless Request object
 *
 * @author lin.peng
 * @since 1.0
 **/
public class ServerlessRequest {

    private final String body;
    private final Map<String, String> headers;
    private final Object objectBody;


    public ServerlessRequest(String body, Map<String, String> headers, Object objectBody) {
        this.body = body;
        this.headers = headers;
        this.objectBody = objectBody;
    }

    public String getBody() {
        return body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Object getObjectBody() {
        return objectBody;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Map<String, String> headers = Collections.emptyMap();
        private String rawBody;
        private Object objectBody;


        public Builder setHeaders(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        /**
         * Builds the {@link ServerlessRequest} using the passed raw body string.
         */
        public Builder setRawBody(String rawBody) {
            this.rawBody = rawBody;
            return this;
        }

        /**
         * Builds the {@link ServerlessRequest} using the passed object body
         * converted to JSON.
         */
        public Builder setObjectBody(Object objectBody) {
            this.objectBody = objectBody;
            return this;
        }

        public ServerlessRequest build() {
            String body = null;
            if (rawBody != null) {
                body = rawBody;
            } else if (objectBody != null) {
                body = JSON.toJSONString(objectBody);
            }
            return new ServerlessRequest(body, headers, objectBody);
        }
    }

}
