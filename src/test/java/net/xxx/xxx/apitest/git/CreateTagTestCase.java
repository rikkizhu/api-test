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

import static org.hamcrest.core.IsCollectionContaining.hasItem;

/**
 * @program: CreateTagTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-12-18 17:47
 **/
public class CreateTagTestCase extends AbstractTestCase {
    Utils utils = new Utils();
    String spaceKey;
    String proName = RandomStringUtils.randomAlphanumeric(8);
    String commitMessage = "this is a commit message";

    @Before
    public void tearUp() throws IOException {
        utils.createPro(proName);
        spaceKey = utils.createWsByxxxPro(proName);
        utils.uploadFile(spaceKey, "test.py", "/", "");
        utils.gitCommitAll(spaceKey, commitMessage);
    }

    @After
    public void tearDown() {
        utils.deleteGeneratedFile();
        utils.deleteWorkspaceBySpacekey(spaceKey);
        utils.deleteProject(proName);
    }

    @Test
    public void testCreateTag() {
        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .header("x-space-key", spaceKey)
                .formParams(new HashMap<String, String>() {{
                    put("tagName", "tag1");
                    put("ref", "HEAD");
                    put("message", "this is a tag");
                    put("force", "false");
                }}).post("/git/" + spaceKey + "/tags")
                .then()
                .statusCode(204);

        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .header("x-space-key", spaceKey)
                .get("/git/" + spaceKey + "/refs")
                .then()
                .body("name", hasItem("refs/tags/tag1"));
    }

    @Test
    public void testForceCreateTag() {
        utils.createTag(spaceKey, "newTag", "this is a new tag");

        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .header("x-space-key", spaceKey)
                .formParams(new HashMap<String, String>() {{
                    put("tagName", "newTag");
                    put("ref", "HEAD");
                    put("message", "this is a tag");
                    put("force", "true");
                }}).post("/git/" + spaceKey + "/tags")
                .then().statusCode(204);
    }


}
