package com.kbarcenas.automation.test.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static com.kbarcenas.automation.main.constants.Constants.POSTS_PATH;
import static com.kbarcenas.automation.main.constants.Constants.COMMENTS_PATH;

import com.kbarcenas.automation.test.ApiBaseTest;
import com.kbarcenas.automation.main.annotation.TCNotes;
import org.testng.annotations.Test;
import io.restassured.response.Response;

public class GetResourceTests extends ApiBaseTest {

    @Test(priority = 0)
    @TCNotes(title = "Get all posts")
    public void getAllPosts() {
        Response resp = apiRequests.getRequest(POSTS_PATH);
        htmlLog.info(resp);
        //Assert that we get a 200 response and the response is not empty
        assertThat(resp.getStatusCode()).withFailMessage("Could not get Posts, Error:" + resp.asString()).isEqualTo(200);
        assertThat(resp.jsonPath().getList("$").size(), greaterThan(0));
    }

    @Test(priority = 0)
    @TCNotes(title = "Get post by id")
    public void getPostById() {
        Response resp = apiRequests.getRequestById(POSTS_PATH, "1");
        htmlLog.info(resp);
        //Assert that we get a 200 response and the attributes exist
        assertThat(resp.getStatusCode()).withFailMessage("Could not get a Post, Error:" + resp.asString()).isEqualTo(200);
        assertThat(resp.jsonPath().getString("userId")).withFailMessage("Does not exist").isEqualTo("1");
        assertThat(resp.jsonPath().getString("id")).withFailMessage("Does not exist").isNotNull();
        assertThat(resp.jsonPath().getString("title")).withFailMessage("Does not exist").isNotNull();
    }

    @Test(priority = 0)
    @TCNotes(title = "Get a Post comments")
    public void getPostComments() {
        Response resp = apiRequests.getPostComments(POSTS_PATH, "1");
        htmlLog.info(resp);
        //Assert that we get a 200 response and the response is not empty
        assertThat(resp.getStatusCode()).withFailMessage("Could not get Posts, Error:" + resp.asString()).isEqualTo(200);
        assertThat(resp.jsonPath().getList("$").size(), greaterThan(0));
    }

    @Test(priority = 0)
    @TCNotes(title = "Get comments using a postId filter")
    public void getCommentsFromPostId() {
        Response resp = apiRequests.getCommentsByPostId(COMMENTS_PATH, "1");
        htmlLog.info(resp);
        //Assert that we get a 200 response and the response is not empty
        assertThat(resp.getStatusCode()).withFailMessage("Could not get Posts, Error:" + resp.asString()).isEqualTo(200);
        assertThat(resp.jsonPath().getList("$").size(), greaterThan(0));
    }

    @Test(priority = 1)
    @TCNotes(title = "Get post by an invalid id")
    public void getPostByInvalidId() {
        Response resp1 = apiRequests.getRequestById(POSTS_PATH, "-1");
        Response resp2 = apiRequests.getRequestById(POSTS_PATH, "0");
        Response resp3 = apiRequests.getRequestById(POSTS_PATH, "invalid");
        //Assert that we get a 404 status code since the resource does not exist
        assertThat(resp1.getStatusCode()).withFailMessage("Could not get Posts, Error:" + resp1.asString()).isEqualTo(404);
        assertThat(resp2.getStatusCode()).withFailMessage("Could not get Posts, Error:" + resp1.asString()).isEqualTo(404);
        assertThat(resp3.getStatusCode()).withFailMessage("Could not get Posts, Error:" + resp1.asString()).isEqualTo(404);
    }

    @Test(priority = 1)
    @TCNotes(title = "Get comments from an invalid post id")
    public void getCommentsFromInvalidId() {
        Response resp = apiRequests.getCommentsByPostId(COMMENTS_PATH, "-1");
        htmlLog.info(resp);
        //Assert that we get a 200, BUT an empty comment list
        assertThat(resp.getStatusCode()).withFailMessage("Could not get Posts, Error:" + resp.asString()).isEqualTo(200);
        assertThat(resp.jsonPath().getList("$").size(), lessThan(1));
    }
}