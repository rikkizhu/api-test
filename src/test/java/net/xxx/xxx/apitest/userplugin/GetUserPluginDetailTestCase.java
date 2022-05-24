package net.xxx.xxx.apitest.userplugin;

import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import net.xxx.xxx.apitest.Utils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * @program: GetUserPluginDetailTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-12-13 11:41
 **/
public class GetUserPluginDetailTestCase extends AbstractTestCase {
    Utils utils = new Utils();
    String pluginName = RandomStringUtils.randomAlphanumeric(8);
    String proName = RandomStringUtils.randomAlphanumeric(8);
    String reMark = RandomStringUtils.randomAlphanumeric(12);
    String spaceKey;
    String pluginId;

    @Before
    public void tearUp() {
        String results[] = utils.createPlugin(pluginName, proName, reMark);
        spaceKey = results[0];
        pluginId = results[1];
    }

    @After
    public void tearDown() throws InterruptedException {
        Thread.sleep(2000);
        utils.deleteWorkspaceBySpacekey(spaceKey);
        utils.deleteProject(proName);
        utils.deletePlugin(pluginId);
    }

    @Test
    public void testGetUserPluginDetail() {
        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .get("/user-plugin/info?pluginId=" + pluginId)
                .then()
                .body("code", equalTo(0))
                .body("msg", equalTo("Success"))
                .body("data.avgScore", equalTo(0.0f))
                .body("data.countScoreUser", equalTo(0))
                .body("data.createdBy", equalTo(Steps.ownerName))
                .body("data.createdDate", notNullValue())
                .body("data.globalStatus", equalTo(2))
                .body("data.id", equalTo(Integer.parseInt(pluginId)))
                .body("data.installCount", equalTo(0))
                .body("data.lastModifiedBy", equalTo(Steps.ownerName))
                .body("data.lastModifiedDate", notNullValue())
                .body("data.pluginName", equalTo(pluginName))
                .body("data.pluginScores", is(empty()))
                .body("data.pluginTypes[0].description", equalTo("娱乐小工具"))
                .body("data.pluginTypes[0].id", equalTo(10))
                .body("data.pluginTypes[0].typeName", equalTo("娱乐小工具"))
                .body("data.pluginVersions", is(empty()))
                .body("data.readme", notNullValue())
                .body("data.remark", equalTo(reMark))
                .body("data.repoName", equalTo(proName))
                .body("data.repoUrl", notNullValue())
                .body("data.spaceKey", equalTo(spaceKey))
                .body("data.status", equalTo(2))
                .body("data.userAvatar", notNullValue());
    }

}
