package com.myproject.task;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
    "spring.security.user.password=pass",
    "security.enable-csrf=false",
    "security.sessions=if_required"
})
public class SecurityTest {

  @LocalServerPort
  private int port;

  @Before
  public void init() {
    RestAssured.port = port;
    RestAssured.filters(new ResponseLoggingFilter());
    RestAssured.filters(new RequestLoggingFilter());
  }

  @Test
  public void apiCallWithAuthenicationMustFail() {
    when()
        .get("/tasks")
        .then()
        .statusCode(HttpStatus.UNAUTHORIZED.value());
  }

  @Test
  public void apiCallWithAuthenicationMustSucceed() {

    given()
        .auth().preemptive().basic("root", "root")
        .when()
        .get("/tasks")
        .then()
        .statusCode(HttpStatus.OK.value());
  }

}
