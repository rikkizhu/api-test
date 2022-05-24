package net.xxx.xxx.apitest.workspace.filetree;

import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import net.xxx.xxx.apitest.Utils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.number.OrderingComparison.greaterThan;

/**
 * @program: ListFilesTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-11-23 09:37
 **/
public class ListFilesTestCase extends AbstractTestCase {

    Utils utils = new Utils();
    String WsName = RandomStringUtils.randomAlphanumeric(8);
    String spaceKey;

    @Before
    public void tearUp() throws InterruptedException {
        spaceKey = utils.createTestDemoWS();
        utils.openWorkspace(spaceKey);
        Thread.sleep(1000);
    }

    @After
    public void tearDown() {
        utils.deleteWorkspaceBySpacekey(spaceKey);
    }

    @Test
    public void testListFiles() {
        Steps.getRequestSpec()
                .header("x-space-key", spaceKey)
                .accept("application/vnd.xxx.v2+json")
                .get("/workspaces/" + spaceKey + "/files?path=/&order=true&group=true")
                .then()
                // 验证文件列表
                .body("path", hasItems("/cli", "/web", "/.gitignore", "/README.md"))
                // 验证文件夹各属性
                .body("find{it.path == '/cli'}.directoriesCount", equalTo(0))
                .body("find{it.path == '/cli'}.filesCount", equalTo(2))
                .body("find{it.path == '/cli'}.gitStatus", equalTo("NONE"))
                .body("find{it.path == '/cli'}.isDir", equalTo(true))
                .body("find{it.path == '/cli'}.isSymbolicLink", equalTo(false))
                .body("find{it.path == '/cli'}.name", equalTo("cli"))
                .body("find{it.path == '/cli'}.readable", equalTo(true))
                .body("find{it.path == '/cli'}.size", equalTo(4096))
                .body("find{it.path == '/cli'}.writable", equalTo(true))
                .body("find{it.path == '/cli'}.lastAccessed", notNullValue())
                .body("find{it.path == '/cli'}.lastModified", notNullValue())
                // 验证文件各属性
                .body("find{it.path == '/README.md'}.contentType", equalTo("text/x-web-markdown"))
                .body("find{it.path == '/README.md'}.gitStatus", equalTo("CLEAN"))
                .body("find{it.path == '/README.md'}.isDir", equalTo(false))
                .body("find{it.path == '/README.md'}.isSymbolicLink", equalTo(false))
                .body("find{it.path == '/README.md'}.name", equalTo("README.md"))
                .body("find{it.path == '/README.md'}.readable", equalTo(true))
                .body("find{it.path == '/README.md'}.size", greaterThan(2))
                .body("find{it.path == '/README.md'}.writable", equalTo(true))
                .body("find{it.path == '/README.md'}.lastAccessed", notNullValue())
                .body("find{it.path == '/README.md'}.lastModified", notNullValue());
    }


}
