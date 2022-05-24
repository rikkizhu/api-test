package net.xxx.xxx.apitest.git;

import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import net.xxx.xxx.apitest.Utils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.hamcrest.Matchers.containsString;

/**
 * @program: GitDiffTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-12-19 18:33
 **/
public class GitDiffTestCase extends AbstractTestCase {
    Utils utils = new Utils();
    String spaceKey;
    String proName = RandomStringUtils.randomAlphanumeric(8);
    String commitMessage = "this is a commit message";

    @Before
    public void tearup() throws IOException {
        utils.createPro(proName);
        spaceKey = utils.createWsByxxxPro(proName);
        utils.uploadFile(spaceKey, "test.py", "/", "");
        utils.gitCommitAll(spaceKey, commitMessage);
        utils.uploadFile(spaceKey, "test.java", "/", "");
    }

    @After
    public void tearDown() {
        utils.deleteGeneratedFile();
        utils.deleteWorkspaceBySpacekey(spaceKey);
        utils.deleteProject(proName);
    }

    @Test
    public void testGitDiff() {
        Steps.getRequestSpec().accept("application/vnd.xxx.v2.diff+json")
                .formParams(new HashMap<String, String>() {{
                    put("path", "/test.java");
                    put("oldRef", "~~unstaged~~");
                    put("newRef", "HEAD");
                }}).get("/git/" + spaceKey + "/commits?path=%2Ftest.java&oldRef=~~unstaged~~&newRef=HEAD")
                .then()
                .body("diff", containsString("diff --git a/test.java b/test.java"));
    }

}
