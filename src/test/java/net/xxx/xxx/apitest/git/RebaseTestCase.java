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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;

/**
 * @program: RebaseTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-12-20 15:25
 **/
public class RebaseTestCase extends AbstractTestCase {
    Utils utils = new Utils();
    String spaceKey;
    String proName = RandomStringUtils.randomAlphanumeric(8);
    String commitMessage = "this is a commit message";

    @Before
    public void tearUp() throws IOException {
        utils.createPro(proName);
        spaceKey = utils.createWsByxxxPro(proName);
        utils.uploadFile(spaceKey, "test.java", "/", "");
        utils.gitCommitAll(spaceKey, commitMessage);
        utils.createBranch(spaceKey, "master", "testbranch");
        utils.uploadFile(spaceKey, "fileontestbranch.java", "/", "");
        utils.gitCommitAll(spaceKey, commitMessage);
        utils.checkoutBranch(spaceKey, "master");
        utils.uploadFile(spaceKey, "fileonmaster1.java", "/", "");
        utils.gitCommitAll(spaceKey, commitMessage);
        utils.uploadFile(spaceKey, "fileonmaster2.java", "/", "");
        utils.gitCommitAll(spaceKey, commitMessage);
    }

    @After
    public void tearDown() {
        utils.deleteGeneratedFile();
        utils.deleteWorkspaceBySpacekey(spaceKey);
        utils.deleteProject(proName);
    }

    @Test
    public void testRebase() {
        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .header("x-space-key", spaceKey)
                .formParams(new HashMap<String, String>() {{
                    put("branch", "master");
                    put("upstream", "refs/heads/testbranch");
                    put("interactive", "false");
                    put("preserve", "false");
                }}).post("/git/" + spaceKey + "/rebase")
                .then()
                .body("status", equalTo("OK"))
                .body("success", equalTo(true));

        Steps.getRequestSpec()
                .header("x-space-key", spaceKey)
                .accept("application/vnd.xxx.v2+json")
                .get("/workspaces/" + spaceKey + "/files?path=/&order=true&group=true")
                .then()
                .body("name", hasItems("fileonmaster1.java", "fileonmaster2.java",
                        "fileontestbranch.java", "test.java"));
    }

    @Test
    public void testInteractiveRebase() {
        //todo 交互变基部分有问题，修复后再补充用例
    }


}

