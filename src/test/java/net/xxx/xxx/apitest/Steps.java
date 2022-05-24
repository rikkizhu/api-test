package net.xxx.xxx.apitest;

import io.restassured.RestAssured;
import io.restassured.http.Cookie;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * @program: Steps
 * @description:
 * @author: zhuruiqi
 * @create: 2018-10-31 10:36
 **/
public class Steps {
    public static Cookie cookie;
    public static String password = "xxx.net";
    public static String collaboratorKey = "xxx2";
    public static String global_key = "qxxxtest";
    public static String ownerName = "qxxxtest";
    public static Integer Balnk_TemplateId = 6;
    public static Integer JavaDemo_TemplateId = 2;
    public static Integer WordPress_TemplateId = 10;
    public static Integer PythonDemo_TemplateId = 9;
    public static Integer AI_TemplateId = 7;
    public static String GitDomain = "git.dev.xxx.com";
    public static String PluginNotInstalledStatus = "1";
    public static String PluginInstalledStatus = "2";


    public static String getPagesTestDomain() {
        Map<String, String> map = System.getenv();
        return map.get("PAGES_TEST_DOMAIN");
    }

    public static String getPhpmyadminUrl() {
        Map<String, String> map = System.getenv();
        return map.get("PHPMYADMIN_URL");
    }

    public static String getDataBaseHost() {
        Map<String, String> map = System.getenv();
        return map.get("DATABASE_HOST");
    }

    public static String getSiteDatabasePort() {
        Map<String, String> map = System.getenv();
        return map.get("SITE_DATABASE_PORT");
    }

    public static String getPagesSiteDomain() {
        Map<String, String> map = System.getenv();
        return map.get("PAGES_SITE_DOMAIN");
    }

    public static String getShardingGroup() {
        Map<String, String> map = System.getenv();
        return map.get("SHARDING_GROUP");
    }


    public static String getPluginScoreId() {
        Map<String, String> map = System.getenv();
        return map.get("PLUGIN_SCORE_ID");
    }

    public static String getCloudStudioPluginMaterialID() {
        Map<String, String> map = System.getenv();
        return map.get("CLOUDSTUDIO_PLUGIN_MATERIAL_ID");
    }


    public static void setBaseUrl(String baseUrl) {
        RestAssured.baseURI = baseUrl;
    }

    public static void setBasePath(String basePath) {
        RestAssured.basePath = basePath;
    }

    public static void initURL() {
        Map<String, String> map = System.getenv();
        Steps.setBaseUrl(map.get("BASE_URL"));
        Steps.setBasePath("/backend");
    }


    public static Cookie getCookie() {
        if (cookie == null) {
            initURL();
            String stringCookie = given().when().formParams(new HashMap<String, String>() {
                {
                    put("email", "qxxxtest");
                    put("password", password);
                }
            }).post("/login").andReturn().cookie("DEV-STUDIO-SESSION");

            cookie = new Cookie.Builder("DEV-STUDIO-SESSION", stringCookie).build();

            return cookie;
        }

        return cookie;
    }


    public static RequestSpecification getRequestSpec() {
        initURL();
        return given().cookie(getCookie());
    }


}
