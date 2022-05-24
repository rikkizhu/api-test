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
 * @program: GetDomainChallengeTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-12-03 11:28
 **/
public class GetDomainChallengeTestCase extends AbstractTestCase {

    String spaceKey;
    String wsName = RandomStringUtils.randomAlphanumeric(8);
    Utils utils = new Utils();


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
    public void testGetDomainChallenge() {
        Response response = Steps.getRequestSpec().header("x-space-key", spaceKey)
                .accept("application/json, text/plain, */*")
                .get("/dynamic-pages/" + spaceKey + "/domain/" + Steps.getPagesTestDomain() + "/challenge")
                .then().extract().response();

        Assert.assertNotNull(response.asString());
    }


}
