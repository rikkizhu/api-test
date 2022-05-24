package net.xxx.xxx.apitest.workspace.ws;

import io.restassured.response.Response;
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
 * @program: CreateWsByGitUrlTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-11-06 15:34
 **/
public class CreateWsByGitUrlTestCase extends AbstractTestCase {
    Utils utils = new Utils();
    String spaceKey;
    String projectName = RandomStringUtils.randomAlphanumeric(8);
    String GitSSH = String.format("git@git.dev.xxx.com:%s/%s.git", Steps.global_key, projectName);

    @Before
    public void tearUp() {
        utils.createPro(projectName);
    }

    @After
    public void tearDown() throws InterruptedException {
        Thread.sleep(1000);
        utils.deleteWorkspaceBySpacekey(spaceKey);
        utils.deleteProject(projectName);
    }

    @Test
    public void testCreateWsByGitUrl() {
        Response response = Steps.getRequestSpec().formParams(new HashMap<String, String>() {
            {
                put("cpuLimit", "2");
                put("memory", "2048");
                put("storage", "2");
                put("source", "Import");
                put("url", GitSSH);
                put("envId", "xxx-tty-hexo");
            }
        }).post("/ws/clone")
                .then()
                .body("code", equalTo(0))
                .body("data.durationStatus", equalTo("Persistent"))
                .body("data.enxxx", equalTo("UTF-8"))
                .body("data.envId", equalTo("xxx-tty-hexo"))
                .body("data.globalKey", equalTo(Steps.global_key))
                .body("data.memory", equalTo(2048))
                .body("data.ownerName", equalTo(Steps.global_key))
                .body("data.projectName", equalTo(projectName))
                .body("data.projectSource", equalTo("Import"))
                .body("data.shardingGroup", containsString(Steps.getShardingGroup()))
                .body("data.spaceKey.length()", equalTo(6))
                .extract().response();
        spaceKey = response.path("data.spaceKey");

    }


}




