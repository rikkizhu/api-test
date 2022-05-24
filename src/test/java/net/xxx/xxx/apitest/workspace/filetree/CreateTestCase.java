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

/**
 * @program: net.xxx.xxx.workspace.filetree.CreateDynamicPagesTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-11-23 11:38
 **/
public class CreateTestCase extends AbstractTestCase {

    Utils utils = new Utils();
    String wsNmae = RandomStringUtils.randomAlphanumeric(8);
    String spaceKey;
    String fileName = RandomStringUtils.randomAlphanumeric(6);


    @Before
    public void tearUp() {
        spaceKey = utils.createTestDemoWS();

    }

    @After
    public void tearDown() {
        utils.deleteWorkspaceBySpacekey(spaceKey);
    }

    @Test
    public void testCreateFile() {

        Steps.getRequestSpec().header("x-space-key", spaceKey)
                .accept("application/vnd.xxx.v2+json")
                .param("path", "/test.java")
                .post("/workspaces/" + spaceKey + "/files").then()
                .body("contentType", equalTo("text/x-java-source"))
                .body("gitStatus", equalTo("UNTRACKED"))
                .body("isDir", equalTo(false))
                .body("isSymbolicLink", equalTo(false))
                .body("name", equalTo("test.java"))
                .body("readable", equalTo(true))
                .body("size", equalTo(0))
                .body("writable", equalTo(true))
                .body("lastAccessed", notNullValue())
                .body("lastModified", notNullValue());
    }

    @Test
    public void testCreateDuplicatedFile() {
        utils.createFile(spaceKey, fileName);
        Steps.getRequestSpec().header("x-space-key", spaceKey)
                .accept("application/vnd.xxx.v2+json")
                .param("path", "/" + fileName)
                .post("/workspaces/" + spaceKey + "/files").then()
                .body("code", equalTo(-1))
                .body("msg", equalTo(String.format("/%s is already exists!", fileName)));
    }


}
