package net.xxx.xxx.apitest.userplugin;

import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import net.xxx.xxx.apitest.Utils;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @program: UninstallPluginTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-12-11 16:01
 **/
public class UninstallPluginTestCase extends AbstractTestCase {
    Utils utils = new Utils();


    @Before
    public void tearUp() {
        utils.enablePlugin(Steps.getCloudStudioPluginMaterialID(), Steps.PluginNotInstalledStatus);
    }


    @Test
    public void testUninstallPlugin() {
        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .formParams(new HashMap<String, String>() {{
                    put("pluginId", Steps.getCloudStudioPluginMaterialID());
                    put("status", "2");
                }}).put("/user-plugin/enable").then()
                .body("code", equalTo(0))
                .body("msg", equalTo("Success"));
    }

}
