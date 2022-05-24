package net.xxx.xxx.apitest.workspace.filetree;

import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import net.xxx.xxx.apitest.Utils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.number.OrderingComparison.greaterThan;

/**
 * @program: RemoveTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-11-23 14:22
 **/
public class RemoveTestCase extends AbstractTestCase {
    Utils utils = new Utils();
    String wsName = RandomStringUtils.randomAlphanumeric(8);
    String spceKey;

    @Before
    public void tearUp() throws InterruptedException {
        spceKey = utils.createTestDemoWS();
        Thread.sleep(1500);
    }

    @After
    public void tearDown() {
        utils.deleteWorkspaceBySpacekey(spceKey);
    }

    @Test
    public void testRemoveFile() {
        Steps.getRequestSpec().header("x-space-key", spceKey)
                .accept("application/vnd.xxx.v2+json")
                .delete("/workspaces/" + spceKey + "/files?path=/README.md&recursive=true").then()
                .body("contentType", equalTo("text/x-web-markdown"))
                .body("gitStatus", equalTo("CLEAN"))
                .body("isDir", equalTo(false))
                .body("isSymbolicLink", equalTo(false))
                .body("name", equalTo("README.md"))
                .body("path", equalTo("/README.md"))
                .body("readable", equalTo(true))
                .body("size", greaterThan(2))
                .body("writable", equalTo(true))
                .body("lastAccessed", notNullValue())
                .body("lastModified", notNullValue());
    }


}
