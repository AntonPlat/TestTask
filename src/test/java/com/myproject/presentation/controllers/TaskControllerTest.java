package com.myproject.presentation.controllers;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import com.myproject.model.entity.Task;
import com.myproject.model.enums.TaskUrgencyType;
import com.myproject.repositories.TaskRepository;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskControllerTest {

  @LocalServerPort
  private int port;

  private static boolean isInitialized = false;

  @Before
  public void init() {
    RestAssured.port = port;
    RestAssured.filters(new ResponseLoggingFilter());
    RestAssured.filters(new RequestLoggingFilter());

  }

  @Before
  public void initDb() {
    if (isInitialized) {
      return;
    }
    setupDb();
    isInitialized = true;
  }

  @Autowired
  private TaskRepository taskRepository;

  @Test
  public void testСreateTaskWhenOk() {
    Task task = new Task("testName ", "testDescripton ",
        LocalDate.now(), TaskUrgencyType.HIGH);

    Response response = given().auth().preemptive().basic("root", "root")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(task)
        .post("/tasks");

    assertEquals(HttpStatus.OK.value(), response.getStatusCode());
  }

  @Test
  public void testСreateTaskWhenInvalidRequestBody() {
    Task task = new Task(null, "testDescripton ",
        LocalDate.now(), TaskUrgencyType.HIGH);

    Response response = given().auth().preemptive().basic("root", "root")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(task)
        .post("/tasks");

    assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
  }

  @Test
  public void testGetAllTasksWhenPaginationOk() {

    Response response = given().auth().preemptive().basic("root", "root")
        .param("pageNumber", 1)
        .param("pageSize", 3)
        .get("/tasks/");

    long numbersTaskInOnePage = response.jsonPath().getList("id").stream().count();

    assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    assertEquals(3, numbersTaskInOnePage);
  }

  @Test
  public void testGetTaskByIdWhenOk() {
    Integer taskId = 1;

    Response response = given().auth().preemptive().basic("root", "root")
        .get("/tasks/" + taskId);

    assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    assertEquals(taskId, response.jsonPath().get("id"));
  }


  @Test
  public void testGetTaskByIdWhenTaskNotFound() {
    Integer taskId = 100;

    Response response = given().auth().preemptive().basic("root", "root")
        .get("/tasks/" + taskId);

    assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
  }

  @Test
  public void testGetTaskByIdWhenInvalidId() {
    Integer taskId = -5;

    Response response = given().auth().preemptive().basic("root", "root")
        .get("/tasks/" + taskId);

    assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
  }


  @Test
  public void testUpdateTaskWhenTaskNotExist() {
    Integer taskIdForUpdate = 100;
    Task task = new Task("testNameUpdate ", "testDescriptonUpdate ",
        LocalDate.now(), TaskUrgencyType.LOW);

    Response response = given().auth().preemptive().basic("root", "root")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(task)
        .put("/tasks/" + taskIdForUpdate);

    assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
  }

  @Test
  public void testUpdateTaskWhenInvalidRequestBody() {
    Integer taskIdForUpdate = 9;
    Task invalidTask = new Task("testNameUpdate ", "testDescriptonUpdate ",
        null, TaskUrgencyType.LOW);

    Response response = given().auth().preemptive().basic("root", "root")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(invalidTask)
        .put("/tasks/" + taskIdForUpdate);

    assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
  }

  @Test
  public void testUpdateTaskThenOk() {
    Integer taskIdForUpdate = 1;
    Task task = new Task("testNameUpdate ", "testDescriptonUpdate ",
        LocalDate.now(), TaskUrgencyType.LOW);

    Response response = given().auth().preemptive().basic("root", "root")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(task)
        .put("/tasks/" + taskIdForUpdate);

    assertEquals(HttpStatus.OK.value(), response.getStatusCode());
  }

  @Test
  public void testDeleteTaskWhenOk() {
    Integer taskId = 10;

    Response response = given().auth().preemptive().basic("root", "root")
        .delete("/tasks/" + taskId);

    assertEquals(HttpStatus.OK.value(), response.getStatusCode());
  }

  @Test
  public void testDeleteTaskWhenTaskNotFound() {
    Integer taskId = 100;

    Response response = given().auth().preemptive().basic("root", "root")
        .get("/tasks/" + taskId);

    assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
  }

  private void setupDb() {
    for (int i = 0; i < 20; i++) {
      Task task = new Task("testName " + i, "testDescripton " + i,
          LocalDate.now(), TaskUrgencyType.LOW);
      taskRepository.save(task);

    }
  }
}