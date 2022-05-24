package net.xxx.xxx.apitest;

import io.restassured.response.Response;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;


/**
 * @program: Utils
 * @description:
 * @author: zhuruiqi
 * @create: 2018-10-31 15:09
 **/
public class Utils {

    public void createPro(final String proName) {
        Steps.getRequestSpec().formParams(new HashMap<String, String>() {
            {
                put("type", "2");
                put("gitEnabled", "true");
                put("gitReadmeEnabled", "false");
                put("vcsType", "git");
                put("name", proName);
            }
        }).post("/projects");
    }

    public String computeTwoFactorCode(String password) {
        return DigestUtils.sha1Hex(password);
    }

    public void deleteProject(final String projectName) {
        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .param("projectName", projectName)
                .param("twoFactorCode", computeTwoFactorCode(Steps.password))
                .delete("/project");
    }

    public void synPro() {
        Steps.getRequestSpec().post("/project/sync");
    }


    public void deleteWorkspaceBySpacekey(String spaceKey) {
        Steps.getRequestSpec()
                .param("spaceKey", spaceKey).delete("/ws/delete");
    }


    public String createWsByxxxPro(final String proName) {
        Response response = Steps.getRequestSpec().formParams(new HashMap<String, String>() {{
            put("cpuLimit", "2");
            put("memory", "2048");
            put("storage", "2");
            put("source", "xxx");
            put("ownerName", Steps.ownerName);
            put("projectName", proName);
            put("envId", "xxx-tty");
        }}).post("/ws/create")
                .then().extract().response();


        return response.path("data.spaceKey");
    }

    public String createWsWithoutPro(final String wsName, final Integer TemplateId) {
        Response response = Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .formParams(new HashMap<String, Object>() {{
                    put("cpuLimit", 2);
                    put("memory", 2048);
                    put("storage", 2);
                    put("workspaceName", wsName);
                    put("ownerName", "xxxxxx");
                    put("projectName", "empty-template");
                    put("templateId", TemplateId);
                }}).post("/workspaces").then().extract().response();

        return response.path("spaceKey");

    }

    public void inviteCollaborators(String spaceKey) {
        Steps.getRequestSpec()
                .accept("application/vnd.xxx.v2+json")
                .header("x-space-key", spaceKey)
                .formParams(new HashMap<String, String>() {{
                    put("inviteKey", Steps.collaboratorKey);
                }})
                .post("workspaces/" + spaceKey + "/collaborators");
    }

    public void openWorkspace(final String spaceKey) {
        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .headers(new HashMap<String, String>() {{
                    put("x-space-key", spaceKey);
                    put("x-global-key", Steps.global_key);
                }})
                .post("/workspaces/" + spaceKey)
                .then().extract().response();
    }

    public void createFile(String spaceKey, String fileName) {
        Steps.getRequestSpec().header("x-space-key", spaceKey)
                .accept("application/vnd.xxx.v2+json")
                .param("path", "/" + fileName)
                .post("/workspaces/" + spaceKey + "/files");
    }

    public String createTestDemoWS() {
        Response response = Steps.getRequestSpec().formParams(new HashMap<String, String>() {{
            put("cpuLimit", "2");
            put("memory", "2048");
            put("storage", "2");
            put("source", "xxx");
            put("ownerName", Steps.ownerName);
            put("projectName", "TestDemo");
            put("envId", "xxx-tty");
        }}).post("/ws/create")
                .then().extract().response();
        return response.path("data.spaceKey");

    }

    public String generateFile(String filename, String fileContent) throws IOException {
        File dir = new File("./target/upload");
        dir.mkdir();
        File file = new File("./target/upload/" + filename);
        file.createNewFile();
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        bufferedWriter.write(fileContent);
        bufferedWriter.flush();
        bufferedWriter.close();
        return file.getAbsolutePath();

    }


    public void deleteGeneratedFile() {
        FileUtils.deleteQuietly(new File("./target/upload"));
    }

    public void mkdirFolderinFileTree(String spaceKey, final String folderName) {
        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .header("x-space-key", spaceKey)
                .formParams(new HashMap<String, String>() {{
                    put("path", folderName);
                }}).post("/workspaces/" + spaceKey + "/mkdir");
    }


    public String getNotificationId() {
        Response response = Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .get("/workspaces/notification?page=1&pageSize=10");
        return response.path("data[0].id");
    }

    public void createAccessUrl(String spaceKey, String port) {
        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .header("x-space-key", spaceKey)
                .param("port", port)
                .post("/hf/" + spaceKey);
    }

    public void createDyPages(String spaceKey) {
        Steps.getRequestSpec().header("x-space-key", spaceKey)
                .accept("application/json, text/plain, */*")
                .post("/dynamic-pages/" + spaceKey);
    }

    public void bindRightPagesDomain(String spaceKey) {
        Steps.getRequestSpec().accept("application/json, text/plain, */*")
                .header("x-space-key", spaceKey)
                .formParams(new HashMap<String, String>() {{
                    put("domain", Steps.getPagesTestDomain());
                }}).post("/dynamic-pages/" + spaceKey + "/domain");
    }

    public void deleteDyPages(String spaceKey) {
        Steps.getRequestSpec().accept("application/json, text/plain, */*")
                .header("x-space-key", spaceKey)
                .delete("/dynamic-pages/" + spaceKey);
    }

    public String[] createPlugin(final String pluginName, final String proName, final String reMark) {
        Response response = Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .formParams(new HashMap<String, String>() {{
                    put("cpuLimit", "2");
                    put("memory", "2048");
                    put("storage", "2");
                    put("pluginName", pluginName);
                    put("repoName", proName);
                    put("typeId", "10");
                    put("pluginTemplateId", "8");
                    put("remark", reMark);
                }}).post("/user-plugin/create");

        String[] results = new String[2];
        results[0] = response.path("data.spaceKey");
        results[1] = response.path("data.id").toString();
        return results;

    }

    public void prePublishPlugin(final String pluginId) {

        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .formParams(new HashMap<String, String>() {{
                    put("version", "");
                    put("description", "[pre publish]");
                    put("isPreDeploy", "true");
                    put("pluginId", pluginId);
                }}).post("/user-plugin/deploy");
    }

    public void deletePlugin(String pluginId) {
        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .delete("/user-plugin/" + pluginId)
                .then()
                .body("code", equalTo(0))
                .body("msg", equalTo("Success"));
    }

    public void enablePlugin(final String pluginId, final String status) {
        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .formParams(new HashMap<String, String>() {{
                    put("pluginId", pluginId);
                    put("status", status);
                }}).put("/user-plugin/enable");
    }


    public void uploadFile(String spaceKey, String wsFileName, final String wsFilePath, String fileContent) throws IOException {
        String filePath = generateFile(wsFileName, fileContent);
        Steps.getRequestSpec().header("x-space-key", spaceKey)
                .header("content-type", "multipart/form-data")
                .accept("application/vnd.xxx.v2+json")
                .formParams(new HashMap<String, String>() {{
                    put("path", wsFilePath);
                }})
                .multiPart("files", new File(filePath))
                .post("/workspaces/" + spaceKey + "/upload");
    }

    public void gitCommitAll(String spaceKey, final String commitMessage) {
        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .contentType("application/x-www-form-urlencoded")
                .header("x-space-key", spaceKey)
                .formParams(new HashMap<String, String>() {{
                    put("message", commitMessage);
                }}).post("/git/" + spaceKey + "/commits");
    }

    public void createBranch(String spaceKey, final String startPoint, final String newBranchName) {
        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .header("x-space-key", spaceKey)
                .formParams(new HashMap<String, String>() {{
                    put("name", newBranchName);
                    put("startPoint", startPoint);
                }}).post("/git/" + spaceKey + "/checkout");
    }

    public void createTag(String spaceKey, final String tagName, final String tagMessage) {
        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .header("x-space-key", spaceKey)
                .formParams(new HashMap<String, String>() {{
                    put("tagName", tagName);
                    put("ref", "HEAD");
                    put("message", tagMessage);
                    put("force", "false");
                }}).post("/git/" + spaceKey + "/tags");
    }

    public void checkoutBranch(String spaceKey, final String branchName) {
        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .header("x-space-key", spaceKey)
                .formParams(new HashMap<String, String>() {{
                    put("name", branchName);
                }}).post("/git/" + spaceKey + "/checkout");
    }

    public void gitMerge(String spaceKey, final String branchName) {
        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .header("x-space-key", spaceKey)
                .formParams(new HashMap<String, String>() {{
                    put("name", branchName);
                }}).post("/git/" + spaceKey + "/merge");
    }

    public void createStash(String spaceKey, final String stashMessage) {
        Steps.getRequestSpec().accept("application/vnd.xxx.v2+json")
                .header("x-space-key", spaceKey)
                .formParams(new HashMap<String, String>() {{
                    put("message", stashMessage);
                }}).post("/git/" + spaceKey + "/stash");
    }

    public void deleteAllWs() {
        Response response = Steps.getRequestSpec()
                .get("/ws/list?page=0&size=100").then().extract().response();
        List<String> spaceKeys = response.path("data.list.spaceKey");
        if (spaceKeys.size() == 0) {
            return;
        } else {
            for (int i = 0; i < spaceKeys.size(); i++) {
                deleteWorkspaceBySpacekey(spaceKeys.get(i));
            }
        }
    }

}


