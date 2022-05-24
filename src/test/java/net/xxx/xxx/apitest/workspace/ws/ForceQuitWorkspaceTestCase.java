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
 * @program: ForceQuitWorkspaceTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-11-27 15:04
 **/
public class ForceQuitWorkspaceTestCase extends AbstractTestCase {
    Utils utils = new Utils();
    String wsName = RandomStringUtils.randomAlphanumeric(8);
    String spacekey;

    @Before
    public void teearUp() {
        spacekey = utils.createWsWithoutPro(wsName, Steps.AI_TemplateId);
        utils.openWorkspace(spacekey);
    }

    @After
    public void tearDown() {
        utils.deleteWorkspaceBySpacekey(spacekey);
    }

    @Test
    public void testForceQuitWs() {
        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .header("x-space-key", spacekey)
                .post("/workspaces/" + spacekey + "/force_quit")
                .then()
                .body("code", equalTo(0))
                .body("msg", equalTo("Success"));
    }


}
