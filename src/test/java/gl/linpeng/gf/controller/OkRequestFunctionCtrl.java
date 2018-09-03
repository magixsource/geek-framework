package gl.linpeng.gf.controller;

import gl.linpeng.gf.base.PayloadResponse;
import gl.linpeng.gf.model.SimpleRequest;

/**
 * @author lin.peng
 * @since 1.0
 **/
public class OkRequestFunctionCtrl extends FunctionController<SimpleRequest, PayloadResponse> {

    @Override
    public PayloadResponse internalHandle(SimpleRequest dto) {
        return new PayloadResponse("sucess", null);
    }

}
