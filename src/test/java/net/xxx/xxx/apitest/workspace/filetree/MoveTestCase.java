package net.xxx.xxx.apitest.workspace.filetree;

import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import net.xxx.xxx.apitest.Utils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.put;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.number.OrderingComparison.greaterThan;

/**
 * @program: MoveTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-11-23 14:48
 **/
public class MoveTestCase extends AbstractTestCase {

    Utils utils = new Utils();
    String wsName = RandomStringUtils.randomAlphanumeric(8);
    String spaceKey;

    @Before
    public void tearUp() throws InterruptedException {
        spaceKey = utils.createWsWithoutPro(wsName, Steps.PythonDemo_TemplateId);
        Thread.sleep(1000 );
    }

    @After
    public void tearDown() {
        utils.deleteWorkspaceBySpacekey(spaceKey);
    }

    @Test
    public void testMoveFile() {
        Steps.getRequestSpec().header("x-space-key", spaceKey)
                .accept("application/vnd.xxx.v2+json")
                .formParams(new HashMap<String, String>() {{
                    put("from", "/README.md");
                    put("to", "/web/README.md");
                    put("force", "false");
                }}).post("/workspaces/" + spaceKey + "/move").then()
                .body("contentType", equalTo("text/x-web-markdown"))
                .body("gitStatus", equalTo("UNTRACKED"))
                .body("isDir", equalTo(false))
                .body("isSymbolicLink", equalTo(false))
                .body("name", equalTo("README.md"))
                .body("path", equalTo("/web/README.md"))
                .body("readable", equalTo(true))
                .body("size", greaterThan(2))
                .body("writable", equalTo(true))
                .body("lastAccessed", notNullValue())
                .body("lastModified", notNullValue());
    }
}




