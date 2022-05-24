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
 * @program: DeleteDynamicPagesTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-11-30 10:31
 **/
public class DeleteDynamicPagesTestCase extends AbstractTestCase {
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
        utils.deleteWorkspaceBySpacekey(spaceKey);
    }

    @Test
    public void testDeleteDynamicPages() {
        Response response = Steps.getRequestSpec().accept("application/json, text/plain, */*")
                .header("x-space-key", spaceKey)
                .delete("/dynamic-pages/" + spaceKey)
                .then()
                .statusCode(204)
                .extract().response();
        Assert.assertNotNull(response);
    }
}
