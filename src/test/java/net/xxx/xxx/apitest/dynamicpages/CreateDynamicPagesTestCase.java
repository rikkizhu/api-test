package net.xxx.xxx.apitest.dynamicpages;

import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import net.xxx.xxx.apitest.Utils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * @program: CreateDynamicPagesTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-11-30 10:00
 **/
public class CreateDynamicPagesTestCase extends AbstractTestCase {

    Utils utils = new Utils();
    String wsName = RandomStringUtils.randomAlphanumeric(8);
    String spaceKey;

    @Before
    public void tearUp() {
        spaceKey = utils.createWsWithoutPro(wsName, Steps.WordPress_TemplateId);
    }

    @After
    public void tearDown() throws InterruptedException {
        Thread.sleep(2000);
        utils.deleteDyPages(spaceKey);
        utils.deleteWorkspaceBySpacekey(spaceKey);
    }

    @Test
    public void testCreateDynamicPages() {
        Steps.getRequestSpec().header("x-space-key", spaceKey)
                .accept("application/json, text/plain, */*")
                .post("/dynamic-pages/" + spaceKey)
                .then()
                .body("created_at", notNullValue())
                .body("database_name", containsString("db-"))
                .body("database_password", notNullValue())
                .body("database_space_quota", notNullValue())
                .body("database_space_usage", equalTo(0))
                .body("database_username", containsString("user-"))
                .body("entry_point", equalTo(""))
                .body("force_ssl", equalTo(false))
                .body("space_key", equalTo(spaceKey))
                .body("suspended", equalTo(false))
                .body("updated_at", notNullValue());
    }

}
