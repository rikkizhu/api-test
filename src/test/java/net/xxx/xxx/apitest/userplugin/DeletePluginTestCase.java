package net.xxx.xxx.apitest.userplugin;

import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import net.xxx.xxx.apitest.Utils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @program: DeletePluginTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-12-11 09:51
 **/
public class DeletePluginTestCase extends AbstractTestCase {
    Utils utils = new Utils();
    String pluginName = RandomStringUtils.randomAlphanumeric(8);
    String proName = RandomStringUtils.randomAlphanumeric(8);
    String reMark = RandomStringUtils.randomAlphanumeric(12);
    String spaceKey;
    String pluginId;

    @Before
    public void tearUp() {
        String[] results = utils.createPlugin(pluginName, proName, reMark);
        spaceKey = results[0];
        pluginId = results[1];
    }

    @After
    public void tearDown() throws InterruptedException {
        Thread.sleep(1500);
        utils.deleteWorkspaceBySpacekey(spaceKey);
        utils.deleteProject(proName);
    }

    @Test
    public void testDeletePlugin() {
        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .delete("/user-plugin/" + pluginId)
                .then()
                .body("code", equalTo(0))
                .body("msg", equalTo("Success"));
    }
}
