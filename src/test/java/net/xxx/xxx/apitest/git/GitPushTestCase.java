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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * @program: net.xxx.xxx.git.GitSyncTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-12-18 15:31
 **/
public class GitPushTestCase extends AbstractTestCase {
    Utils utils = new Utils();
    String spaceKey;
    String proName = RandomStringUtils.randomAlphanumeric(8);
    String commitMessage = "this is a commit message";

    @Before
    public void tearUp() {
        utils.createPro(proName);
        spaceKey = utils.createWsByxxxPro(proName);
    }

    @After
    public void tearDown() {
        utils.deleteGeneratedFile();
        utils.deleteWorkspaceBySpacekey(spaceKey);
        utils.deleteProject(proName);
    }

    @Test
    public void testGitPush() throws IOException {
        utils.uploadFile(spaceKey, "test.java", "/", "");
        utils.gitCommitAll(spaceKey, commitMessage);
        utils.createBranch(spaceKey, "master", "testbranch");
        utils.createTag(spaceKey, "tagtest", "this is a ag test");
        utils.checkoutBranch(spaceKey, "master");
        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .header("x-space-key", spaceKey)
                .post("/git/" + spaceKey + "/push")
                .then()
                .body("nothingToPush", equalTo(false))
                .body("ok", equalTo(true))
                .body("updates.remoteRefName", hasSize(1))
                .body("updates.remoteRefName", hasItem("origin/master"))
                .body("updates.localRefName", hasSize(1))
                .body("updates.localRefName", hasItem("master"))
                .body("updates.status", hasSize(1))
                .body("updates.status", hasItem("OK"));
    }

    @Test
    public void testGitPushAll() throws IOException {
        utils.uploadFile(spaceKey, "test.java", "/", "");
        utils.gitCommitAll(spaceKey, commitMessage);
        utils.createBranch(spaceKey, "master", "testbranch");
        utils.createTag(spaceKey, "tagtest", "this is a tag test");
        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .header("x-space-key", spaceKey)
                .formParams(new HashMap<String, String>() {{
                    put("all", "true");
                }}).post("/git/" + spaceKey + "/push?all=true")
                .then()
                .body("nothingToPush", equalTo(false))
                .body("ok", equalTo(true))
                .body("updates.localRefName", hasItems("master", "testbranch", "tagtest"))
                .body("updates.remoteRefName", hasItems("origin/master", "origin/testbranch", "origin/tagtest"))
                .body("updates.find{it.localRefName == 'master'}.status", equalTo("OK"))
                .body("updates.find{it.localRefName == 'testbranch'}.status", equalTo("OK"))
                .body("updates.find{it.localRefName == 'tagtest'}.status", equalTo("OK"));
    }

    @Test
    public void testGitPushNothing() {
        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .header("x-space-key", spaceKey)
                .formParams(new HashMap<String, String>() {{
                    put("all", "true");
                }}).post("/git/" + spaceKey + "/push?all=true")
                .then()
                .body("code", equalTo(500))
                .body("msg", equalTo("Nothing to push."));
    }

    @Test
    public void testGetPushCommits() throws IOException {
        utils.uploadFile(spaceKey, "test.java", "/", "");
        utils.gitCommitAll(spaceKey, commitMessage);

        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .header("x-space-key", spaceKey)
                .get("/git/" + spaceKey + "/push")
                .then()
                .body("localRef", equalTo("master"))
                .body("remote", equalTo("origin"))
                .body("remoteRef", equalTo("+master"))
                .body("commits[0].shortMessage", equalTo("this is a commit message"))
                .body("commits[0].fullMessage", equalTo("this is a commit message"))
                .body("commits[0].sha", notNullValue())
                .body("commits[0].diffEntries[0].changeType", equalTo("ADD"))
                .body("commits[0].diffEntries[0].oldPath", equalTo("/dev/null"))
                .body("commits[0].diffEntries[0].newPath", equalTo("test.java"));
    }
}

