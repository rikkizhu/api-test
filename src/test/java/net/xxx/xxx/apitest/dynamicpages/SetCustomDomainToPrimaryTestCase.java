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
 * @program: SetCustomDomainToPrimaryTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-12-03 13:58
 **/
public class SetCustomDomainToPrimaryTestCase extends AbstractTestCase {


    Utils utils = new Utils();
    String wsName = RandomStringUtils.randomAlphanumeric(8);
    String spaceKey;


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
    public void testSetCustomDomainToPrimary() {

        Steps.getRequestSpec().accept("application/json, text/plain, */*")
                .header("x-space-key", spaceKey)
                .formParams(new HashMap<String, String>() {{
                    put("domain", Steps.getPagesTestDomain());
                }}).post("/dynamic-pages/" + spaceKey + "/domain");


        Steps.getRequestSpec().accept("application/json, text/plain, */*")
                .header("x-space-key", spaceKey)
                .formParams(new HashMap<String, String>() {{
                    put("domain", Steps.getPagesTestDomain());
                }}).put("/dynamic-pages/" + spaceKey + "/domain/" + Steps.getPagesTestDomain() + "/primary")
                .then().statusCode(204);
    }
}
