package net.xxx.xxx.apitest.workspace.search;

import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import net.xxx.xxx.apitest.Utils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;

/**
 * @program: SearchTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-11-26 15:22
 **/
public class SearchTestCase extends AbstractTestCase {
    Utils utils = new Utils();
    String spaceKey;

    @Before
    public void tearUp() throws InterruptedException {
        spaceKey = utils.createTestDemoWS();
        Thread.sleep(1000);
    }

    @After
    public void tearDown() {
        utils.deleteWorkspaceBySpacekey(spaceKey);
    }

    @Test
    public void testSearch() {
        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .header("x-space-key", spaceKey)
                .formParams(new HashMap<String, String>() {{
                    put("keyword", "");
                    put("includeNonProjectItems", "false");
                }}).post("/workspaces/" + spaceKey + "/search").then()
                .body("path", hasItems("/README.md", "/cli/snake.py", "/cli/hello.py"
                        , "/.gitignore", "/web/app.py", "/web/requirements.txt"))
                .body("find{it.path=='/README.md'}.contentType", equalTo("text/x-web-markdown"))
                .body("find{it.path=='/cli/snake.py'}.contentType", equalTo("text/x-python"))
                .body("find{it.path=='/cli/hello.py'}.contentType", equalTo("text/x-python"))
                .body("find{it.path=='/.gitignore'}.contentType", equalTo("text/plain"))
                .body("find{it.path=='/web/app.py'}.contentType", equalTo("text/x-python"))
                .body("find{it.path=='/web/requirements.txt'}.contentType", equalTo("text/plain"));
    }

}
