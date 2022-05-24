package net.xxx.xxx.apitest.project;

import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import net.xxx.xxx.apitest.Utils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static org.hamcrest.CoreMatchers.*;

/**
 * @program: SynProjectTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-10-31 14:18
 **/
public class SynProjectTestCase extends AbstractTestCase {
    Utils utils = new Utils();
    String projectName = RandomStringUtils.randomAlphanumeric(8);

    @Before
    public void tearUp() {
        utils.createPro(projectName);
    }

    @After
    public void tearDown() {
        utils.deleteProject(projectName);
    }

    @Test
    public void testSyncProjects() {
        Steps.getRequestSpec().post("/project/sync");
        Steps.getRequestSpec().get("/projects").then()
                .body("data.name", hasItem(projectName));
    }


}
