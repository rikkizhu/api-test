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


/**
 * @program: QueryCollaborativeWorkspaceTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-11-21 16:09
 **/
public class QueryCollaborativeWorkspaceTestCase extends AbstractTestCase {

    Utils utils = new Utils();
    String wsName = RandomStringUtils.randomAlphanumeric(8);
    String spaceKey;

    @Before
    public void tearUp() {
        spaceKey = utils.createWsWithoutPro(wsName, Steps.AI_TemplateId);
        utils.inviteCollaborators(spaceKey);
    }

    @After
    public void tearDown() throws InterruptedException {
        Thread.sleep(1000);
        utils.deleteWorkspaceBySpacekey(spaceKey);
    }

    @Test
    public void testQueryCollaborativeWorkspace() {
        Steps.getRequestSpec().get("/ws/list?collaborative").then()
                .body("spaceKey", hasItem(spaceKey))
                .body("find{it.spaceKey=='" + spaceKey + "'}.collaborative", equalTo(true))
                .body("find{it.spaceKey=='" + spaceKey + "'}.workspaceName", equalTo(wsName));

    }


}
