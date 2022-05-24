package net.xxx.xxx.apitest.workspace.filetree;


import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import net.xxx.xxx.apitest.Utils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.number.OrderingComparison.greaterThan;


/**
 * @program: UploadTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-11-23 16:20
 **/
public class UploadTestCase extends AbstractTestCase {
    Utils utils = new Utils();
    String spaceKey;
    String filePath;
    String fileName = RandomStringUtils.randomAlphanumeric(6) + ".java";

    @Before
    public void tearUp() throws IOException {
        filePath = utils.generateFile(fileName, "");
        spaceKey = utils.createTestDemoWS();
        utils.openWorkspace(spaceKey);
    }

    @After
    public void teardown() {
        utils.deleteWorkspaceBySpacekey(spaceKey);
        utils.deleteGeneratedFile();
    }

    @Test
    public void testUpload() {
        Steps.getRequestSpec().header("x-space-key", spaceKey)
                .header("content-type", "multipart/form-data")
                .accept("application/vnd.xxx.v2+json")
                .formParams(new HashMap<String, String>() {{
                    put("path", "/web");
                }})
                .multiPart("files", new File(filePath))
                .post("/workspaces/" + spaceKey + "/upload").then()
                .body("contentType", hasItem("text/x-java-source"))
                .body("gitStatus", hasItem("UNTRACKED"))
                .body("isDir", hasItem(false))
                .body("isSymbolicLink", hasItem(false))
                .body("name", hasItem(fileName))
                .body("path", hasItem("/web/" + fileName))
                .body("readable", hasItem(true))
                .body("size", greaterThan(-1))
                .body("writable", hasItem(true))
                .body("lastAccessed", notNullValue())
                .body("lastModified", notNullValue());

    }
}
