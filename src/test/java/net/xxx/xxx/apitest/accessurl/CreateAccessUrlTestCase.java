package net.xxx.xxx.apitest.accessurl;

import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import net.xxx.xxx.apitest.Utils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

/**
 * @program: CreateAccessUrlTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-11-27 16:08
 **/
public class CreateAccessUrlTestCase extends AbstractTestCase {

    Utils utils = new Utils();
    String wsName = RandomStringUtils.randomAlphanumeric(8);
    String spaceKey;

    @Before
    public void tearUp() {
        spaceKey = utils.createWsWithoutPro(wsName, Steps.AI_TemplateId);
    }

    @After
    public void tearDown() {
        utils.deleteWorkspaceBySpacekey(spaceKey);
    }

    @Test
    public void testCreateAccessUrl() {
        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .header("x-space-key", spaceKey)
                .param("port", "8080")
                .post("/hf/" + spaceKey)
                .then()
                .body("port", equalTo(8080))
                .body("token", notNullValue())
                .body("ttl", equalTo(3600))
                .body("url", containsString("-8080-"))
                .body("workspaceId", notNullValue());
    }


}
