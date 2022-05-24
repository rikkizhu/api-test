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
 * @program: CreateWsByxxxProjectTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-11-05 17:32
 **/
public class CreateWsByxxxProjectTestCase extends AbstractTestCase {


    Utils utils = new Utils();
    String spaceKey;
    String projectName = RandomStringUtils.randomAlphanumeric(8);

    @Before
    public void tearUp() throws InterruptedException {
        Thread.sleep(1000);
        utils.createPro(projectName);
        utils.synPro();
    }

    @After
    public void tearDown() throws InterruptedException {
        Thread.sleep(1000);
        utils.deleteWorkspaceBySpacekey(spaceKey);
        utils.deleteProject(projectName);
    }

    @Test
    public void testCreateWsByxxxProject() {
        Response response = Steps.getRequestSpec().formParams(new HashMap<String, String>() {{
            put("cpuLimit", "2");
            put("memory", "2048");
            put("storage", "2");
            put("source", "xxx");
            put("ownerName", Steps.ownerName);
            put("projectName", projectName);
            put("envId", "xxx-tty-jekyll");
        }}).post("/ws/create").then()
                .body("code", equalTo(0))
                .body("data.cpuLimit", equalTo(2))
                .body("data.durationStatus", equalTo("Persistent"))
                .body("data.enxxx", equalTo("UTF-8"))
                .body("data.envId", equalTo("xxx-tty-jekyll"))
                .body("data.globalKey", equalTo(Steps.global_key))
                .body("data.memory", equalTo(2048))
                .body("data.ownerName", equalTo(Steps.ownerName))
                .body("data.projectHtmlUrl", equalTo(String.format("https://xxx.net/u/%s/p/%s", Steps.global_key, projectName)))
                .body("data.projectName", equalTo(projectName))
                .body("data.projectSource", equalTo("xxx"))
                .body("data.status", equalTo("Created"))
                .body("data.storage", equalTo(2))
                .body("data.shardingGroup", containsString(Steps.getShardingGroup()))
                .body("data.spaceKey.length()", equalTo(6))
                .extract().response();

        spaceKey = response.path("data.spaceKey");

    }


}
