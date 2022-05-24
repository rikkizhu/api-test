package net.xxx.xxx.apitest.workspace.filetree;

import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import net.xxx.xxx.apitest.Utils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.equalTo;

/**
 * @program: MkdirTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-11-26 10:13
 **/
public class MkdirTestCase extends AbstractTestCase {

    Utils utils = new Utils();
    String spaceKey;
    String wsName = RandomStringUtils.randomAlphanumeric(8);


    @Before
    public void tearUP() {
        spaceKey = utils.createWsWithoutPro(wsName, Steps.AI_TemplateId);
    }


    @After
    public void tearDown() {
        utils.deleteWorkspaceBySpacekey(spaceKey);
    }

    @Test
    public void testMkdir() {
        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .header("x-space-key", spaceKey)
                .formParams(new HashMap<String, String>() {{
                    put("path", "/testfolder");
                }}).post("/workspaces/" + spaceKey + "/mkdir")
                .then()
                .body("path", equalTo("/testfolder"))
                .body("spaceKey", equalTo(spaceKey));
    }

    @Test
    public void testMkdirDuplicatedFolder() {
        utils.mkdirFolderinFileTree(spaceKey, "/test");

        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .header("x-space-key", spaceKey)
                .formParams(new HashMap<String, String>() {{
                    put("path", "/test");
                }}).post("/workspaces/" + spaceKey + "/mkdir")
                .then()
                .body("code", equalTo(-1))
                .body("msg", equalTo("/test directory already exists!"));
    }


}
