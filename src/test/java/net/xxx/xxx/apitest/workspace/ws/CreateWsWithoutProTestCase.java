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
 * @program: CreateWsWithoutProTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-11-06 16:30
 **/
public class CreateWsWithoutProTestCase extends AbstractTestCase {

    String wsName = RandomStringUtils.randomAlphanumeric(8);
    String spaceKey;
    Utils utils = new Utils();

    @After
    public void tearDown() throws InterruptedException {
        Thread.sleep(1000);
        utils.deleteWorkspaceBySpacekey(spaceKey);
    }

    @Test
    public void testCreateWsWithoutPro() {
        Response response = Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .formParams(new HashMap<String, Object>() {{
                    put("cpuLimit", 2);
                    put("memory", 2048);
                    put("storage", 2);
                    put("workspaceName", wsName);
                    put("ownerName", "xxxxxx");
                    put("projectName", "empty-template");
                    put("templateId", Steps.JavaDemo_TemplateId);
                }}).post("/workspaces").then()
                .body("cpuLimit", equalTo(2))
                .body("memory", equalTo(2048))
                .body("durationStatus", equalTo("Persistent"))
                .body("enxxx", equalTo("UTF-8"))
                .body("envId", equalTo("xxx-tty-java-maven"))
                .body("project.name", equalTo("empty-template"))
                .body("project.httpsUrl", equalTo("https://git.dev.xxx.com/xxxxxx/empty-template.git"))
                .body("storage", equalTo(2))
                .body("workingStatus", equalTo("Created"))
                .body("workspaceName", equalTo(wsName))
                .body("spaceKey.length()", equalTo(6))
                .body("shardingGroup", containsString(Steps.getShardingGroup()))
                .body("project.source", equalTo("xxx"))
                .extract().response();

        spaceKey = response.path("spaceKey");

    }
}

