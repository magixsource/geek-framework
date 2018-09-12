package gl.linpeng.gf.base;

import java.util.Map;

/**
 * Payload response
 *
 * @author lin.peng
 */
public class PayloadResponse {

    private final String message;
    private final Map<String, Object> payload;

    public PayloadResponse(String message, Map<String, Object> payload) {
        this.message = message;
        this.payload = payload;
    }

    public String getMessage() {
        return this.message;
    }

    public Map<String, Object> getPayload() {
        return this.payload;
    }

}
