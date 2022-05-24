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

/**
 * @program: DeleteConflictFileTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-12-20 10:40
 **/
public class DeleteConflictFileTestCase extends AbstractTestCase {
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
        utils.createBranch(spaceKey, "master", "testbranch");
        utils.uploadFile(spaceKey, "test.java", "/", "file on testbranch");
        utils.gitCommitAll(spaceKey, commitMessage);
        utils.checkoutBranch(spaceKey, "master");
        utils.uploadFile(spaceKey, "test.java", "/", "file on master");
        utils.gitCommitAll(spaceKey, commitMessage);
        utils.gitMerge(spaceKey, "testbranch");
    }

    @After
    public void tearDown() {
        utils.deleteGeneratedFile();
        utils.deleteWorkspaceBySpacekey(spaceKey);
        utils.deleteProject(proName);
    }

    @Test
    public void testDeleteConflictFile() throws IOException {
        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .header("x-space-key", spaceKey)
                .formParams(new HashMap<String, String>() {{
                    put("path", "test.java");
                }}).delete("/git/" + spaceKey + "/conflicts?path=test.java")
                .then()
                .statusCode(204);


    }
}
