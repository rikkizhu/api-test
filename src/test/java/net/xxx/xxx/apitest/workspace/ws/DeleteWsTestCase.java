package net.xxx.xxx.apitest.workspace.ws;

import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import net.xxx.xxx.apitest.Utils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static org.hamcrest.CoreMatchers.equalTo;

/**
 * @program: DeleteWsTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-11-08 15:59
 **/
public class DeleteWsTestCase extends AbstractTestCase {
    Utils utils = new Utils();
    String spaceKey;
    String projectName = RandomStringUtils.randomAlphanumeric(8);


    @Before
    public void tearUp() {
        utils.createPro(projectName);
        utils.synPro();
        spaceKey = utils.createWsByxxxPro(projectName);
    }

    @After
    public void tearDown() throws InterruptedException {
        Thread.sleep(1000);
        utils.deleteWorkspaceBySpacekey(spaceKey);
        utils.deleteProject(projectName);
    }

    @Test
    public void testDeleteWs() {
        Steps.getRequestSpec()
                .param("spaceKey", spaceKey).delete("/ws/delete")
                .then()
                .body("code", equalTo(0))
                .body("data.spaceKey", equalTo(spaceKey))
                .extract().response();

    }
}
