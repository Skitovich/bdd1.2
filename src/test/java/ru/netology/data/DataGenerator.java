package ru.netology.data;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
import lombok.Data;
import lombok.Value;

import java.util.Locale;

import static com.codeborne.selenide.Selenide.element;
import static io.restassured.RestAssured.given;

@Data
public class DataGenerator {

    public DataGenerator() {
    }

    @Value
    public static class User {
        private static Faker faker = new Faker(new Locale("ru"));


        public static void registerUser(String login, String password, String activeOrBlocked) {
            given()
                    .baseUri("http://localhost:9999")
                    .contentType(ContentType.JSON)
                    .body(new Gson().toJson(new AuthInfo(login, password, activeOrBlocked)))
                    .when()
                    .post("/api/system/users")
                    .then()
                    .statusCode(200);
        }


        public static void registerActiveUser() {
            String login = faker.name().firstName();
            String password = faker.internet().password();
            registerUser(login, password, "active");
            element("[name='login']").sendKeys(login);
            element("[name='password']").sendKeys(password);
        }

        public static void registerBlockedUser() {
            String login = faker.name().firstName();
            String password = faker.internet().password();
            registerUser(login, password, "blocked");
            element("[name='login']").sendKeys(login);
            element("[name='password']").sendKeys(password);
        }

        public static void registerUserInvalidPassword() {
            String login = faker.name().firstName();
            String password = faker.internet().password();
            String invalidPassword = faker.internet().password();
            registerUser(login, password, "active");
            element("[name='login']").sendKeys(login);
            element("[name='password']").sendKeys(invalidPassword);
        }

        public static void registerUserInvalidLogin() {
            String login = faker.name().firstName();
            String password = faker.internet().password();
            String invalidLogin = faker.name().firstName();
            registerUser(login, password, "active");
            element("[name='login']").sendKeys(invalidLogin);
            element("[name='password']").sendKeys(password);
        }

        public static void unregisteredUser() {
            String login = faker.name().firstName();
            String password = faker.internet().password();
            element("[name='login']").sendKeys(login);
            element("[name='password']").sendKeys(password);
        }


    }

    @Value
    public static class AuthInfo {
        String login;
        String password;
        String status;
    }
}
