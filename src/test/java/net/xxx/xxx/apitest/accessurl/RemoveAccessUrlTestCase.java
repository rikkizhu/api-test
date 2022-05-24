package net.xxx.xxx.apitest.accessurl;

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
 * @program: RemoveAccessUrlTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-11-27 16:47
 **/
public class RemoveAccessUrlTestCase extends AbstractTestCase {

    Utils utils = new Utils();
    String wsName = RandomStringUtils.randomAlphanumeric(8);
    String spaceKey;

    @Before
    public void tearUp() {
        spaceKey = utils.createWsWithoutPro(wsName, Steps.AI_TemplateId);
        utils.createAccessUrl(spaceKey, "8080");
    }

    @After
    public void tearDown() {
        utils.deleteWorkspaceBySpacekey(spaceKey);
    }

    @Test
    public void testRemoveAccessUrl() {
        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .header("x-space-key", spaceKey)
                .delete("/hf/" + spaceKey + "?port=8080");

        Response response = Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .header("x-space-key", spaceKey)
                .get("/hf/" + spaceKey)
                .then().extract().response();

        Assert.assertTrue(response.asString().equals("[]"));
    }
}
