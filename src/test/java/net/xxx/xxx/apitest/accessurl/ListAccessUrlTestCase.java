package net.xxx.xxx.apitest.accessurl;

import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import net.xxx.xxx.apitest.Utils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.notNullValue;

/**
 * @program: ListAccessUrlTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-11-27 16:31
 **/
public class ListAccessUrlTestCase extends AbstractTestCase {

    Utils utils = new Utils();
    String spaceKey;
    String wsName = RandomStringUtils.randomAlphanumeric(8);

    @Before
    public void tearUp() {
        spaceKey = utils.createWsWithoutPro(wsName, Steps.AI_TemplateId);
        utils.createAccessUrl(spaceKey, "8080");
        utils.createAccessUrl(spaceKey, "8090");
    }

    @After
    public void tearDown() {
        utils.deleteWorkspaceBySpacekey(spaceKey);
    }

    @Test
    public void testListAccessUrl() {
        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .header("x-space-key", spaceKey)
                .get("/hf/" + spaceKey)
                .then()
                .body("port", hasItems(8080, 8090))
                .body("token", notNullValue())
                .body("ttl", notNullValue())
                .body("url", notNullValue())
                .body("workspaceId", notNullValue());
    }

}
