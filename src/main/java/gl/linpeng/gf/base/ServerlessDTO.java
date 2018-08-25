package gl.linpeng.gf.base;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Serverless Data Translate Object
 *
 * @author lin.peng
 * @since 1.0
 **/
public class ServerlessDTO implements Serializable {
    private String rawBody;
    private String content;

    public String getRawBody() {
        return rawBody;
    }

    public void setRawBody(String rawBody) {
        this.rawBody = rawBody;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
