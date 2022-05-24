package net.xxx.xxx.apitest.dynamicpages;

import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import net.xxx.xxx.apitest.Utils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsIn.isIn;
import static org.hamcrest.core.IsNull.notNullValue;


/**
 * @program: AddCustomDomainTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-11-30 11:19
 **/
public class AddCustomDomainTestCase extends AbstractTestCase {

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
    public void testAddDNSWrongDomain() {
        Steps.getRequestSpec().accept("application/json, text/plain, */*")
                .header("x-space-key", spaceKey)
                .formParams(new HashMap<String, String>() {{
                    put("domain", "test123.com");
                }})
                .post("/dynamic-pages/" + spaceKey + "/domain").then()
                .body("code", isIn(Arrays.asList(4021,4044)))
                .body("msg", isIn(Arrays.asList("dns resolving error","no valid addr")));
    }

    @Test
    public void testAddRightDomain() {
        Steps.getRequestSpec().accept("application/json, text/plain, */*")
                .header("x-space-key", spaceKey)
                .formParams(new HashMap<String, String>() {{
                    put("domain", Steps.getPagesTestDomain());
                }}).post("/dynamic-pages/" + spaceKey + "/domain").then()
                .body("created_at", notNullValue())
                .body("name", equalTo(Steps.getPagesTestDomain()))
                .body("primary", equalTo(false))
                .body("to_primary", equalTo(false))
                .body("updated_at", notNullValue());
    }

    @Test
    public void testAddInvalidDomain() {
        Steps.getRequestSpec().accept("application/json, text/plain, */*")
                .header("x-space-key", spaceKey)
                .formParams(new HashMap<String, String>() {{
                    put("domain", "abcdefg");
                }}).post("/dynamic-pages/" + spaceKey + "/domain").then()
                .body("code", equalTo(4013))
                .body("msg", equalTo("invalid custom domain"));
    }

    @Test
    public void testAddDuplicatedDomain() {
        utils.bindRightPagesDomain(spaceKey);
        Steps.getRequestSpec().accept("application/json, text/plain, */*")
                .header("x-space-key", spaceKey)
                .formParams(new HashMap<String, String>() {{
                    put("domain", Steps.getPagesTestDomain());
                }}).post("/dynamic-pages/" + spaceKey + "/domain").then()
                .body("code", equalTo(4003))
                .body("msg", equalTo("domain existed"));
    }


}
