package gl.linpeng.gf.controller;

import com.alibaba.fastjson.JSON;
import gl.linpeng.gf.base.ServerlessRequest;
import gl.linpeng.gf.base.ServerlessResponse;
import gl.linpeng.gf.model.SimpleRequest;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FunctionControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(FunctionControllerTest.class);

    ServerlessRequest request;
    SimpleRequest simpleRequest;

    @Before
    public void setUp() throws Exception {
        String jsonText = "{\"id\":\"44\"}";
        simpleRequest = JSON.parseObject(jsonText, SimpleRequest.class);
        request = new ServerlessRequest(jsonText, null, simpleRequest);

    }

    @Test
    public void okRequest() {
        OkRequestFunctionCtrl ctrl = new OkRequestFunctionCtrl();
        ServerlessResponse response = ctrl.handler(request);
        logger.debug("response {} ", JSON.toJSONString(response, true));
    }

}
