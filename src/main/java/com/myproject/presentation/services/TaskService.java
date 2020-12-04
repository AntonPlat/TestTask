package com.myproject.presentation.services;

import com.myproject.model.entity.Task;
import java.util.List;

public interface TaskService {

  void createTask(Task task);

  List<Task> getAll(Integer pageNumber, Integer pageSize);

  Task getById(Long id);

  Task updateTask(Long id, Task task);

  void deleteTask(Long id);

}
