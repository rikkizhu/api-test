package net.xxx.xxx.apitest.project;

import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import net.xxx.xxx.apitest.Utils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

/**
 * @program: DeleteProjectTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-11-14 09:51
 **/
public class DeleteProjectTestCase extends AbstractTestCase {
    Utils utils = new Utils();
    String projectName = RandomStringUtils.randomAlphanumeric(8);

    @Before
    public void tearUp() {
        utils.createPro(projectName);
    }

    @Test
    public void testDeletePro() {
        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .param("projectName", projectName)
                .param("twoFactorCode", utils.computeTwoFactorCode(Steps.password))
                .delete("/project").then()
                .body("code", equalTo(0))
                .body("msg", equalTo("Success"));
    }

}
