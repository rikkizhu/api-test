package net.xxx.xxx.apitest.dynamicpages;

import io.restassured.response.Response;
import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import net.xxx.xxx.apitest.Utils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @program: GetDeployStatusTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-12-03 15:12
 **/
public class GetDeployStatusTestCase extends AbstractTestCase {

    Utils utils = new Utils();
    String wsName = RandomStringUtils.randomAlphanumeric(8);
    String spceKey;

    @Before
    public void tearUp() {
        spceKey = utils.createWsWithoutPro(wsName, Steps.WordPress_TemplateId);
        utils.createDyPages(spceKey);
    }

    @After
    public void tearDown() throws InterruptedException {
        Thread.sleep(2000);
        utils.deleteDyPages(spceKey);
        utils.deleteWorkspaceBySpacekey(spceKey);
    }

    @Test
    public void testGetDeployStatus() {
        Response response = Steps.getRequestSpec().accept("application/json, text/plain, */*")
                .header("x-space-key", spceKey)
                .get("/dynamic-pages/" + spceKey + "/deploy");

        Assert.assertEquals("null", response.asString());
    }
}
