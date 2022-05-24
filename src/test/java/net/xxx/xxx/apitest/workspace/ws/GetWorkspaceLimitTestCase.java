package net.xxx.xxx.apitest.workspace.ws;

import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

/**
 * @program: GetWorkspaceLimitTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-11-26 18:02
 **/
public class GetWorkspaceLimitTestCase extends AbstractTestCase {

    @Test
    public void testGetWsLimit() {
        Steps.getRequestSpec()
                .accept("application/vnd.xxx.v2+json")
                .get("/workspaces/workspace-limit")
                .then()
                .body("code", equalTo(0))
                .body("data.cpu", equalTo(3))
                .body("data.id", notNullValue())
                .body("data.imageLimit", equalTo(5))
                .body("data.level", equalTo(5))
                .body("data.memory", equalTo(4096))
                .body("data.online", equalTo(1))
                .body("data.persistAccessUrl", equalTo(false))
                .body("data.storage", equalTo(8))
                .body("data.temporary", equalTo(1))
                .body("data.version", equalTo(1))
                .body("data.workspace", equalTo(5))
                .body("msg", equalTo("Success"));


    }

}

