package net.xxx.xxx.apitest.workspace.notification;

import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import net.xxx.xxx.apitest.Utils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

/**
 * @program: net.xxx.xxx.workspace.NotificationTestCase.MarkNotificationTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-11-27 15:32
 **/
public class MarkNotificationTestCase extends AbstractTestCase {

    Utils utils = new Utils();
    String proName = RandomStringUtils.randomAlphanumeric(8);

    @Before
    public void tearUp(){
        utils.createPro(proName);
        utils.deleteProject(proName);
    }

    @Test
    public void testMarkNotification(){
        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .param("id",utils.getNotificationId())
                .post("/workspaces/notification/mark-read")
                .then()
                .body("code",equalTo(0))
                .body("msg",equalTo("Success"));

    }

}
