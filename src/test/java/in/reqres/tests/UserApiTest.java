package in.reqres.tests;

import in.reqres.data.TestData;
import in.reqres.models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static in.reqres.specs.LoginSpec.*;
import static in.reqres.specs.LoginSpec.loginRequestSpec;
import static in.reqres.utils.FakeUtils.*;
import static in.reqres.utils.FakeUtils.getFakeUserId;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserApiTest {
    CreateUserModel createUserModel = new CreateUserModel();
    TestData testData = new TestData();

    @Test
    @Tag("api")
    @DisplayName("Get list delayed users")
    void delayedResponseTest() {
        DelayedResponseModel delayedResponseModel =
                step("Make request", () ->
                        given(loginRequestSpec)
                                .when()
                                .get("/users?delay=3")
                                .then()
                                .spec(delayedResponseSpec)
                                .extract().as(DelayedResponseModel.class));

        step("Check response", () -> {
            assertEquals(testData.getDefaultSuccessEmail(), delayedResponseModel.getUsers().get(3).getEmail());
            assertEquals(6, delayedResponseModel.getUsers().size());
        });
    }

    @Test
    @Tag("api")
    @DisplayName("Create user with name and job")
    void createUserTest() {
        String name = getFakeName();
        String job = getFakeJob();
        createUserModel.setName(name);
        createUserModel.setJob(job);

        CreateUserResponseModel createUserResponseModel =
                step("Make request", () ->
                        given(loginRequestSpec)
                                .when()
                                .post("/users")
                                .then()
                                .spec(createUserResponseSpec)
                                .extract().as(CreateUserResponseModel.class));

        step("Check response", () -> {
            assertNotNull(createUserResponseModel.getId());
            assertNotNull(createUserResponseModel.getCreatedAt());
        });
    }

    @Test
    @Tag("api")
    @DisplayName("Get user with id")
    void getSingleUserTest() {
        int clientId = getFakeUserId();

        SingleUserResponseModel singleUserResponseModel =
                step("Make request", () ->
                        given(loginRequestSpec)
                                .when()
                                //.get("/users/2")
                                .get("/users/" + clientId)
                                .then()
                                .spec(getUserResponseSpec)
                                .extract().as(SingleUserResponseModel.class));

        step("Check response", () -> {
            assertEquals(clientId, singleUserResponseModel.getUser().getId());
        });
    }

    @Test
    @Tag("api")
    @DisplayName("Delete user with id")
    void deleteUser() {
        int clientId = getFakeUserId();
        step("Delete user with id " + clientId, () ->
                given(loginRequestSpec)
                        .when()
                        .delete("/users/" + clientId)
                        .then()
                        .statusCode(204));
    }
}
