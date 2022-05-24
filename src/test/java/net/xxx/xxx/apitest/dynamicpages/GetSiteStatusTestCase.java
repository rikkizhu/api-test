package net.xxx.xxx.apitest.dynamicpages;

import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import net.xxx.xxx.apitest.Utils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

/**
 * @program: GetSiteStatusTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-12-03 14:46
 **/
public class GetSiteStatusTestCase extends AbstractTestCase {
    Utils utils = new Utils();
    String spaceKey;
    String wsName = RandomStringUtils.randomAlphanumeric(8);


    @Before
    public void tearUp() {
        spaceKey = utils.createWsWithoutPro(wsName, Steps.WordPress_TemplateId);
    }

    @After
    public void tearDown() throws InterruptedException {
        Thread.sleep(2000);
        utils.deleteWorkspaceBySpacekey(spaceKey);
    }

    @Test
    public void testGetCreatedNotDeployedSiteStatus() {
        utils.createDyPages(spaceKey);
        Steps.getRequestSpec().accept("application/json, text/plain, */*")
                .header("x-space-key", spaceKey)
                .get("/dynamic-pages/" + spaceKey)
                .then()
                .body("deploy_quotas", equalTo(30))
                .body("phpmyadmin", equalTo(Steps.getPhpmyadminUrl()))
                .body("site.created_at", notNullValue())
                .body("site.database_host", equalTo(Steps.getDataBaseHost()))
                .body("site.database_name", containsString("db-"))
                .body("site.database_password", notNullValue())
                .body("site.database_port", equalTo(Steps.getSiteDatabasePort()))
                .body("site.database_space_quota", notNullValue())
                .body("site.database_space_usage", equalTo(0))
                .body("site. database_username", containsString("user-"))
                .body("site.disk_space_quota", notNullValue())
                .body("site.disk_space_usage", equalTo(0))
                .body("site.entry_point", equalTo(""))
                .body("site.force_ssl", equalTo(false))
                .body("site.space_key", equalTo(spaceKey))
                .body("site.suspended", equalTo(false))
                .body("site.updated_at", notNullValue())
                .body("test_domain", containsString(Steps.getPagesSiteDomain()));

        utils.deleteDyPages(spaceKey);
    }

    @Test
    public void testGetNotCreatedSiteTestCase() {
        Steps.getRequestSpec().accept("application/json, text/plain, */*")
                .header("x-space-key", spaceKey)
                .get("/dynamic-pages/" + spaceKey)
                .then()
                .body("code", equalTo(4002))
                .body("msg", equalTo("site not found"));
    }
}
