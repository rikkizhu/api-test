package net.xxx.xxx.apitest.userplugin;

import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import net.xxx.xxx.apitest.Utils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @program: GradePluginTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-12-13 13:55
 **/
public class GradePluginTestCase extends AbstractTestCase {
    Utils utils = new Utils();

    @Before
    public void tearUp() {
        utils.enablePlugin(Steps.getCloudStudioPluginMaterialID(), Steps.PluginNotInstalledStatus);
    }

    @After
    public void tearDown() {
        utils.enablePlugin(Steps.getCloudStudioPluginMaterialID(), Steps.PluginInstalledStatus);
    }

    @Test
    public void testGradePlugin() {
        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .formParams(new HashMap<String, String>() {{
                    put("pluginScoreId", Steps.getPluginScoreId());
                    put("score", "5");
                    put("comment", "nice!!!!!!!!");
                }}).put("/user-plugin/score")
                .then()
                .body("code", equalTo(0))
                .body("msg", equalTo("Success"));
    }
}
