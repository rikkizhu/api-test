package net.xxx.xxx.apitest.workspace.ws;

import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import net.xxx.xxx.apitest.Utils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.number.OrderingComparison.greaterThan;

/**
 * @program: ListWorkspaceTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-11-21 14:17
 **/
public class ListWorkspaceTestCase extends AbstractTestCase {
    Utils utils = new Utils();
    String projectName1 = RandomStringUtils.randomAlphanumeric(8);
    String projectName2 = RandomStringUtils.randomAlphanumeric(8);
    String spaceKey1;
    String spaceKey2;

    @Before
    public void tearUp() {
        utils.createPro(projectName1);
        utils.createPro(projectName2);
        spaceKey1 = utils.createWsByxxxPro(projectName1);
        spaceKey2 = utils.createWsByxxxPro(projectName2);
    }


    @After
    public void tearDown() throws InterruptedException {
        Thread.sleep(1000);
        utils.deleteWorkspaceBySpacekey(spaceKey1);
        utils.deleteProject(projectName1);
        utils.deleteWorkspaceBySpacekey(spaceKey2);
        utils.deleteProject(projectName2);
    }

    @Test
    public void testListWorkspace() {
        Steps.getRequestSpec().get("/ws/list?page=0&size=100").then()
                .body("code", equalTo(0))
                .body("data.list.spaceKey", hasItem(spaceKey1))
                .body("data.list.spaceKey", hasItem(spaceKey2))
                .body("data.pageMetaData.number", equalTo(0))
                .body("data.pageMetaData.size", equalTo(100))
                .body("data.pageMetaData.totalElements", greaterThan(1))
                .body("data.pageMetaData.totalPages", equalTo(1));
    }
}
