package net.xxx.xxx.apitest.userplugin;

import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import net.xxx.xxx.apitest.Utils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsEmptyCollection.empty;

/**
 * @program: QueryDevPluginsTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-12-13 11:02
 **/
public class QueryDevPluginsTestCase extends AbstractTestCase {
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
    public void testQueryDevPlugins() {
        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .get("/user-plugin/dev/list")
                .then()
                .body("code", equalTo(0))
                .body("msg", equalTo("Success"))
                .body("data[0].allowRelease", equalTo(true))
                .body("data[0].avgScore", equalTo(0.0f))
                .body("data[0].countScoreUser", equalTo(0))
                .body("data[0].createdBy", equalTo(Steps.ownerName))
                .body("data[0].createdDate", notNullValue())
                .body("data[0].globalStatus", equalTo(2))
                .body("data[0].id", equalTo(Integer.parseInt(pluginId)))
                .body("data[0].lastModifiedBy", equalTo(Steps.ownerName))
                .body("data[0].lastModifiedDate", notNullValue())
                .body("data[0].pluginName", equalTo(pluginName))
                .body("data[0].pluginScores", is(empty()))
                .body("data[0].pluginTypes[0].description", equalTo("娱乐小工具"))
                .body("data[0].pluginTypes[0].id", equalTo(10))
                .body("data[0].pluginTypes[0].typeName", equalTo("娱乐小工具"))
                .body("data[0].pluginVersions", is(empty()))
                .body("data[0].remark", equalTo(reMark))
                .body("data[0].repoName", equalTo(proName))
                .body("data[0].repoUrl", notNullValue())
                .body("data[0].spaceKey", equalTo(spaceKey))
                .body("data[0].status", equalTo(2))
                .body("data[0].sumScore", equalTo(0.0f))
                .body("data[0].userAvatar", notNullValue())
                .body("data[0].version", equalTo(1));
    }


}
