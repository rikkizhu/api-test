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
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.empty;

/**
 * @program: CreateBranchTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-12-18 17:21
 **/
public class CheckoutTestCase extends AbstractTestCase {
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
    }


    @After
    public void tearDown() {
        utils.deleteGeneratedFile();
        utils.deleteProject(proName);
        utils.deleteWorkspaceBySpacekey(spaceKey);
    }

    @Test
    public void testCheckoutNewBranch() {
        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .header("x-space-key", spaceKey)
                .formParams(new HashMap<String, String>() {{
                    put("name", "testbranch");
                    put("startPoint", "master");
                }}).post("/git/" + spaceKey + "/checkout")
                .then()
                .body("conflictList", is(empty()))
                .body("modifiedList", is(empty()))
                .body("removedList", is(empty()))
                .body("status", equalTo("OK"))
                .body("undeletedList", is(empty()));
    }

    @Test
    public void testCheckoutBranch() {
        utils.createBranch(spaceKey, "master", "testbranch123");
        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .header("x-space-key", spaceKey)
                .formParams(new HashMap<String, String>() {{
                    put("name", "master");
                }}).post("/git/" + spaceKey + "/checkout")
                .then()
                .body("conflictList", is(empty()))
                .body("modifiedList", is(empty()))
                .body("removedList", is(empty()))
                .body("status", equalTo("OK"))
                .body("undeletedList", is(empty()));
    }


    @Test
    public void testCheckoutNotExistBranch() {
        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .header("x-space-key", spaceKey)
                .formParams(new HashMap<String, String>() {{
                    put("name", "notexistbranch");
                }}).post("/git/" + spaceKey + "/checkout")
                .then()
                .body("code", equalTo(500))
                .body("msg", equalTo("Ref notexistbranch cannot be resolved"));
    }
}

