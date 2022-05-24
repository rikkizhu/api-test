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

/**
 * @program: MergeTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-12-19 14:30
 **/
public class MergeTestCase extends AbstractTestCase {
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
        utils.uploadFile(spaceKey, "test.java", "/", "");
        utils.gitCommitAll(spaceKey, commitMessage);
    }

    @After
    public void tearDown() {
        utils.deleteGeneratedFile();
        utils.deleteWorkspaceBySpacekey(spaceKey);
        utils.deleteProject(proName);
    }

    @Test
    public void testMerge() {
        utils.checkoutBranch(spaceKey, "master");
        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .header("x-space-key", spaceKey)
                .formParams(new HashMap<String, String>() {{
                    put("name", "testbranch");
                }}).post("/git/" + spaceKey + "/merge")
                .then()
                .body("status", equalTo("FAST_FORWARD"))
                .body("success", equalTo(true));
    }

    @Test
    public void testUpToDateMerge() {
        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .header("x-space-key", spaceKey)
                .formParams(new HashMap<String, String>() {{
                    put("name", "testbranch");
                }}).post("/git/" + spaceKey + "/merge")
                .then()
                .body("status", equalTo("ALREADY_UP_TO_DATE"))
                .body("success", equalTo(true));
    }
}
