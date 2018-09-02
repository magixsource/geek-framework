package gl.linpeng.gf.controller;

import gl.linpeng.gf.base.ServerlessResponse;
import gl.linpeng.gf.model.SimpleRequest;

/**
 * @author lin.peng
 * @since 1.0
 **/
public class OkRequestFunctionCtrl extends FunctionController<SimpleRequest> {

    @Override
    public ServerlessResponse internalHandle(SimpleRequest dto) {
        return new ServerlessResponse();
    }

}
