package net.xxx.xxx.apitest.userplugin;

import io.restassured.response.Response;
import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import net.xxx.xxx.apitest.Utils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsEmptyCollection.empty;

/**
 * @program: CreatePluginTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-12-03 16:00
 **/
public class CreatePluginTestCase extends AbstractTestCase {

    Utils utils = new Utils();
    String pluginName = RandomStringUtils.randomAlphanumeric(8);
    String proName = RandomStringUtils.randomAlphanumeric(8);
    String reMark = RandomStringUtils.randomAlphanumeric(12);
    String spaceKey;
    String pluginId;

    @After
    public void tearDown() throws InterruptedException {
        utils.deletePlugin(pluginId);
        utils.deleteProject(proName);
        utils.deleteWorkspaceBySpacekey(spaceKey);
    }

    @Test
    public void testCreatePlugin() {
        Response response = Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .formParams(new HashMap<String, String>() {{
                    put("cpuLimit", "2");
                    put("memory", "2048");
                    put("storage", "2");
                    put("pluginName", pluginName);
                    put("repoName", proName);
                    put("typeId", "12");
                    put("pluginTemplateId", "8");
                    put("remark", reMark);
                }}).post("/user-plugin/create")
                .then()
                .body("code", equalTo(0))
                .body("msg", equalTo("Success"))
                .body("data.avgScore", equalTo(0.0f))
                .body("data.countScoreUser", equalTo(0))
                .body("data.createdBy", equalTo(Steps.ownerName))
                .body("data.createdDate", notNullValue())
                .body("data.globalStatus", equalTo(2))
                .body("data.id", notNullValue())
                .body("data.lastModifiedBy", equalTo(Steps.ownerName))
                .body("data.lastModifiedDate", notNullValue())
                .body("data.pluginName", equalTo(pluginName))
                .body("data.pluginScores", is(empty()))
                .body("data.pluginVersions", is(empty()))
                .body("data.remark", equalTo(reMark))
                .body("data.repoName", equalTo(proName))
                .body("data.repoUrl", equalTo("https://" + Steps.GitDomain + "/" + Steps.ownerName + "/" + proName + ".git"))
                .body("data.spaceKey.length()", equalTo(6))
                .body("data.status", equalTo(2))
                .body("data.userAvatar", notNullValue())
                .body("data.pluginTypes.typeName", hasItem("腾讯云接口"))
                .body("data.pluginTypes.description", hasItem("腾讯云接口"))
                .body("data.pluginTypes.id", hasItem(12))
                .extract().response();
        spaceKey = response.path("data.spaceKey");
        pluginId = response.path("data.id").toString();
    }

}


