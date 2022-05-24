package net.xxx.xxx.apitest.userplugin;

import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import net.xxx.xxx.apitest.Utils;
import org.junit.After;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @program: InstallPluginTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-12-11 15:46
 **/
public class InstallPluginTestCase extends AbstractTestCase {
    Utils utils = new Utils();


    @After
    public void tearDown() {
        utils.enablePlugin(Steps.getCloudStudioPluginMaterialID(), Steps.PluginInstalledStatus);
    }

    @Test
    public void testInstallPlugin() {
        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .formParams(new HashMap<String, String>() {{
                    put("pluginId", Steps.getCloudStudioPluginMaterialID());
                    put("status", "1");
                }}).put("/user-plugin/enable").then()
                .body("code", equalTo(0))
                .body("msg", equalTo("Success"));
    }
}
