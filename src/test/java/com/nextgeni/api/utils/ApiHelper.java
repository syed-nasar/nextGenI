package com.nextgeni.api.utils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

public class ApiHelper {

    private static final String BASE_URL = "https://automation-backend-ec08fe65847a.herokuapp.com/api/v1";
    private static final String LOGIN_ENDPOINT = BASE_URL + "/auth/login";
    private static final String CREATE_AGENCY_ENDPOINT = BASE_URL + "/agencies/add";
    private static final String GET_AGENCY_ENDPOINT = BASE_URL + "/agencies";

    public static Response login(Map<String, String> payload) {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post(LOGIN_ENDPOINT)
                .thenReturn();
    }

    public static Response createAgency(Map<String, String> payload, String authToken) {
        var request = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payload);

        if (authToken != null) {
            request.header("Authorization", "Bearer " + authToken);
        }

        return request.when()
                .post(CREATE_AGENCY_ENDPOINT)
                .thenReturn();
    }

    public static Response getAgencyById(Integer id, String authToken) {
        var request = RestAssured.given();

        if (authToken != null) {
            request.header("Authorization", "Bearer " + authToken);
        }

        return request.when()
                .get(GET_AGENCY_ENDPOINT + "/" + id)
                .thenReturn();
    }

    public static String extractAccessToken(Response response) {
        return response.jsonPath().getString("tokens.access.token");
    }

    public static Integer extractAgencyId(Response response) {
        Object id = response.jsonPath().get("agency.id");
        if (id == null) {
            id = response.jsonPath().get("response.id");
        }
        if (id == null) {
            id = response.jsonPath().get("data.id");
        }
        if (id == null) {
            throw new AssertionError("Cannot extract agency id from response: " + response.getBody().asString());
        }
        if (id instanceof Integer) {
            return (Integer) id;
        }
        if (id instanceof Double) {
            return ((Double) id).intValue();
        }
        if (id instanceof Long) {
            return ((Long) id).intValue();
        }
        return Integer.parseInt(String.valueOf(id));
    }

    public static String getJsonPath(Response response, String... paths) {
        for (String path : paths) {
            String value = response.jsonPath().getString(path);
            if (value != null) {
                return value;
            }
        }
        return null;
    }
}
