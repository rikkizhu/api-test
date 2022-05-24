package net.xxx.xxx.apitest.workspace.search;

import io.restassured.http.ContentType;
import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import net.xxx.xxx.apitest.Utils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;

/**
 * @program: SearchTextTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-11-26 15:39
 **/
public class SearchTextTestCase extends AbstractTestCase {
    Utils utils = new Utils();
    String spaceKey;

    @Before
    public void tearUp() {
        spaceKey = utils.createTestDemoWS();
    }

    @After
    public void tearDown() {
        utils.deleteWorkspaceBySpacekey(spaceKey);
    }

    @Test
    public void testSearchText() {
        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .header("x-space-key", spaceKey)
                .contentType(ContentType.JSON)
                .body("{\"keyword\":\"cli\"}")
                .post("/workspaces/" + spaceKey + "/txt-search")
                .then().body("taskId", containsString(spaceKey + "-cli-"));
    }
}
