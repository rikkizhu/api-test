package net.xxx.xxx.apitest.languagesetting;

import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import net.xxx.xxx.apitest.Utils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @program: net.xxx.xxx.languagesetting.SetLanguageService
 * @description:
 * @author: zhuruiqi
 * @create: 2018-12-17 15:23
 **/
public class SetLanguageServiceTestCase extends AbstractTestCase {
    Utils utils = new Utils();
    String wsName = RandomStringUtils.randomAlphanumeric(8);
    String spaceKey;


    @Before
    public void tearUp() throws IOException {
        spaceKey = utils.createWsWithoutPro(wsName, Steps.AI_TemplateId);
        utils.openWorkspace(spaceKey);
        utils.uploadFile(spaceKey, "settings.json", "/.xxx-xxx/", "");
    }


    @After
    public void tearDown() {
        utils.deleteWorkspaceBySpacekey(spaceKey);
        utils.deleteGeneratedFile();
    }

    @Test
    public void testSetLanguageService() {
        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .header("x-space-key", spaceKey)
                .formParams(new HashMap<String, String>() {{
                    put("type", "Java");
                    put("srcPath", "/");
                }}).put("/settings/" + spaceKey + "/language/one")
                .then()
                .body("code", equalTo(0))
                .body("msg", equalTo("Success"));

        Steps.getRequestSpec()
                .accept("application/vnd.xxx.v2+json")
                .header("x-space-key", spaceKey)
                .get("/settings/" + spaceKey + "/language").then()
                .body("code", equalTo(0))
                .body("msg", equalTo("Success"))
                .body("data.active", equalTo(false))
                .body("data.srcPath", equalTo("/"))
                .body("data.type", equalTo("Java"));
    }
}
