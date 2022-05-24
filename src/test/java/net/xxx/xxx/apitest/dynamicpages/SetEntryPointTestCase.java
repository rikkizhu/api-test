package net.xxx.xxx.apitest.dynamicpages;

import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import net.xxx.xxx.apitest.Utils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

/**
 * @program: SetEntryPointTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-12-03 15:34
 **/
public class SetEntryPointTestCase extends AbstractTestCase {

    Utils utils = new Utils();
    String spaceKey;
    String wsName = RandomStringUtils.randomAlphanumeric(8);

    @Before
    public void tearUp() {
        spaceKey = utils.createWsWithoutPro(wsName, Steps.WordPress_TemplateId);
        utils.createDyPages(spaceKey);
    }

    @After
    public void tearDown() throws InterruptedException {
        Thread.sleep(2000);
        utils.deleteDyPages(spaceKey);
        utils.deleteWorkspaceBySpacekey(spaceKey);
    }

    @Test
    public void testSetEntryPoint() {
        Steps.getRequestSpec().accept("application/json, text/plain, */*")
                .header("x-space-key", spaceKey)
                .formParams(new HashMap<String, String>() {{
                    put("entry_point", "/wp-includes");
                }}).put("/dynamic-pages/" + spaceKey + "/entry-point")
                .then()
                .statusCode(204);
    }
}
