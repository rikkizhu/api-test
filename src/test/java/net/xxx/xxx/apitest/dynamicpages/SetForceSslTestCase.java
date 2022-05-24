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
 * @program: net.xxx.xxx.dynamicpages.SetsetForceSslTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-11-30 11:08
 **/
public class SetForceSslTestCase extends AbstractTestCase {
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
    public void testSetForceSslTrue() {
        Steps.getRequestSpec().accept("application/json, text/plain, */*")
                .header("x-space-key", spaceKey)
                .formParams(new HashMap<String, String>() {{
                    put("force_ssl", "true");
                }})
                .put("/dynamic-pages/" + spaceKey + "/force-ssl")
                .then()
                .statusCode(204);


    }

    @Test
    public void testSetForceSslFalse() {
        Steps.getRequestSpec().accept("application/json, text/plain, */*")
                .header("x-space-key", spaceKey)
                .formParams(new HashMap<String, String>() {{
                    put("force_ssl", "false");
                }})
                .put("/dynamic-pages/" + spaceKey + "/force-ssl")
                .then()
                .statusCode(204);
    }

}
