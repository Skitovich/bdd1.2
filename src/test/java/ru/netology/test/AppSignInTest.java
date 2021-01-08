package ru.netology.test;


import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
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


    @Test
    void shouldLogin() {
        open("http://localhost:9999/");
        activeOrBlockedUserAuthData("active");
        element("[name='login']").sendKeys(getValidLogin());
        element("[name='password']").sendKeys(getValidPassword());
        element(".button__text").click();
        element("h2").shouldHave(text("Личный кабинет")).shouldBe(visible);
    }

    @Test
    void shouldBlocked() {
        open("http://localhost:9999/");
        activeOrBlockedUserAuthData("blocked");
        element("[name='login']").sendKeys(getValidLogin());
        element("[name='password']").sendKeys(getValidPassword());
        element(".button__text").click();
        element(".notification__title").shouldBe(visible);
    }
    @Test
    void shouldErrorByLogin() {
        open("http://localhost:9999/");
        activeOrBlockedUserAuthData("active");
        element("[name='login']").sendKeys(getInvalidLogin());
        element("[name='password']").sendKeys(getValidPassword());
        element(".button__text").click();
        element(".notification__content").shouldHave(text("Неверно указан логин или пароль"));
    }
    @Test
    void shouldErrorByPassword() {
        open("http://localhost:9999/");
        activeOrBlockedUserAuthData("active");
        element("[name='login']").sendKeys(getValidLogin());
        element("[name='password']").sendKeys(getInvalidPassword());
        element(".button__text").click();
        element(".notification__content").shouldHave(text("Неверно указан логин или пароль"));
    }

    @Test
    void shouldErrorByPasswordAndLogin() {
        open("http://localhost:9999/");
        activeOrBlockedUserAuthData("active");
        element("[name='login']").sendKeys(getInvalidLogin());
        element("[name='password']").sendKeys(getInvalidPassword());
        element(".button__text").click();
        element(".notification__content").shouldHave(text("Неверно указан логин или пароль"));
    }


}