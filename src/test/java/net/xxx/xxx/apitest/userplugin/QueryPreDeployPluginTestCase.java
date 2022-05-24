package net.xxx.xxx.apitest.userplugin;

import io.restassured.response.Response;
import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import net.xxx.xxx.apitest.Utils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * @program: net.xxx.xxx.userplugin.QueryPreDeployPlugin
 * @description:
 * @author: zhuruiqi
 * @create: 2018-12-11 14:21
 **/
public class QueryPreDeployPluginTestCase extends AbstractTestCase {

    Utils utils = new Utils();
    String pluginName = RandomStringUtils.randomAlphanumeric(8);
    String proName = RandomStringUtils.randomAlphanumeric(8);
    String reMark = RandomStringUtils.randomAlphanumeric(12);
    String spaceKey;
    String pluginId;
    Integer versionId;


    @Before
    public void tearUp() throws InterruptedException {
        String[] results = utils.createPlugin(pluginName, proName, reMark);
        spaceKey = results[0];
        pluginId = results[1];
        Thread.sleep(5000);
        utils.prePublishPlugin(pluginId);
    }

    @After
    public void tearDown() throws InterruptedException {
        Thread.sleep(2000);
        utils.deletePlugin(pluginId);
        utils.deleteWorkspaceBySpacekey(spaceKey);
        utils.deleteProject(proName);
    }

    @Test
    public void testQueryPreDeployPlugins() throws InterruptedException {
        Boolean status = true;
        while (status) {
            Response response =
                    Steps.getRequestSpec()
                            .accept("application/vnd.xxx.v2+json")
                            .get("/user-plugin/info?pluginId=" + pluginId)
                            .then().extract().response();
            versionId = response.path("data.pluginVersions[0].id");
            Thread.sleep(1000);
            if (!response.path("data.pluginVersions[0].buildStatus").equals(1)) {
                status = false;
            }
        }

        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .get("/user-plugin/pre/deploy/list")
                .then()
                .body("code", equalTo(0))
                .body("msg", equalTo("Success"))
                .body("data.createdDate", notNullValue())
                .body("data.lastModifiedDate", notNullValue())
                .body("data.globalStatus", hasItem(2))
                .body("data.id", hasItem(Integer.parseInt(pluginId)))
                .body("data.pluginName", hasItem(pluginName))
                .body("data.remark", hasItem(reMark))
                .body("data.repoName", hasItem(proName))
                .body("data.status", hasItem(2))
                .body("data.repoUrl", hasItem(
                        "https://git.dev.xxx.com/qxxxtest/" + proName + ".git"))
                .body("data.pluginFilePath", notNullValue())
                .body("data.version.auditStatus", hasItem(1))
                .body("data.version.buildStatus", hasItem(2))
                .body("data.version.buildStatus", hasItem(2))
                .body("data.version.buildVersion", hasItem(""))
                .body("data.version.createdBy", hasItem(Steps.ownerName))
                .body("data.version.createdDate", notNullValue())
                .body("data.version.lastModifiedDate", notNullValue())
                .body("data.version.description", hasItem(
                        "[pre publish]"))
                .body("data.version.id", hasItem(versionId))
                .body("data.version.isPreDeploy", hasItem(true))
                .body("data.version.version", hasItem(1));
    }
}
