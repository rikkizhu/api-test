package net.xxx.xxx.apitest.project;

import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import net.xxx.xxx.apitest.Utils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;

/**
 * @program: GetProjectsTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-11-05 16:05
 **/
public class GetProjectsTestCase extends AbstractTestCase {
    Utils utils = new Utils();
    String projectName1 = RandomStringUtils.randomAlphanumeric(8);
    String projectName2 = RandomStringUtils.randomAlphabetic(8);

    @Before
    public void tearUp() {
        utils.createPro(projectName1);
        utils.createPro(projectName2);
        utils.synPro();
    }

    @After
    public void tearDown() {
        utils.deleteProject(projectName1);
        utils.deleteProject(projectName2);
    }


    @Test
    public void testGetProjects() {
        Steps.getRequestSpec().get("/projects").then()
                .body("code", equalTo(0))
                .body("data.name", hasItem(projectName1))
                .body("data.name", hasItem(projectName2));
    }
}
