package net.xxx.xxx.apitest.dynamicpages;

import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import net.xxx.xxx.apitest.Utils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @program: DeleteCustomDomainTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-11-30 11:58
 **/
public class DeleteCustomDomainTestCase extends AbstractTestCase {
    Utils utils = new Utils();
    String wsName = RandomStringUtils.randomAlphanumeric(8);
    String spaceKey;

    @Before
    public void tearUp() {
        spaceKey = utils.createWsWithoutPro(wsName, Steps.WordPress_TemplateId);
        utils.createDyPages(spaceKey);
        utils.bindRightPagesDomain(spaceKey);
    }

    @After
    public void tearDown() throws InterruptedException {
        Thread.sleep(2000);
        utils.deleteDyPages(spaceKey);
        utils.deleteWorkspaceBySpacekey(spaceKey);
    }

    @Test
    public void testDeleteCustomDomain() {
        Steps.getRequestSpec().accept("application/json, text/plain, */*")
                .header("x-space-key", spaceKey)
                .delete("dynamic-pages/" + spaceKey + "/domain/" + Steps.getPagesTestDomain())
                .then()
                .statusCode(204);
    }
}
