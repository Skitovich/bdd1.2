package ru.netology.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;

import static com.codeborne.selenide.Selenide.*;
import static ru.netology.data.DataGenerator.*;

public class AppSignInTest {

    @BeforeAll
   static void browser() {
        Configuration.browser = "opera";
    }

    @BeforeEach
    void setup() {
        open("http://localhost:9999/");
    }

    @Test
    public void shouldSuccessfulLoginActiveRegisteredUser() {
        AuthInfo validUser = getRegisteredUser("active");
        element("[name='login']").sendKeys(validUser.getLogin());
        element("[name='password']").sendKeys(validUser.getPassword());
        element(".button__text").click();
        element("h2").shouldHave(text("Личный кабинет"));
    }

    @Test
    public void shouldGetErrorWithWrongPasswordUser() {
        AuthInfo wrongPasswordUser = getWrongPasswordUser("active");
        element("[name='login']").sendKeys(wrongPasswordUser.getLogin());
        element("[name='password']").sendKeys(wrongPasswordUser.getPassword());
        element(".button__text").click();
        element(".notification__content").shouldHave(text("Неверно указан логин или пароль"));
    }

    @Test
    public void shouldGetErrorWithWrongLoginUser() {
        AuthInfo wrongLoginUser = getWrongLoginUser("active");
        element("[name='login']").sendKeys(wrongLoginUser.getLogin());
        element("[name='password']").sendKeys(wrongLoginUser.getPassword());
        element(".button__text").click();
        element(".notification__content").shouldHave(text("Неверно указан логин или пароль"));
    }

    @Test
    public void shouldGetErrorWithUnregisteredUser() {
        AuthInfo unregisteredUser = getUnregisteredUser("active");
        element("[name='login']").sendKeys(unregisteredUser.getLogin());
        element("[name='password']").sendKeys(unregisteredUser.getPassword());
        element(".button__text").click();
        element(".notification__content").shouldHave(text("Неверно указан логин или пароль"));
    }

    @Test
    public void shouldGetErrorWithBlockedUser() {
        AuthInfo blockedUser = getRegisteredUser("blocked");
        element("[name='login']").sendKeys(blockedUser.getLogin());
        element("[name='password']").sendKeys(blockedUser.getPassword());
        element(".button__text").click();
        element(".notification__content").shouldHave(text("Ошибка! Пользователь заблокирован"));
    }
}



