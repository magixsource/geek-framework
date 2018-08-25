package gl.linpeng.gf.controller;

import com.alibaba.fastjson.JSON;
import gl.linpeng.gf.C;
import gl.linpeng.gf.base.ServerlessRequest;
import gl.linpeng.gf.base.ServerlessResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FunctionControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(FunctionControllerTest.class);

    ServerlessRequest.Builder builder;
    ServerlessRequest request;

    @Before
    public void setUp() throws Exception {
        builder = new ServerlessRequest.Builder();
        String jsonText = "{\"content\":\"abcdef\"}";
        request = builder.setRawBody(jsonText).build();
    }

    @Test
    public void okRequest() {
        OkRequestFunctionCtrl ctrl = new OkRequestFunctionCtrl();
        ServerlessResponse response = ctrl.handler(request);
        logger.debug("response {} ", JSON.toJSONString(response, true));

        Assert.assertTrue(response != null);
        Assert.assertEquals(response.getStatusCode(), C.Http.StatusCode.OK.v());
        Assert.assertTrue(response.getHeaders().get(C.Http.contentType).contains(C.Http.ContentType.JSON));
        Assert.assertNotNull(response.getBody());
    }

    @Test
    public void badRequestWithBeanValidate() {
        BadRequestFunctionCtrl ctrl = new BadRequestFunctionCtrl();
        ServerlessResponse response = ctrl.handler(request);
        logger.debug("response {} ", JSON.toJSONString(response, true));

        Assert.assertTrue(response != null);
        Assert.assertEquals(response.getStatusCode(), C.Http.StatusCode.BAD_REQUEST.v());
        Assert.assertTrue(response.getHeaders().get(C.Http.contentType).contains(C.Http.ContentType.JSON));
        Assert.assertNotNull(response.getBody());
    }
}