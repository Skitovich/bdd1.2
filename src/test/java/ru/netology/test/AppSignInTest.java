package ru.netology.test;


import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.element;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataClass.User.*;

public class AppSignInTest {


    @BeforeAll
    static void setUp() {
        Configuration.browser = "firefox";
        Configuration.startMaximized = true;
        open("http://localhost:9999/");
        element("[name='login']").sendKeys(getLogin());
        element("[name='password']").sendKeys(getPassword());

    }


    @Test
    void shouldLogin() {
        activeUser();
        element(".button__text").click();
        element("h2").shouldHave(text("Личный кабинет")).shouldBe(visible);
    }

    @Test
    void shouldError() {
        blockedUser();
        element(".button__text").click();
        element(".notification__title").shouldBe(visible);
    }
}