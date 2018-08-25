package gl.linpeng.gf.controller;

import gl.linpeng.gf.base.ServerlessResponse;
import gl.linpeng.gf.model.BadRequestServerlessDTO;

/**
 * @author lin.peng
 * @since 1.0
 **/
public class BadRequestFunctionCtrl extends FunctionController<BadRequestServerlessDTO> {

    @Override
    public ServerlessResponse internalHandle(BadRequestServerlessDTO dto) {
        return new ServerlessResponse.Builder().setObjectBody(dto).build();
    }
}
