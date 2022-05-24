package net.xxx.xxx.apitest.workspace.filetree;

import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import net.xxx.xxx.apitest.Utils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @program: RawTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-11-26 15:11
 **/
public class RawTestCase extends AbstractTestCase {
    Utils utils = new Utils();
    String spaceKey;

    @Before
    public void tearUp() {
        spaceKey = utils.createTestDemoWS();
    }

    @After
    public void tearDown() {
        utils.deleteWorkspaceBySpacekey(spaceKey);
    }

    @Test
    public void testRaw() {
        String response = Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .header("x-space-key", spaceKey)
                .get("/workspaces/" + spaceKey + "/raw?path=/README.md&inline=false")
                .then().extract().response().asString();
        Assert.assertNotNull(response);
    }
}
