package ru.netology.data;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
import lombok.Value;

import java.util.Locale;

import static io.restassured.RestAssured.given;


public class DataGenerator {
    private DataGenerator() {
    }

    @Value
    public static class User {
        private static Faker faker = new Faker(new Locale("ru"));
        private static String password = faker.internet().password();
        private static String login = faker.name().username();

        public static void activeOrBlockedUserAuthData(String activeOrBlocked) {

            given()
                    .baseUri("http://localhost:9999")
                    .contentType(ContentType.JSON)
                    .body(new Gson().toJson(new AuthInfo(login,
                            password, activeOrBlocked)))
                    .when()
                    .post("/api/system/users")
                    .then()
                    .statusCode(200);
        }

        public static String getInvalidLogin() {
            return faker.internet().password();
        }

        public static String getInvalidPassword() {
            return faker.internet().password();
        }

        public static String getValidLogin() {
            return login;
        }

        public static String getValidPassword() {
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
