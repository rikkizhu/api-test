package net.xxx.xxx.apitest.workspace.notification;

import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

/**
 * @program: MessageTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-11-27 15:59
 **/
public class MessageTestCase extends AbstractTestCase {


    @Test
    public void testMessage() {
        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .get("/workspaces/message?page=1&pageSize=10")
                .then()
                .body("code", equalTo(0))
                .body("msg", equalTo("Success"))
                .body("data", notNullValue());
    }
}
