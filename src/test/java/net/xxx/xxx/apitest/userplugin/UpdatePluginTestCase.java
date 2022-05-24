package net.xxx.xxx.apitest.userplugin;

import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import net.xxx.xxx.apitest.Utils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.equalTo;

/**
 * @program: UpdatePluginTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-12-04 11:07
 **/
public class UpdatePluginTestCase extends AbstractTestCase {
    Utils utils = new Utils();
    String spceKey;
    String pluginId;
    String proName = RandomStringUtils.randomAlphanumeric(8);
    String pluginName = RandomStringUtils.randomAlphanumeric(8);
    String reMark = RandomStringUtils.randomAlphanumeric(12);


    @After
    public void tearDown() throws InterruptedException {
        Thread.sleep(2000);
        utils.deletePlugin(pluginId);
        utils.deleteWorkspaceBySpacekey(spceKey);
        utils.deleteProject(proName);
    }

    @Test
    public void testUpdatePlugin() {
        String[] results = utils.createPlugin(pluginName, proName, reMark);
        spceKey = results[0];
        pluginId = results[1];
        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .formParams(new HashMap<String, String>() {{
                    put("pluginId", pluginId);
                    put("pluginName", pluginName + "123");
                    put("remark", reMark + "123");
                }}).put("/user-plugin/plugin")
                .then()
                .body("code", equalTo(0))
                .body("msg", equalTo("Success"));
    }
}
