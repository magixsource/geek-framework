package gl.linpeng.gf.controller;

import gl.linpeng.gf.base.JsonDTO;
import gl.linpeng.gf.base.ServerlessResponse;

/**
 * @author lin.peng
 * @since 1.0
 **/
public class OkRequestFunctionCtrl extends FunctionController<JsonDTO> {

    @Override
    public ServerlessResponse internalHandle(JsonDTO dto) {
        return new ServerlessResponse.Builder().setObjectBody(dto).build();
    }

}
