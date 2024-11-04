package com.kbarcenas.automation.test.api;

import static org.assertj.core.api.Assertions.assertThat;
import static com.kbarcenas.automation.main.constants.Constants.POSTS_PATH;

import com.kbarcenas.automation.main.models.PostModel;

import com.kbarcenas.automation.test.ApiBaseTest;
import com.kbarcenas.automation.main.annotation.TCNotes;
import org.testng.annotations.Test;
import io.restassured.response.Response;

public class CreateResourceTests extends ApiBaseTest {

    @Test(priority = 0)
    @TCNotes(title = "Create a resource")
    public void CreateResource() {
        PostModel resource = new PostModel("Kevin Test", "This is just a test", 1);

        Response resp = apiRequests.postRequest(POSTS_PATH, resource);
        htmlLog.info(resp);
        //Assert that we get a 200 response, that the response is not empty and the userId is correct
        assertThat(resp.getStatusCode()).withFailMessage("Could not Post a resource, Error:" + resp.asString()).isEqualTo(201);
        assertThat(resp.jsonPath().getString("title")).withFailMessage("Incorrect Data").isEqualTo("Kevin Test");
        assertThat(resp.jsonPath().getString("body")).withFailMessage("Incorrect Data").isEqualTo("This is just a test");
        assertThat(resp.jsonPath().getString("userId")).withFailMessage("Incorrect Data").isEqualTo("1");
        assertThat(resp.jsonPath().getString("id")).withFailMessage("Incorrect Data").isGreaterThan("1");
    }

    @Test(priority = 1)
    @TCNotes(title = "Create a resource with a an invalid userId")
    public void CreateResourceInvalidUserId() {
        PostModel resource = new PostModel("Kevin Test", "This is just a test", -1);

        Response resp = apiRequests.postRequest(POSTS_PATH, resource);
        htmlLog.info(resp);
        //Assert that we get a 400 status code since the userId should not be valid
        assertThat(resp.getStatusCode()).withFailMessage("Unexpected Status Code, Error:" + resp.asString()).isEqualTo(400);
    }

    @Test(priority = 1)
    @TCNotes(title = "Create a resource missing values")
    public void CreateResourceMissingValues() {
        String resource = "{ \"title\": \"Kevin Test\", \"body\": \"This is just a test\"}";

        Response resp = apiRequests.postRequest(POSTS_PATH, resource);
        htmlLog.info(resp);
        //Assert that we get a 400 status code since we are missing attributes in the body
        assertThat(resp.getStatusCode()).withFailMessage("Unexpected Status Code, Error:" + resp.asString()).isEqualTo(400);
    }

    @Test(priority = 1)
    @TCNotes(title = "Create a resource with empty data")
    public void CreateResourceEmptyData() {
        String resource = "{}";

        Response resp = apiRequests.postRequest(POSTS_PATH, resource);
        htmlLog.info(resp);
        //Assert that we get a 400 status since the body is empty
        assertThat(resp.getStatusCode()).withFailMessage("Unexpected Status Code, Error:" + resp.asString()).isEqualTo(400);
    }

    @Test(priority = 1)
    @TCNotes(title = "Create a resource with invalid data")
    public void CreateResourceInvalidData() {
        String resource = "{ \"title\": 21, \"body\": 2233}, \"userId\": \"invalid\"}";
        Response resp = apiRequests.postRequest(POSTS_PATH, resource);
        htmlLog.info(resp);
        //Assert that we get a 400 response since the attributes have invalid data
        assertThat(resp.getStatusCode()).withFailMessage("Unexpected Status Code, Error:" + resp.asString()).isEqualTo(400);
    }

}
