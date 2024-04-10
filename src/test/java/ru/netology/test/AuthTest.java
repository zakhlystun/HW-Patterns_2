package ru.netology.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.data.DataGenerator.Registration.getUser;
import static ru.netology.data.DataGenerator.getRandomLogin;
import static ru.netology.data.DataGenerator.getRandomPassword;

public class AuthTest {
    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisrteredActiveUser() {
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id=login] input"). setValue(registeredUser.getLogin());
        $("[data-test-id=password] input"). setValue(registeredUser.getPassword());
        $("button.button"). click();
        $("h2").shouldHave(Condition.exactText("Личный кабинет")).shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should get error message if user is not registered")
    void shouldGetErrorMessageIfUserIsNotRegistered() {
        var notRegosteredUser = getUser("active");
        $("[data-test-id=login] input"). setValue(notRegosteredUser.getLogin());
        $("[data-test-id=password] input"). setValue(notRegosteredUser.getPassword());
        $("button.button"). click();
        $("[Data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(10))
                .shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should get error message if login is wrong")
    void shouldGetErrorMessageIfLoginIsWrong() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();
        $("[data-test-id=login] input"). setValue(wrongLogin);
        $("[data-test-id=password] input"). setValue(registeredUser.getPassword());
        $("button.button"). click();
        $("[Data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(10))
                .shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should get error message if password is wrong")
    void shouldGetErrorMessageIfPasswordIsWrong() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();
        $("[data-test-id=login] input"). setValue(registeredUser.getLogin());
        $("[data-test-id=password] input"). setValue(wrongPassword);
        $("button.button"). click();
        $("[Data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(10))
                .shouldBe(Condition.visible);
    }


}


