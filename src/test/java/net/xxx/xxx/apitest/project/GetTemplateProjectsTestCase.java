package net.xxx.xxx.apitest.project;

import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

/**
 * @program: GetTemplateProjectsTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-11-05 16:23
 **/

public class GetTemplateProjectsTestCase extends AbstractTestCase {


    @Test
    public void getTemplateProjects() {
        Steps.getRequestSpec().param("template")
                .get("/projects").then()
                .body("code", equalTo(0))
                .body("data.name", hasItems("Blank", "JavaDemo", "WordPress", "PythonDemo", "AI"))
                .body("data.findAll {it.name =='Blank'}.env", hasItem("xxx-tty"))
                .body("data.findAll {it.name =='JavaDemo'}.env", hasItem("xxx-tty-java-maven"))
                .body("data.findAll {it.name =='WordPress'}.env", hasItem("xxx-tty-php-mysql"))
                .body("data.findAll {it.name =='PythonDemo'}.env", hasItem("xxx-tty-machine-learning"))
                .body("data.findAll {it.name =='AI'}.env", hasItem("xxx-tty-machine-learning"));
    }
}
