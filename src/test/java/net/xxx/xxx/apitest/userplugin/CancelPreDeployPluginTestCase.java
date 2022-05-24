package net.xxx.xxx.apitest.userplugin;

import io.restassured.response.Response;
import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import net.xxx.xxx.apitest.Utils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

/**
 * @program: CancelPreDeployPluginTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-12-05 15:50
 **/
public class CancelPreDeployPluginTestCase extends AbstractTestCase {
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
        Thread.sleep(1500);
        utils.deletePlugin(pluginId);
        utils.deleteWorkspaceBySpacekey(spaceKey);
        utils.deleteProject(proName);
    }

    @Test
    public void testCancelPreDeployPlugin() throws InterruptedException {

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
                .formParams("versionId", versionId)
                .post("/user-plugin/pre/deploy/cancel")
                .then()
                .body("code", equalTo(0))
                .body("msg", equalTo("Success"));

    }
}


