package net.xxx.xxx.apitest.git;

import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import net.xxx.xxx.apitest.Utils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * @program: GitCommitTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-12-18 11:46
 **/
public class GitCommitTestCase extends AbstractTestCase {
    Utils utils = new Utils();
    String spaceKey;

    @Before
    public void tearUp() throws IOException {
        spaceKey = utils.createTestDemoWS();
        utils.uploadFile(spaceKey, "test.java", "/", "");
    }

    @After
    public void tearDowm() {
        utils.deleteWorkspaceBySpacekey(spaceKey);
        utils.deleteGeneratedFile();
        utils.deleteAllWs();
    }

    @Test
    public void testGitCommitAll() {
        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .contentType("application/x-www-form-urlencoded")
                .header("x-space-key", spaceKey)
                .formParams(new HashMap<String, String>() {{
                    put("message", "commit api test");
                }}).post("/git/" + spaceKey + "/commits")
                .then()
                .body("contentType[0]", equalTo("text/x-java-source"))
                .body("gitStatus[0]", equalTo("CLEAN"))
                .body("isDir[0]", equalTo(false))
                .body("isSymbolicLink[0]", equalTo(false))
                .body("lastAccessed[0]", notNullValue())
                .body("lastModified[0]", notNullValue())
                .body("name[0]", equalTo("test.java"))
                .body("path[0]", equalTo("/test.java"))
                .body("readable[0]", equalTo(true))
                .body("writable[0]", equalTo(true))
                .body("find{it.name == 'test.java'}.size", equalTo(0));
    }

    @Test
    public void testGitCommit() {
        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .contentType("application/x-www-form-urlencoded")
                .header("x-space-key", spaceKey)
                .formParams(new HashMap<String, String>() {{
                    put("message", "commit api test");
                    put("files[]", "test.java");
                }}).post("/git/" + spaceKey + "/commits")
                .then()
                .body("contentType[0]", equalTo("text/x-java-source"))
                .body("gitStatus[0]", equalTo("CLEAN"))
                .body("isDir[0]", equalTo(false))
                .body("isSymbolicLink[0]", equalTo(false))
                .body("lastAccessed[0]", notNullValue())
                .body("lastModified[0]", notNullValue())
                .body("name[0]", equalTo("test.java"))
                .body("path[0]", equalTo("/test.java"))
                .body("readable[0]", equalTo(true))
                .body("writable[0]", equalTo(true))
                .body("find{it.name == 'test.java'}.size", equalTo(0));
    }
}
