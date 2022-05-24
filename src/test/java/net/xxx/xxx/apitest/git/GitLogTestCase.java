package net.xxx.xxx.apitest.git;

import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import net.xxx.xxx.apitest.Utils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasSize;


/**
 * @program: GitLogTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-12-21 11:22
 **/
public class GitLogTestCase extends AbstractTestCase {
    Utils utils = new Utils();
    String spaceKey;
    String proName = RandomStringUtils.randomAlphanumeric(8);

    @Before
    public void tearUp() throws IOException {
        utils.createPro(proName);
        spaceKey = utils.createWsByxxxPro(proName);
        utils.uploadFile(spaceKey, "test1.java", "/", "test1java");
        utils.gitCommitAll(spaceKey, "commit1");
        utils.uploadFile(spaceKey, "test2.java", "/", "test2java");
        utils.gitCommitAll(spaceKey, "commit2");
        utils.uploadFile(spaceKey, "test3.java", "/", "test3java");
        utils.gitCommitAll(spaceKey, "commit3");
    }

    @After
    public void tearDown() {
        utils.deleteWorkspaceBySpacekey(spaceKey);
        utils.deleteProject(proName);
    }


    @Test
    public void testGitLog() {
        Steps.getRequestSpec().header("x-space-key", spaceKey)
                .accept("application/vnd.xxx.v2+json")
                .get("/git/" + spaceKey + "/logs?size=30&page=0")
                .then()
                .body("shortMessage[0]", equalTo("commit3"))
                .body("shortMessage[1]", equalTo("commit2"))
                .body("shortMessage[2]", equalTo("commit1"))
                .body("shortName", hasSize(3))
                .body("authorxxxnt.name", hasItems("qxxxtest", "qxxxtest", "qxxxtest"))
                .body("commitTime", hasSize(3));

    }
}
