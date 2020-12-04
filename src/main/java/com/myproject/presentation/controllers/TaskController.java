package com.myproject.presentation.controllers;


import com.myproject.model.entity.Task;
import com.myproject.presentation.services.TaskService;
import java.util.List;
import javax.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/tasks")
@Validated
public class TaskController {

  @Autowired
  private TaskService taskService;

  @PostMapping
  public void createTask(@Validated @RequestBody Task task) {
    taskService.createTask(task);
  }


  @GetMapping
  public List<Task> getAllTasks(@RequestParam(defaultValue = "0") Integer pageNumber,
      @RequestParam(defaultValue = "5") Integer pageSize) {

    return taskService.getAll(pageNumber, pageSize);
  }


  @GetMapping("/{id}")
  public Task getTaskById(@Min(value = 1) @PathVariable Long id) {
    return taskService.getById(id);
  }


  @PutMapping("/{id}")
  public void updateTask(@Min(value = 1) @PathVariable Long id, @Validated @RequestBody Task task) {
    taskService.updateTask(id, task);

  }


  @DeleteMapping("/{id}")
  public void deleteTask(@Min(value = 1) @PathVariable Long id) {
    taskService.deleteTask(id);
  }
}
