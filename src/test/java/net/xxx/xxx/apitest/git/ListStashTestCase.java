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
import static org.hamcrest.CoreMatchers.notNullValue;

/**
 * @program: ListStashTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-12-20 15:04
 **/
public class ListStashTestCase extends AbstractTestCase {
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
        utils.createStash(spaceKey, "this is a stash test");
    }

    @After
    public void tearDown() {
        utils.deleteWorkspaceBySpacekey(spaceKey);
        utils.deleteProject(proName);
    }

    @Test
    public void testListStash() {
        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .header("x-space-key", spaceKey)
                .get("/git/" + spaceKey + "/stash")
                .then()
                .body("stashes[0].message", equalTo("this is a stash test"))
                .body("stashes[0].name", equalTo("stash@{0}"))
                .body("stashes[0].rev", notNullValue());
    }


}
