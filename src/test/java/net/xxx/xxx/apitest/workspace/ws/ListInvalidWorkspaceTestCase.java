package net.xxx.xxx.apitest.workspace.ws;

import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import net.xxx.xxx.apitest.Utils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.hasItem;

/**
 * @program: ListInvalidWorkspaceTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-11-21 15:06
 **/
public class ListInvalidWorkspaceTestCase extends AbstractTestCase {
    Utils utils = new Utils();
    String projectName1 = RandomStringUtils.randomAlphanumeric(8);
    String projectName2 = RandomStringUtils.randomAlphabetic(8);
    String spaceKey1, spaceKey2;

    @Before
    public void tearUp() {
        utils.createPro(projectName1);
        utils.createPro(projectName2);
        spaceKey1 = utils.createWsByxxxPro(projectName1);
        spaceKey2 = utils.createWsByxxxPro(projectName2);
        utils.deleteWorkspaceBySpacekey(spaceKey1);
        utils.deleteWorkspaceBySpacekey(spaceKey2);

    }

    @After
    public void tearDown() throws InterruptedException {
        Thread.sleep(1000);
        utils.deleteProject(projectName1);
        utils.deleteProject(projectName2);
    }


    @Test

    public void testListInvalidWorkspace() {
        Steps.getRequestSpec().get("/workspaces?invalid").then()
                .body("spaceKey", hasItem(spaceKey1))
                .body("spaceKey", hasItem(spaceKey2));
    }

}
