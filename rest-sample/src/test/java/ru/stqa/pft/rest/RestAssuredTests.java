package ru.stqa.pft.rest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.specification.ProxySpecification;

import com.sun.java.browser.net.ProxyInfo;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.NTCredentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Set;


import static com.sun.java.browser.net.ProxyService.getProxyInfo;
import static org.testng.Assert.assertEquals;

public class RestAssuredTests {

    @BeforeClass
    public void init(){
        RestAssured.authentication = RestAssured.basic("28accbe43ea112d9feb328d2c00b3eed", "");
    }
    @Test
    public void testCreateIssue() throws IOException {
        Set<Issue> oldIssues = getIssues();
        Issue newIssue = new Issue().withSubject("test").withDescription("new test");
        int issueId = CreateIssue(newIssue);
        Set<Issue> newIssues = getIssues();
        oldIssues.add(newIssue.withId(issueId));
        assertEquals(newIssues, oldIssues);
    }

    private Set<Issue> getIssues() throws IOException {
            String proxyUsername = "";
            String proxyPassword = "";
            String proxyHost = "";
            int proxyPort = 0;

        System.getProperties().put("http.proxyHost", proxyHost);
        System.getProperties().put("http.proxyPort", proxyPort);
        System.getProperties().put("http.proxyUser", proxyUsername);
        System.getProperties().put("http.proxyPassword", proxyPassword);






        String json = RestAssured
                .get("http://demo.bugify.com/api/issues.json").asString();
//1
        JsonElement parsed = new JsonParser().parse(json).getAsJsonObject();
        JsonElement issues = parsed.getAsJsonObject().get("issues");
        return new Gson().fromJson(issues, new TypeToken<Set<Issue>>(){}.getType());
    }

    private int CreateIssue(Issue newIssue) throws IOException {

        String proxyHost = "proxy.corp.tele2.ru";
        int proxyPort = 8080;
                RestAssured.proxy(new ProxySpecification(proxyHost, proxyPort, "http").withAuth("", "" ));

        String json = RestAssured.given()
                .parameter("subject", newIssue.getSubject())
                .parameter("description", newIssue.getDescription())
                .post("http://demo.bugify.com/api/issues.json").asString();
        JsonElement parsed = new JsonParser().parse(json);
        return parsed.getAsJsonObject().get("issue_id").getAsInt();
    }
}
