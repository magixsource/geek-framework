package gl.linpeng.gf.model;

import gl.linpeng.gf.base.ServerlessRequest;

import javax.validation.constraints.NotNull;

/**
 * @author lin.peng
 * @since 1.0
 **/
public class BadRequestServerlessDTO extends ServerlessRequest {

    @NotNull
    private String append;

    public String getAppend() {
        return append;
    }

    public void setAppend(String append) {
        this.append = append;
    }

}
