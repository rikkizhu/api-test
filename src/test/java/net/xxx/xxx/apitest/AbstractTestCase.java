package net.xxx.xxx.apitest;

import org.junit.BeforeClass;

/**
 * @program: AbstractTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2019-01-08 09:41
 **/
public abstract class AbstractTestCase {

    @BeforeClass
    public static void deleteAllWorkspaces() {
        Utils utils = new Utils();
        utils.deleteAllWs();
    }

}
