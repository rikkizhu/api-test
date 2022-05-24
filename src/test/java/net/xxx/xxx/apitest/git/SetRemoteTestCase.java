package net.xxx.xxx.apitest.git;

import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import net.xxx.xxx.apitest.Utils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @program: net.xxx.xxx.git.setRemoteTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-12-18 11:02
 **/
public class SetRemoteTestCase extends AbstractTestCase {
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
    public void testSetRemote() {
        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .header("x-space-key", spaceKey)
                .formParams(new HashMap<String, String>() {{
                    put("url", "git@git.dev.xxx.com:qxxxtest/TestDemo.git");
                }}).post("/git/" + spaceKey + "/remote")
                .then()
                .body("code", equalTo(0))
                .body("msg", equalTo("Success"));

    }
}
