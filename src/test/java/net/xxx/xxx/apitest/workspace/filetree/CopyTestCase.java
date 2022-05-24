package net.xxx.xxx.apitest.workspace.filetree;

import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import net.xxx.xxx.apitest.Utils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.number.OrderingComparison.greaterThan;

/**
 * @program: CopyTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-11-23 15:59
 **/
public class CopyTestCase extends AbstractTestCase {
    Utils utils = new Utils();
    String spaceKey;


    @Before
    public void tearUp() throws InterruptedException {
        spaceKey = utils.createTestDemoWS();
        Thread.sleep(1500);
    }

    @After
    public void tearDown() {
        utils.deleteWorkspaceBySpacekey(spaceKey);
    }

    @Test
    public void testCopy() {
        Steps.getRequestSpec().header("x-space-key", spaceKey)
                .accept("application/vnd.xxx.v2+json")
                .formParams(new HashMap<String, String>() {{
                    put("from", "/README.md");
                    put("to", "/web/README.md");
                    put("force", "false");
                }}).post("/workspaces/" + spaceKey + "/copy").then()
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
