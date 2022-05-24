package net.xxx.xxx.apitest.git;

import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import net.xxx.xxx.apitest.Utils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.empty;

/**
 * @program: DeleteBranchTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-12-20 14:42
 **/
public class DeleteBranchTestCase extends AbstractTestCase {
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
        utils.checkoutBranch(spaceKey, "master");
    }

    @After
    public void tearDown() {
        utils.deleteProject(proName);
        utils.deleteWorkspaceBySpacekey(spaceKey);
    }

    @Test
    public void testDeleteBranch() {
        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .header("x-space-key", spaceKey)
                .delete("/git/" + spaceKey + "/branches/?branchName=testbranch")
                .then()
                .body("current", equalTo("master"))
                .body("remote", is(empty()))
                .body("local", hasItem("master"));
    }
}
