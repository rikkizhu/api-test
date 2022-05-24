package net.xxx.xxx.apitest.publicapi;

import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * @program: GetPluginScoreListTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-12-14 11:56
 **/
public class GetPluginScoreListTestCase extends AbstractTestCase {
    @Test
    public void testGetPluginScoreList() {
        Steps.getRequestSpec()
                .formParams(new HashMap<String, String>() {{
                    put("pluginId", Steps.getCloudStudioPluginMaterialID());
                    put("page", "0");
                    put("size", "10");
                }})
                .post("/public/user-plugins/score/list").then()
                .body("page.number", equalTo(0))
                .body("page.size", equalTo(10))
                .body("page.totalElements", notNullValue())
                .body("page.totalPages", notNullValue())
                .body("contents.comment", notNullValue())
                .body("contents.createdBy", notNullValue())
                .body("contents.createdDate", notNullValue())
                .body("contents.id", notNullValue())
                .body("contents.lastModifiedBy", notNullValue())
                .body("contents.lastModifiedDate", notNullValue())
                .body("contents.score", notNullValue())
                .body("contents.userAvatar", notNullValue())
                .body("contents.version", notNullValue());
    }
}
