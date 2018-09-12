package gl.linpeng.gf.controller;

import gl.linpeng.gf.base.ServerlessRequest;
import gl.linpeng.gf.base.ServerlessResponse;
import gl.linpeng.gf.model.SimpleRequest;

/**
 * @author lin.peng
 * @since 1.0
 **/
public class OkRequestFunctionCtrl extends FunctionController<SimpleRequest, ServerlessRequest, ServerlessResponse> {

    @Override
    public ServerlessResponse internalHandle(SimpleRequest dto) {
        System.out.println(dto.getId());
        return new ServerlessResponse.Builder().setObjectBody(dto).build();
    }

}
