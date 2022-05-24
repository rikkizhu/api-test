package net.xxx.xxx.apitest.git;

import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import net.xxx.xxx.apitest.Utils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @program: GitFetchTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-12-20 11:37
 **/
public class GitFetchTestCase extends AbstractTestCase {
    Utils utils = new Utils();
    String spaceKey;
    String proName = RandomStringUtils.randomAlphanumeric(8);

    @Before
    public void tearUp() {
        utils.createPro(proName);
        spaceKey = utils.createWsByxxxPro(proName);
    }

    @After
    public void tearDown() {
        utils.deleteWorkspaceBySpacekey(spaceKey);
        utils.deleteProject(proName);
    }

    @Test
    public void testGitFetch() {
        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .header("x-space-key", spaceKey)
                .post("/git/" + spaceKey + "/fetch")
                .then()
                .statusCode(204);
    }
}
