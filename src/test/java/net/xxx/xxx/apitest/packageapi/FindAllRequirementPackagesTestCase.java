package net.xxx.xxx.apitest.packageapi;

import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import org.junit.Test;

import static org.hamcrest.core.IsCollectionContaining.hasItems;

/**
 * @program: FindAllRequirementPackagesTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-12-14 14:45
 **/
public class FindAllRequirementPackagesTestCase extends AbstractTestCase {

    @Test
    public void testFindAllRequirementPackages() {
        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .get("/packages?requirement=Required")
                .then()
                .body("name", hasItems("platform", "env", "access-url", "temporary", "collaboration", "Debug",
                        "PdfEditor", "Pages", "Notice"));
    }
}
