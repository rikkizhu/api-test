package net.xxx.xxx.apitest.userplugin;

import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import net.xxx.xxx.apitest.Utils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

/**
 * @program: DeployPluginVersionTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-12-04 14:56
 **/
public class DeployPluginVersionTestCase extends AbstractTestCase {

    Utils utils = new Utils();
    String spaceKey;
    String pluginId;
    String proName = RandomStringUtils.randomAlphanumeric(8);
    String pluginName = RandomStringUtils.randomAlphanumeric(8);
    String reMark = RandomStringUtils.randomAlphanumeric(12);


    @After
    public void tearDown() throws InterruptedException {
        Thread.sleep(2000);
        utils.deletePlugin(pluginId);
        utils.deleteWorkspaceBySpacekey(spaceKey);
        utils.deleteProject(proName);
    }

    @Test
    public void testPreDeployPluginVersion() {
        String[] results = utils.createPlugin(pluginName, proName, reMark);
        spaceKey = results[0];
        pluginId = results[1];

        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .formParams(new HashMap<String, String>() {{
                    put("version", "");
                    put("description", "[pre publish]");
                    put("isPreDeploy", "true");
                    put("pluginId", pluginId);
                }}).post("/user-plugin/deploy")
                .then()
                .body("code", equalTo(0))
                .body("msg", equalTo("Success"))
                .body("data.auditStatus", equalTo(1))
                .body("data.buildStatus", equalTo(1))
                .body("data.buildVersion", equalTo(""))
                .body("data.createdBy", equalTo(Steps.ownerName))
                .body("data.createdDate", notNullValue())
                .body("data.description", equalTo("[pre publish]"))
                .body("data.id", notNullValue())
                .body("data.isPreDeploy", equalTo(true))
                .body("data.lastModifiedBy", equalTo(Steps.ownerName))
                .body("data.lastModifiedDate", notNullValue())
                .body("data.version", equalTo(0));
    }


    @Test
    public void testDeployPluginVersion() {
        String[] results = utils.createPlugin(pluginName, proName, reMark);
        spaceKey = results[0];
        pluginId = results[1];

        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .formParams(new HashMap<String, String>() {{
                    put("version", "0.0.1");
                    put("description", "0.0.1 version");
                    put("isPreDeploy", "false");
                    put("pluginId", pluginId);
                }}).post("/user-plugin/deploy")
                .then()
                .body("code", equalTo(0))
                .body("msg", equalTo("Success"))
                .body("data.auditStatus", equalTo(1))
                .body("data.buildVersion", equalTo("0.0.1"))
                .body("data.createdBy", equalTo(Steps.ownerName))
                .body("data.createdDate", notNullValue())
                .body("data.description", equalTo("0.0.1 version"))
                .body("data.id", notNullValue())
                .body("data.isPreDeploy", equalTo(false))
                .body("data.lastModifiedBy", equalTo(Steps.ownerName))
                .body("data.lastModifiedDate", notNullValue())
                .body("data.version", equalTo(0));
    }
}
