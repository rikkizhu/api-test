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
import static org.hamcrest.CoreMatchers.notNullValue;

/**
 * @program: CreateStashTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-12-20 14:58
 **/
public class CreateStashTestCase extends AbstractTestCase {
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
        utils.uploadFile(spaceKey, "test.java", "/", "this is a new line");
    }

    @After
    public void tearDown() {
        utils.deleteProject(proName);
        utils.deleteWorkspaceBySpacekey(spaceKey);
    }

    @Test
    public void testCreateStash() {
        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .header("x-space-key", spaceKey)
                .formParams(new HashMap<String, String>() {{
                    put("message", "this is a stash test");
                }}).post("/git/" + spaceKey + "/stash")
                .then()
                .statusCode(204);

        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .header("x-space-key", spaceKey)
                .get("/git/" + spaceKey + "/stash")
                .then()
                .body("stashes[0].message", equalTo("this is a stash test"))
                .body("stashes[0].name", equalTo("stash@{0}"))
                .body("stashes[0].rev", notNullValue());
    }

}
