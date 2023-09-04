package in.reqres.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static in.reqres.helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.core.IsNull.notNullValue;

public class LoginSpec {
    public static RequestSpecification loginRequestSpec = with()
            .log().uri()
            .log().method()
            .log().body()
            .filter(withCustomTemplates())
            .contentType(JSON)
            .baseUri("https://reqres.in")
            .basePath("/api");

    public static ResponseSpecification loginResponseSpec =
            new ResponseSpecBuilder()
                    .log(STATUS)
                    .log(BODY)
                    .expectStatusCode(200)
                    .expectBody(matchesJsonSchemaInClasspath("schemas/success-login-response-schema.json"))
                    .build();

    public static ResponseSpecification missingPassword400Spec =
            new ResponseSpecBuilder()
                    .log(STATUS)
                    .log(BODY)
                    .expectStatusCode(400)
                    .expectBody(matchesJsonSchemaInClasspath("schemas/missing-password-response-schema.json"))
                    .build();

    public static ResponseSpecification delayedResponseSpec =
            new ResponseSpecBuilder()
                    .log(STATUS)
                    .log(BODY)
                    .expectStatusCode(200)
                    .expectBody(matchesJsonSchemaInClasspath("schemas/list-users-schema.json"))
                    .build();

    public static ResponseSpecification createUserResponseSpec =
            new ResponseSpecBuilder()
                    .log(STATUS)
                    .log(BODY)
                    .expectStatusCode(201)
                    .expectBody(matchesJsonSchemaInClasspath("schemas/create-user-response-schema.json"))
                    .build();

    public static ResponseSpecification getUserResponseSpec =
            new ResponseSpecBuilder()
                    .log(STATUS)
                    .log(BODY)
                    .expectStatusCode(200)
                    .expectBody(matchesJsonSchemaInClasspath("schemas/single-user-response-schema.json"))
                    .build();
}
