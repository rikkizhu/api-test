package net.xxx.xxx.apitest.workspace.ws;

import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import net.xxx.xxx.apitest.Utils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;


/**
 * @program: FindxxxProjectWorkspaceTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-11-26 16:54
 **/
public class FindxxxProjectWorkspaceTestCase extends AbstractTestCase {
    Utils utils = new Utils();
    String spaceKey;
    String proName = RandomStringUtils.randomAlphanumeric(8);


    @Before
    public void tearUp() {
        utils.createWsByxxxPro(proName);
        spaceKey = utils.createWsByxxxPro(proName);
    }

    @After
    public void tearDown() {
        utils.deleteWorkspaceBySpacekey(spaceKey);
    }


    @Test
    public void testFindxxxProjectWorkspace() {
        Steps.getRequestSpec()
                .get("/ws/find/xxx/" + Steps.ownerName + "/" + proName)
                .then()
                .body("code", equalTo(0));
    }


}
