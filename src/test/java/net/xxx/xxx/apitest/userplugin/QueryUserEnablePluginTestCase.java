package net.xxx.xxx.apitest.userplugin;

import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import net.xxx.xxx.apitest.Utils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * @program: QueryUserEnablePluginTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-12-11 15:40
 **/
public class QueryUserEnablePluginTestCase extends AbstractTestCase {
    Utils utils = new Utils();


    @Before
    public void tearup() {
        utils.enablePlugin(Steps.getCloudStudioPluginMaterialID(), Steps.PluginNotInstalledStatus);
    }

    @After
    public void tearDown() throws InterruptedException {
        Thread.sleep(2000);
        utils.enablePlugin(Steps.getCloudStudioPluginMaterialID(), Steps.PluginInstalledStatus);
    }


    @Test
    public void testQueryUserEnablePlugin() {
        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .get("/user-plugin/enable/list")
                .then()
                .body("code", equalTo(0))
                .body("msg", equalTo("Success"))
                .body("data.createdBy", hasItem("xxxxxx"))
                .body("data.createdDate", notNullValue())
                .body("data.currentVersion", notNullValue())
                .body("data.id", hasItem(Integer.parseInt(Steps.getCloudStudioPluginMaterialID())))
                .body("data.lastModifiedDate", notNullValue())
                .body("data.pluginFilePath", notNullValue())
                .body("data.pluginName", notNullValue())
                .body("data.remark", notNullValue())
                .body("data.repoName", notNullValue())
                .body("data.userAvatar", notNullValue());
    }
}
