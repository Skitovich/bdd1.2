package ru.netology.data;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
import lombok.Value;

import static io.restassured.RestAssured.given;


public class DataClass {
    private DataClass() {
    }


    @Value
    public static class User {
        private static final Faker faker = new Faker();
        private static final String password = faker.internet().password();
        private static final String login = faker.name().username();


        public static void activeUser() {
            given()
                    .baseUri("http://localhost:9999")
                    .contentType(ContentType.JSON)
                    .body(new Gson().toJson(new AuthInfo(login, password, "active")))
                    .when()
                    .post("/api/system/users")
                    .then()
                    .statusCode(200);
        }

        public static void blockedUser() {
            given()
                    .baseUri("http://localhost:9999")
                    .contentType(ContentType.JSON)
                    .body(new Gson().toJson(new AuthInfo(login, password, "blocked")))
                    .when()
                    .post("/api/system/users")
                    .then()
                    .statusCode(200);
        }

        public static String getLogin() {
            return login;
        }

        public static String getPassword() {
            return password;
        }

    }

    @Value
    private static class AuthInfo {
        String login;
        String password;
        String status;
    }
}
