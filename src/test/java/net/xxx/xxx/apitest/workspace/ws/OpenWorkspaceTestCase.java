package net.xxx.xxx.apitest.workspace.ws;

import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import net.xxx.xxx.apitest.Utils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;


/**
 * @program: OpenWorkspaceTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-11-16 14:50
 **/
public class OpenWorkspaceTestCase extends AbstractTestCase {
    Utils utils = new Utils();
    String projectName = RandomStringUtils.randomAlphanumeric(8);
    String spaceKey;


    @Before
    public void tearUp() {
        utils.createPro(projectName);
        spaceKey = utils.createWsByxxxPro(projectName);

    }

    @After
    public void tearDown() throws InterruptedException {
        Thread.sleep(1000);
        utils.deleteWorkspaceBySpacekey(spaceKey);
        utils.deleteProject(projectName);
    }

    @Test
    public void testOpenWorkspace() {
        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .headers(new HashMap<String, String>() {{
                    put("x-space-key", spaceKey);
                    put("x-global-key", Steps.global_key);
                }})
                .post("/workspaces/" + spaceKey).then()
                .body("spaceKey", equalTo(spaceKey))
                .body("durationStatus", equalTo("Persistent"))
                .body("shardingGroup", containsString(Steps.getShardingGroup()));

    }


}
