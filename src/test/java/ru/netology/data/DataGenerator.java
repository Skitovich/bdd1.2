package ru.netology.data;

import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import lombok.Value;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {
    private static final Faker faker = new Faker(new Locale("en"));

    public DataGenerator() {
    }

    public static void registerUser(AuthInfo user) {
        given()
                .baseUri("http://localhost:9999")
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    public static String getLogin() {
        return faker.name().username();
    }

    public static String getPassword() {
        return faker.internet().password();
    }

    public static AuthInfo getRegisteredUser(String status) {
        AuthInfo user = new AuthInfo(getLogin(), getPassword(), status);
        registerUser(user);
        return user;
    }

    public static AuthInfo getWrongPasswordUser(String status) {
        String login = getLogin();
        registerUser(new AuthInfo(login, getPassword(), status));
        return new AuthInfo(login, getPassword(), status);
    }

    public static AuthInfo getWrongLoginUser (String status) {
        String password = getPassword();
        registerUser(new AuthInfo(getLogin(), password, status));
        return new AuthInfo(getLogin(), password, status);
    }

    public static AuthInfo getUnregisteredUser(String status) {
        return new AuthInfo(getLogin(), getPassword(), status);
    }

    @Value
    public static class AuthInfo {
        String login;
        String password;
        String status;
    }
}