package net.xxx.xxx.apitest.publicapi;

import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * @program: GetUserPluginDetailTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-12-14 11:34
 **/
public class GetUserPluginDetailTestCase extends AbstractTestCase {
    @Test
    public void testGetUserPluginDetail() {
        Steps.getRequestSpec()
                .formParams(new HashMap<String, String>() {{
                    put("pluginId", Steps.getCloudStudioPluginMaterialID());
                }})
                .get("/public/user-plugins/info").then()
                .body("code", equalTo(0))
                .body("msg", equalTo("Success"))
                .body("data.id", notNullValue())
                .body("data.globalStatus", equalTo(1))
                .body("data.avgScore", notNullValue())
                .body("data.countScoreUser", notNullValue())
                .body("data.createdBy", equalTo("xxxxxx"))
                .body("data.createdDate", notNullValue())
                .body("data.currentVersion", notNullValue())
                .body("data.installCount", notNullValue())
                .body("data.lastDeployDate", notNullValue())
                .body("data.lastModifiedBy", notNullValue())
                .body("data.lastModifiedDate", notNullValue())
                .body("data.pluginName", equalTo("material 主题"))
                .body("data.pluginFilePath", notNullValue())
                .body("data.pluginScores", notNullValue())
                .body("data.pluginTypes", notNullValue())
                .body("data.pluginVersions", notNullValue())
                .body("data.readme", notNullValue())
                .body("data.remark", equalTo("material 主题插件"))
                .body("data.repoName", equalTo("CloudStudio-Plugin-Material"))
                .body("data.repoUrl", notNullValue())
                .body("data.spaceKey", notNullValue())
                .body("data.status", equalTo(2))
                .body("data.userAvatar", notNullValue());
    }
}
