package com.kbarcenas.automation.test.api;

import com.kbarcenas.automation.main.annotation.TCNotes;
import com.kbarcenas.automation.main.models.PostModel;
import com.kbarcenas.automation.test.ApiBaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.kbarcenas.automation.main.constants.Constants.POSTS_PATH;
import static org.assertj.core.api.Assertions.assertThat;

public class PatchResourceTest extends ApiBaseTest {

    @Test(priority = 0)
    @TCNotes(title = "Patch a resource title")
    public void PatchResourceTitle() {
        String resource = "{ \"title\": \"Kevin Patch Test\"}";

        Response resp = apiRequests.patchRequest(POSTS_PATH, resource, "2");
        htmlLog.info(resp);
        //Assert that we get a 200 response, that the response is not empty and the userId is correct
        assertThat(resp.getStatusCode()).withFailMessage("Could not patch resource, Error:" + resp.asString()).isEqualTo(200);
        assertThat(resp.jsonPath().getString("title")).withFailMessage("Incorrect Data").isEqualTo("Kevin Patch Test");
    }

    @Test(priority = 0)
    @TCNotes(title = "Patch a resource body")
    public void PatchResourceBody() {
        String resource = "{ \"body\": \"Kevin Patch Test\"}";

        Response resp = apiRequests.patchRequest(POSTS_PATH, resource, "2");
        htmlLog.info(resp);
        //Assert that we get a 200 response and the attribute changed
        assertThat(resp.getStatusCode()).withFailMessage("Could not patch resource, Error:" + resp.asString()).isEqualTo(200);
        assertThat(resp.jsonPath().getString("body")).withFailMessage("Incorrect Data").isEqualTo("Kevin Patch Test");
    }

    @Test(priority = 1)
    @TCNotes(title = "Patch a resource userId should not be possible")
    public void PatchResourceUserId() {
        String resource = "{ \"userId\": \"invalid\"}";

        Response resp = apiRequests.patchRequest(POSTS_PATH, resource, "2");
        htmlLog.info(resp);
        //Assert that we get a 200 response and the userId changed
        assertThat(resp.getStatusCode()).withFailMessage("Unexpected status code, Error:" + resp.asString()).isEqualTo(400);
    }

    @Test(priority = 1)
    @TCNotes(title = "Patch a resource id should not be possible")
    public void PatchResourceId() {
        String resource = "{ \"id\": 3}";

        Response resp = apiRequests.patchRequest(POSTS_PATH, resource, "2");
        htmlLog.info(resp);
        //Assert that we get a 200 response and the userId changed
        assertThat(resp.getStatusCode()).withFailMessage("Unexpected status code, Error:" + resp.asString()).isEqualTo(400);
    }

    @Test(priority = 1)
    @TCNotes(title = "Patch into a resource that doesn't exist")
    public void PatchResourceInvalidId() {
        String resource = "{ \"title\": \"Kevin Patch Test\"}";

        Response resp = apiRequests.patchRequest(POSTS_PATH, resource, "1000");
        htmlLog.info(resp);
        //Assert that we get a 400 status code since the resource id does not exist
        assertThat(resp.getStatusCode()).withFailMessage("Unexpected Status Code, Error:" + resp.asString()).isEqualTo(500);
    }

    @Test(priority = 1)
    @TCNotes(title = "Patch a resource with empty data")
    public void PutResourceEmptyData() {
        String resource = "{}";

        Response resp = apiRequests.putRequest(POSTS_PATH, resource, "2");
        htmlLog.info(resp);
        //Assert that we get a 400 status since the body is empty
        assertThat(resp.getStatusCode()).withFailMessage("Unexpected Status Code, Error:" + resp.asString()).isEqualTo(400);
    }

    @Test(priority = 1)
    @TCNotes(title = "Patch a resource with invalid data")
    public void PutResourceInvalidData() {
        String resource = "{ \"title\": 21, \"body\": 2233}, \"userId\": \"invalid\"}";
        Response resp = apiRequests.putRequest(POSTS_PATH, resource, "2");
        htmlLog.info(resp);
        //Assert that we get a 400 response since the attributes have invalid data
        assertThat(resp.getStatusCode()).withFailMessage("Unexpected Status Code, Error:" + resp.asString()).isEqualTo(400);
    }
}
