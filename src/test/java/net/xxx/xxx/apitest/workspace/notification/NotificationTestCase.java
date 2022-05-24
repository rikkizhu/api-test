package net.xxx.xxx.apitest.workspace.notification;

import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * @program: net.xxx.xxx.workspace.NotificationTestCase.NotificationTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-11-27 15:21
 **/
public class NotificationTestCase extends AbstractTestCase {


    @Test
    public void testNotification() {
        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .get("/workspaces/notification?page=1&pageSize=10")
                .then()
                .body("code", equalTo(0))
                .body("msg", equalTo("Success"))
                .body("data.content", notNullValue())
                .body("data.created_at", notNullValue())
                .body("data.id", notNullValue())
                .body("data.owner_id", notNullValue())
                .body("data.reminded", notNullValue())
                .body("data.status", notNullValue())
                .body("data.target_id", notNullValue())
                .body("data.target_type", notNullValue())
                .body("data.type", notNullValue());

    }
}
