package net.xxx.xxx.apitest.languagesetting;

import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import net.xxx.xxx.apitest.Utils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @program: net.xxx.xxx.languagesetting.QueryLanguageServerSettings
 * @description:
 * @author: zhuruiqi
 * @create: 2018-12-14 15:06
 **/
public class QueryLanguageServerSettingsTestCase extends AbstractTestCase {
    Utils utils = new Utils();
    String wsName = RandomStringUtils.randomAlphanumeric(8);
    String spaceKey;

    @Before
    public void tearUp() {
        spaceKey = utils.createWsWithoutPro(wsName, Steps.AI_TemplateId);
    }

    @After
    public void tearDown() {
        utils.deleteWorkspaceBySpacekey(spaceKey);
    }

    @Test
    public void testQueryLanguageServerSettings() {
        Steps.getRequestSpec()
                .accept("application/vnd.xxx.v2+json")
                .header("x-space-key", spaceKey)
                .get("/settings/" + spaceKey + "/language")
                .then()
                .body("code", equalTo(0))
                .body("msg", equalTo("Success"));
    }
}
