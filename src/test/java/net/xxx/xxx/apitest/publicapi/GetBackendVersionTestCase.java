package net.xxx.xxx.apitest.publicapi;

import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * @program: GetBackendVersionTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-12-14 10:24
 **/
public class GetBackendVersionTestCase extends AbstractTestCase {
    @Test
    public void testGetBackendVersion() {
        Steps.getRequestSpec().get("/public/versions").then()
                .body("code", equalTo(0))
                .body("msg", equalTo("Success"))
                .body("data.commitId", notNullValue())
                .body("data.describe", notNullValue())
                .body("data.buildTime", notNullValue());
    }
}
