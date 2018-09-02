package gl.linpeng.gf.model;

import gl.linpeng.gf.base.ServerlessRequest;

/**
 * @author lin.peng
 * @since 1.0
 **/
public class SimpleRequest extends ServerlessRequest {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
