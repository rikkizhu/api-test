package net.xxx.xxx.apitest.publicapi;

import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * @program: GetUserDeployPluginsTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-12-14 10:52
 **/
public class GetUserDeployPluginsTestCase extends AbstractTestCase {
    @Test
    public void testGetUserDeployPlugins() {
        Steps.getRequestSpec()
                .formParams(new HashMap<String, String>() {{
                    put("pluginTypeId", "false");
                    put("keywords", "mater");
                    put("pluginTypeId", "-1");
                    put("page", "0");
                    put("size", "12");
                }}).post("/public/user-plugins").then()
                .body("contents.id", notNullValue())
                .body("contents.avgScore", notNullValue())
                .body("contents.countScoreUser", notNullValue())
                .body("contents.createdBy", hasItem("xxxxxx"))
                .body("contents.createdDate", notNullValue())
                .body("contents.currentVersion", notNullValue())
                .body("contents.globalStatus", hasItem(1))
                .body("contents.installCount", notNullValue())
                .body("contents.lastDeployDate", notNullValue())
                .body("contents.lastModifiedBy", notNullValue())
                .body("contents.lastModifiedDate", notNullValue())
                .body("contents.pluginFilePath", notNullValue())
                .body("contents.pluginName", hasItem("material 主题"))
                .body("contents.pluginScores", notNullValue())
                .body("contents.pluginTypes", notNullValue())
                .body("contents.pluginVersions", notNullValue())
                .body("contents.remark", hasItem("material 主题插件"))
                .body("contents.repoName", hasItem("CloudStudio-Plugin-Material"))
                .body("contents.repoUrl", notNullValue())
                .body("contents.spaceKey", notNullValue())
                .body("contents.status", hasItem(2))
                .body("contents.userAvatar", notNullValue())
                .body("page.number", equalTo(0))
                .body("page.size", equalTo(12))
                .body("page.totalElements", equalTo(1))
                .body("page.totalPages", equalTo(1));
    }
}
