package net.xxx.xxx.apitest.publicapi;

import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * @program: GetClientCredentialTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-12-14 10:30
 **/
public class GetClientCredentialTestCase extends AbstractTestCase {
    @Test
    public void testGetClientCredential() {
        Steps.getRequestSpec()
                .formParams(new HashMap<String, String>() {{
                    put("url", "https://www.baidu.com/");
                }})
                .post("/public/get-weixin-client-credential")
                .then()
                .body("code", equalTo(0))
                .body("msg", equalTo("Success"))
                .body("data.appId", notNullValue())
                .body("data.ticket", notNullValue())
                .body("data.noncestr", notNullValue())
                .body("data.timestamp", notNullValue())
                .body("data.signature", notNullValue());
    }
}
