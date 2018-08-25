package gl.linpeng.gf.base;

import com.alibaba.fastjson.JSON;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * Serverless response
 *
 * @author lin.peng
 * @since 1.0
 **/
public class ServerlessResponse {

    private final int statusCode;
    private final String body;
    private final Map<String, String> headers;
    private final boolean isBase64Encoded;

    public ServerlessResponse(int statusCode, String body, Map<String, String> headers, boolean isBase64Encoded) {
        this.statusCode = statusCode;
        this.body = body;
        this.headers = headers;
        this.isBase64Encoded = isBase64Encoded;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getBody() {
        return body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    // API Gateway expects the property to be called "isBase64Encoded" => isIs
    public boolean isIsBase64Encoded() {
        return isBase64Encoded;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        // private static final Loggerger LOG = LogManager.getLogger(ServerlessResponse.Builder.class);

        private int statusCode = 200;
        private Map<String, String> headers =  new HashMap();
        private String rawBody;
        private Object objectBody;
        private byte[] binaryBody;
        private boolean base64Encoded;

        public Builder setStatusCode(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public Builder setHeaders(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        /**
         * Builds the {@link ServerlessResponse} using the passed raw body string.
         */
        public Builder setRawBody(String rawBody) {
            this.rawBody = rawBody;
            return this;
        }

        /**
         * Builds the {@link ServerlessResponse} using the passed object body
         * converted to JSON.
         */
        public Builder setObjectBody(Object objectBody) {
            this.objectBody = objectBody;
            return this;
        }

        /**
         * Builds the {@link ServerlessResponse} using the passed binary body
         * encoded as base64. {@link #setBase64Encoded(boolean)
         * setBase64Encoded(true)} will be in invoked automatically.
         */
        public Builder setBinaryBody(byte[] binaryBody) {
            this.binaryBody = binaryBody;
            setBase64Encoded(true);
            return this;
        }

        /**
         * A binary or rather a base64encoded responses requires
         * <ol>
         * <li>"Binary Media Types" to be configured in API Gateway
         * <li>a request with an "Accept" header set to one of the "Binary Media
         * Types"
         * </ol>
         */
        public Builder setBase64Encoded(boolean base64Encoded) {
            this.base64Encoded = base64Encoded;
            return this;
        }

        public ServerlessResponse build() {
            String body = null;
            if (rawBody != null) {
                body = rawBody;
            } else if (objectBody != null) {
                body = JSON.toJSONString(objectBody, false);
            } else if (binaryBody != null) {
                body = new String(Base64.getEncoder().encode(binaryBody), StandardCharsets.UTF_8);
            }
            return new ServerlessResponse(statusCode, body, headers, base64Encoded);
        }
    }
}
