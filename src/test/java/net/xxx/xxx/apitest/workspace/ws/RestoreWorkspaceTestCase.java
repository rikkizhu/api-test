package net.xxx.xxx.apitest.workspace.ws;

import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import net.xxx.xxx.apitest.Utils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

/**
 * @program: RestoreWorkspaceTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-11-21 15:18
 **/
public class RestoreWorkspaceTestCase extends AbstractTestCase {

    Utils utils = new Utils();
    String projectName = RandomStringUtils.randomAlphanumeric(8);
    String spaceKey;

    @Before
    public void tearUp() {
        utils.createPro(projectName);
        spaceKey = utils.createWsByxxxPro(projectName);
        utils.deleteWorkspaceBySpacekey(spaceKey);
    }

    @After
    public void tearDown() throws InterruptedException {
        Thread.sleep(1000);
        utils.deleteWorkspaceBySpacekey(spaceKey);
        utils.deleteProject(projectName);
    }

    @Test
    public void testRestoreWorkspace() {
        Steps.getRequestSpec()
                .contentType("application/x-www-form-urlencoded")
                .accept("application/vnd.xxx.v2+json")
                .post("/workspaces/" + spaceKey + "/restore").then()
                .body("spaceKey", equalTo(spaceKey))
                .body("deleteTime", notNullValue())
                .body("durationStatus", equalTo("Persistent"))
                .body("memory", equalTo(2048))
                .body("storage", equalTo(2));
    }
}
