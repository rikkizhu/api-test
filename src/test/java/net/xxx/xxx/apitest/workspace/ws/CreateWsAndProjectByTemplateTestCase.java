package net.xxx.xxx.apitest.workspace.ws;

import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import net.xxx.xxx.apitest.Utils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Test;

import io.restassured.response.Response;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;

/**
 * @program: CreateWsAndProjectByTemplateTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-11-06 13:59
 **/
public class CreateWsAndProjectByTemplateTestCase extends AbstractTestCase {
    Utils utils = new Utils();
    String projectName = RandomStringUtils.randomAlphanumeric(8);
    String spaceKey;

    @After
    public void tearDown() throws InterruptedException {
        Thread.sleep(1000);
        utils.deleteWorkspaceBySpacekey(spaceKey);
        utils.deleteProject(projectName);
    }

    @Test
    public void testCreateWsAndProjectByTemplate() {
        Steps.getRequestSpec().formParams(new HashMap<String, String>() {
            {
                put("type", "2");
                put("gitEnabled", "true");
                put("gitReadmeEnabled", "false");
                put("vcsType", "git");
                put("name", projectName);
            }
        }).post("/projects").then()
                .body("code", equalTo(0));

        Response response = Steps.getRequestSpec().formParams(new HashMap<String, String>() {{
            put("cpuLimit", "2");
            put("memory", "2048");
            put("storage", "2");
            put("source", "xxx");
            put("ownerName", Steps.ownerName);
            put("projectName", projectName);
            put("templateId", Steps.AI_TemplateId.toString());

        }}).post("/ws/create").then()
                .body("code", equalTo(0))
                .body("data.cpuLimit", equalTo(2))
                .body("data.durationStatus", equalTo("Persistent"))
                .body("data.enxxx", equalTo("UTF-8"))
                .body("data.envId", equalTo("xxx-tty-machine-learning"))
                .body("data.memory", equalTo(2048))
                .body("data.storage", equalTo(2))
                .body("data.projectHtmlUrl", equalTo(String.format("https://xxx.net/u/%s/p/%s", Steps.global_key, projectName)))
                .body("data.projectSource", equalTo("xxx"))
                .body("data.status", equalTo("Created"))
                .body("data.globalKey", equalTo(Steps.global_key))
                .body("data.ownerName", equalTo(Steps.global_key))
                .body("data.shardingGroup", containsString(Steps.getShardingGroup()))
                .body("data.spaceKey.length()", equalTo(6))
                .extract().response();

        spaceKey = response.path("data.spaceKey");

    }


}


