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
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsEmptyCollection.empty;

/**
 * @program: GetBranchesTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-12-20 14:30
 **/
public class GetBranchesTestCase extends AbstractTestCase {
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
    }

    @After
    public void tearDown() {
        utils.deleteWorkspaceBySpacekey(spaceKey);
        utils.deleteProject(proName);
    }

    @Test
    public void testGetBranches() {
        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .header("x-space-key", spaceKey)
                .get("git/" + spaceKey + "/branches")
                .then()
                .body("current", equalTo("testbranch"))
                .body("remote", is(empty()))
                .body("local", hasItems("master", "testbranch"));

    }
}
