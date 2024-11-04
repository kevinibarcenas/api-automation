package com.kbarcenas.automation.test.api;

import com.kbarcenas.automation.main.annotation.TCNotes;
import com.kbarcenas.automation.main.models.PostModel;
import com.kbarcenas.automation.test.ApiBaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.kbarcenas.automation.main.constants.Constants.POSTS_PATH;
import static org.assertj.core.api.Assertions.assertThat;

public class PutResourceTests extends ApiBaseTest {

    @Test(priority = 0)
    @TCNotes(title = "PUT into a resource")
    public void PutResource() {
        PostModel resource = new PostModel("Kevin Test", "This is just a test", 1);

        Response resp = apiRequests.putRequest(POSTS_PATH, resource, "2");
        htmlLog.info(resp);
        //Assert that we get a 200 response, that the response is not empty and the userId is correct
        assertThat(resp.getStatusCode()).withFailMessage("Could not put into resource, Error:" + resp.asString()).isEqualTo(200);
        assertThat(resp.jsonPath().getString("title")).withFailMessage("Incorrect Data").isEqualTo("Kevin Test");
        assertThat(resp.jsonPath().getString("body")).withFailMessage("Incorrect Data").isEqualTo("This is just a test");
        assertThat(resp.jsonPath().getString("userId")).withFailMessage("Incorrect Data").isEqualTo("1");
        assertThat(resp.jsonPath().getString("id")).withFailMessage("Incorrect Data").isEqualTo("2");
    }

    @Test(priority = 1)
    @TCNotes(title = "PUT into a resource that doesn't exist")
    public void PutResourceInvalidId() {
        PostModel resource = new PostModel("Kevin Test", "This is just a test", 1);

        Response resp = apiRequests.putRequest(POSTS_PATH, resource, "1000");
        htmlLog.info(resp);
        //Assert that we get a 400 status code since the resource id does not exist
        assertThat(resp.getStatusCode()).withFailMessage("Unexpected Status Code, Error:" + resp.asString()).isEqualTo(500);
    }

    @Test(priority = 1)
    @TCNotes(title = "PUT into a resource with a an invalid userId")
    public void PutResourceInvalidUserId() {
        PostModel resource = new PostModel("Kevin Test", "This is just a test", -1);

        Response resp = apiRequests.putRequest(POSTS_PATH, resource, "2");
        htmlLog.info(resp);
        //Assert that we get a 400 status code since the userId should not be valid
        assertThat(resp.getStatusCode()).withFailMessage("Unexpected Status Code, Error:" + resp.asString()).isEqualTo(400);
    }

    @Test(priority = 1)
    @TCNotes(title = "Put into a resource missing values")
    public void PutResourceMissingValues() {
        String resource = "{ \"title\": \"Kevin Test\", \"body\": \"This is just a test\"}";

        Response resp = apiRequests.putRequest(POSTS_PATH, resource, "2");
        htmlLog.info(resp);
        //Assert that we get a 400 status code since we are missing attributes in the body
        assertThat(resp.getStatusCode()).withFailMessage("Unexpected Status Code, Error:" + resp.asString()).isEqualTo(400);
    }

    @Test(priority = 1)
    @TCNotes(title = "Put into a resource with empty data")
    public void PutResourceEmptyData() {
        String resource = "{}";

        Response resp = apiRequests.putRequest(POSTS_PATH, resource, "2");
        htmlLog.info(resp);
        //Assert that we get a 400 status since the body is empty
        assertThat(resp.getStatusCode()).withFailMessage("Unexpected Status Code, Error:" + resp.asString()).isEqualTo(400);
    }

    @Test(priority = 1)
    @TCNotes(title = "Put into a resource with invalid data")
    public void PutResourceInvalidData() {
        String resource = "{ \"title\": 21, \"body\": 2233}, \"userId\": \"invalid\"}";
        Response resp = apiRequests.putRequest(POSTS_PATH, resource, "2");
        htmlLog.info(resp);
        //Assert that we get a 400 response since the attributes have invalid data
        assertThat(resp.getStatusCode()).withFailMessage("Unexpected Status Code, Error:" + resp.asString()).isEqualTo(400);
    }
}
