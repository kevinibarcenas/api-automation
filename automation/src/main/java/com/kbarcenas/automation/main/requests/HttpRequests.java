package com.kbarcenas.automation.main.requests;

import static io.restassured.RestAssured.given;

import com.kbarcenas.automation.main.models.PostModel;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.springframework.boot.autoconfigure.cassandra.CassandraProperties;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@Component
public class HttpRequests {

    //Post a POJO
    public static Response postRequest(String endPoint, PostModel payload) {
        return given().log().all().contentType(ContentType.JSON).body(payload)
                .when().post(endPoint);
    }

    //Post a Json String
    public static Response postRequest(String endPoint, String payload) {
        return given().log().all().contentType(ContentType.JSON).body(payload)
                .when().post(endPoint);
    }

    //Put a POJO
    public static Response putRequest(String endPoint, PostModel payload, String postId) {
        return given().log().all().contentType(ContentType.JSON).body(payload).pathParam("id", postId)
                .when().put(endPoint+"/{id}");
    }

    //Put a json string
    public static Response putRequest(String endPoint, String payload, String postId) {
        return given().log().all().contentType(ContentType.JSON).body(payload).pathParam("id", postId)
                .when().put(endPoint+"/{id}");
    }

    //Patch a json string
    public static Response patchRequest(String endPoint, String payload, String postId) {
        return given().log().all().contentType(ContentType.JSON).body(payload).pathParam("id", postId)
                .when().patch(endPoint+"/{id}");
    }

    //Delete a resource
    public static Response deleteRequest(String endPoint, String id) {
        return given().log().all().pathParam("id",id)
                .delete(endPoint+"/{id}");
    }

    //Get request to endpoint
    public static Response getRequest(String endPoint) {
        return given().log().all()
                .get(endPoint);
    }

    //Get request to id
    public static Response getRequestById(String endPoint, String id) {
        return given().log().all().pathParam("id",id)
                .get(endPoint+"/{id}");
    }

    //Get comments of postId
    public static Response getPostComments(String endPoint, String postId) {
        return given().log().all().pathParam("id",postId)
                .get(endPoint+"/{id}/comments");
    }

    //Get comments by postId filter
    public static Response getCommentsByPostId(String endPoint, String postId) {
        return given().log().all().queryParam("postId",postId)
                .get(endPoint);
    }

}
