package gl.linpeng.gf.base.api;

import com.alibaba.fastjson.JSON;
import gl.linpeng.gf.base.ServerlessResponse;

import java.util.Map;

/**
 * @author lin.peng
 * @since 1.0
 **/
public class ApiResponse {
    private Map headers;
    private boolean isBase64Encoded;
    private int statusCode;
    private String body;

    public ApiResponse(Map headers, boolean isBase64Encoded, int statusCode, String body) {
        this.headers = headers;
        this.isBase64Encoded = isBase64Encoded;
        this.statusCode = statusCode;
        this.body = body;
    }

    public ApiResponse(ServerlessResponse serverlessResponse) {
        this.headers = serverlessResponse.getHeaders();
        this.isBase64Encoded = serverlessResponse.isIsBase64Encoded();
        this.statusCode = serverlessResponse.getStatusCode();
        if (serverlessResponse.getErrors() != null && serverlessResponse.getErrors().isEmpty() == false) {
            this.body = JSON.toJSONString(serverlessResponse.getErrors());
        } else {
            this.body = serverlessResponse.getBody();
        }
    }

    public Map getHeaders() {
        return headers;
    }

    public void setHeaders(Map headers) {
        this.headers = headers;
    }

    public boolean getIsBase64Encoded() {
        return isBase64Encoded;
    }

    public void setIsBase64Encoded(boolean base64Encoded) {
        this.isBase64Encoded = base64Encoded;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
