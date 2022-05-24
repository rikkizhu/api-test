package net.xxx.xxx.apitest.workspace.filetree;

import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import net.xxx.xxx.apitest.Utils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.core.IsNull.notNullValue;

/**
 * @program: PackTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-11-26 14:41
 **/
public class PackTestCase extends AbstractTestCase {

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
    public void testPack() {
        String response = Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .header("x-space-key", spaceKey)
                .formParams(new HashMap<String, String>() {{
                    put("path", "/web");
                    put("inline", "false");
                }}).get("/workspaces/" + spaceKey + "/pack?path=/web&inline=false")
                .then()
                .extract().response().asString();
        Assert.assertNotNull(response);
    }

}
