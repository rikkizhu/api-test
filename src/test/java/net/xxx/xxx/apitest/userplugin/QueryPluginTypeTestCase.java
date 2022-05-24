package net.xxx.xxx.apitest.userplugin;

import net.xxx.xxx.apitest.AbstractTestCase;
import net.xxx.xxx.apitest.Steps;
import org.junit.Test;

import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @program: QueryPluginTypeTestCase
 * @description:
 * @author: zhuruiqi
 * @create: 2018-12-13 15:17
 **/
public class QueryPluginTypeTestCase extends AbstractTestCase {

    @Test
    public void testQueryPluginType() {
        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .contentType("application/x-www-form-urlencoded")
                .post("/public/user-plugins/types")
                .then()
                .body("code", equalTo(0))
                .body("msg", equalTo("Success"))
                .body("data.description", hasItems("Git 功能增强", "Git UI 增强", "编辑器功能增强", "编辑器 UI 增强",
                        "代码片段", "语法高亮", "快捷键", "编辑器预览视图", "文件图标主题", "娱乐小工具", "信息栏显示内容", "腾讯云接口"
                        , "第三方 API", "实用类小工具"))
                .body("data.id", hasItems(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13))
                .body("data.typeName", hasItems("Git 功能增强", "Git UI 增强", "编辑器功能增强", "编辑器 UI 增强",
                        "代码片段", "语法高亮", "快捷键", "编辑器预览视图", "文件图标主题", "娱乐小工具", "信息栏显示内容", "腾讯云接口"
                        , "第三方 API", "实用类小工具"));
    }
}
