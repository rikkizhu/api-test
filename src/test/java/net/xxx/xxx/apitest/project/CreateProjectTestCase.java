package net.xxx.xxx.apitest.project;

import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import net.xxx.xxx.apitest.Utils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.equalTo;

/**
 * @program: CreateProjectTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-11-05 15:18
 **/
public class CreateProjectTestCase extends AbstractTestCase {
    Utils utils = new Utils();
    String projectName = RandomStringUtils.randomAlphanumeric(8);

    @Test
    public void testCreateProject() {
        Steps.getRequestSpec().formParams(new HashMap<String, String>() {{
            put("type", "2");
            put("gitEnabled", "true");
            put("gitReadmeEnabled", "false");
            put("vcsType", "git");
            put("name", projectName);
        }}).post("/projects").then()
                .body("code", equalTo(0))
                .body("data", equalTo(String.format("/u/%s/p/%s", Steps.global_key, projectName)));
    }


    @Test
    public void testCreateDuplicatedProject() {

        HashMap<String, String> params = new HashMap<String, String>() {{
            put("type", "2");
            put("gitEnabled", "true");
            put("gitReadmeEnabled", "false");
            put("vcsType", "git");
            put("name", "CreateDuplicatedProject");
        }};

        Steps.getRequestSpec().formParams(params).post("/projects");

        Steps.getRequestSpec().formParams(params).post("/projects").then()
                .body("code", equalTo(1103));
    }


    @After
    public void tearDown() {
        utils.deleteProject(projectName);
        utils.deleteProject("CreateDuplicatedProject");
    }

}
