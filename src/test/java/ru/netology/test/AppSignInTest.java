package ru.netology.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.element;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataGenerator.User.*;

public class AppSignInTest {

    @BeforeAll
    static void setUp() {
        Configuration.browser = "firefox";
        Configuration.startMaximized = true;

    }

    @BeforeEach
    void closeUp() {
        open("http://localhost:9999/");
    }


    @Test
    void shouldLogin() {
        registerActiveUser();
        element(".button__text").click();
        element("h2").shouldHave(text("Личный кабинет")).shouldBe(visible);
    }

    @Test
    void shouldBlocked() {
        registerBlockedUser();
        element(".button__text").click();
        element(".notification__title").shouldBe(visible);
    }

    @Test
    void shouldErrorByLogin() {
        registerUserInvalidLogin();
        element(".button__text").click();
        element(".notification__content").shouldHave(text("Неверно указан логин или пароль"));
    }

    @Test
    void shouldErrorByPassword() {
        registerUserInvalidPassword();
        element(".button__text").click();
        element(".notification__content").shouldHave(text("Неверно указан логин или пароль"));
    }

    @Test
    void shouldErrorByPasswordAndLogin() {
        unregisteredUser();
        element(".button__text").click();
        element(".notification__content").shouldHave(text("Неверно указан логин или пароль"));
    }


}