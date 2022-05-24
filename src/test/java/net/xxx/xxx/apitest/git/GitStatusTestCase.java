package net.xxx.xxx.apitest.git;


import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import net.xxx.xxx.apitest.Utils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @program: GitStatusTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-12-17 15:35
 **/
public class GitStatusTestCase extends AbstractTestCase {
    Utils utils = new Utils();
    String spaceKey1;
    String spaceKey2;
    String wsName = RandomStringUtils.randomAlphanumeric(8);

    @Before
    public void tearUp() {
        spaceKey1 = utils.createTestDemoWS();
        spaceKey2 = utils.createWsWithoutPro(wsName, Steps.AI_TemplateId);
    }

    @After
    public void tearDown() {
        utils.deleteGeneratedFile();
        utils.deleteAllWs();
    }

    @Test
    public void testWithProWsGitStatus() {
        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .header("x-space-key", spaceKey1)
                .get("/git/" + spaceKey1)
                .then()
                .body("files", is(empty()))
                .body("clean", equalTo(true));
    }

    @Test
    public void testWithoutProWsGitStatus() throws InterruptedException {
        Thread.sleep(1500);
        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .header("x-space-key", spaceKey2)
                .get("/git/" + spaceKey2)
                .then()
                .body("files.name", hasItems("MLDemo.ipynb", "README.md"))
                .body("clean", equalTo(false))
                .body("files.find{it.name == 'MLDemo.ipynb'}.status", equalTo("UNTRACKED"))
                .body("files.find{it.name == 'README.md'}.status", equalTo("UNTRACKED"));
    }

    @Test
    public void testAddFileGitStatus() throws IOException {
        utils.uploadFile(spaceKey1, "test.java", "/", "");
        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .header("x-space-key", spaceKey1)
                .get("/git/" + spaceKey1)
                .then()
                .body("files.name", hasItem("test.java"))
                .body("clean", equalTo(false))
                .body("files.find{it.name=='test.java'}.status", equalTo("UNTRACKED"));
    }
}
