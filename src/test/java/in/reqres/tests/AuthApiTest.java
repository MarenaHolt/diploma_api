package in.reqres.tests;

import in.reqres.data.TestData;
import in.reqres.models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static in.reqres.specs.LoginSpec.*;
import static in.reqres.specs.LoginSpec.loginRequestSpec;
import static in.reqres.utils.FakeUtils.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthApiTest {

    LoginBodyLombokModel authData = new LoginBodyLombokModel();
    TestData testData = new TestData();

    @Test
    @Tag("api")
    @DisplayName("Successful login auth")
    void successfulLoginTest() {
        authData.setEmail(testData.getDefaultSuccessEmail());
        authData.setPassword(testData.getDefaultSuccessPassword());

        LoginResponseLombokModel loginResponse = step("Make request", () ->
                given(loginRequestSpec)
                        .body(authData)
                        .when()
                        .post("/login")
                        .then()
                        .spec(loginResponseSpec)
                        .extract().as(LoginResponseLombokModel.class));

        step("Check response", () -> {
            assertEquals(testData.getDefaultSuccessToken(), loginResponse.getToken());
        });
    }

    @Test
    @Tag("api")
    @DisplayName("Error with missing password")
    void missingPasswordTest() {
        authData.setEmail(getFakeEmail());

        MissingPasswordLombokModel missingPasswordResponse =
                step("Make request", () ->
                        given(loginRequestSpec)
                                .body(authData)
                                .when()
                                .post("/login")
                                .then()
                                .spec(missingPassword400Spec)
                                .extract().as(MissingPasswordLombokModel.class));

        step("Check response", () -> {
            assertEquals("Missing password", missingPasswordResponse.getError());
        });
    }
}
