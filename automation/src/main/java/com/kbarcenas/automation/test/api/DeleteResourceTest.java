package com.kbarcenas.automation.test.api;

import com.kbarcenas.automation.main.annotation.TCNotes;
import com.kbarcenas.automation.test.ApiBaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.kbarcenas.automation.main.constants.Constants.POSTS_PATH;
import static org.assertj.core.api.Assertions.assertThat;

public class DeleteResourceTest extends ApiBaseTest {

    @Test(priority = 0)
    @TCNotes(title = "Delete a resource")
    public void DeleteResource() {
        htmlLog.info("Deleting resource");
        Response resp = apiRequests.deleteRequest(POSTS_PATH, "1");
        htmlLog.info(resp);
        //Assert that we get a 200 status
        assertThat(resp.getStatusCode()).withFailMessage("Could not delete resource, Error:" + resp.asString()).isEqualTo(200);
        htmlLog.info("Verifying resource deletion");
        Response getResp = apiRequests.getRequestById(POSTS_PATH, "1");
        htmlLog.info(getResp);
        //Assert that the deleted resource does not exist anymore
        assertThat(getResp.getStatusCode()).withFailMessage("Resource still exists, Error:" + resp.asString()).isEqualTo(404);
    }

    @Test(priority = 0)
    @TCNotes(title = "Delete without an id")
    public void DeleteResourceNoId() {
        Response resp = apiRequests.deleteRequest(POSTS_PATH, "");
        htmlLog.info(resp);
        //Assert that we get a 404 status
        assertThat(resp.getStatusCode()).withFailMessage("Could not delete resource, Error:" + resp.asString()).isEqualTo(404);
    }

    @Test(priority = 1)
    @TCNotes(title = "Delete a resource with invalid id")
    public void DeleteResourceInvalidId() {
        Response resp = apiRequests.deleteRequest(POSTS_PATH, "1000");
        htmlLog.info(resp);
        //Assert that we get a 404 response since the resource should not exist
        assertThat(resp.getStatusCode()).withFailMessage("Resource should not exist, Error:" + resp.asString()).isEqualTo(404);
    }

}
