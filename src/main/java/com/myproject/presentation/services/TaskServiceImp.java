package com.myproject.presentation.services;

import com.myproject.model.entity.Task;
import com.myproject.presentation.exceptions.TaskNotFoundException;
import com.myproject.repositories.TaskRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskServiceImp implements TaskService {

  private static final String TASK_NOT_FOUND_MESSAGE = "Task with id: %d not found";

  @Autowired
  private TaskRepository taskRepository;

  @Override
  public void createTask(Task task) {
    taskRepository.save(task);
  }


  @Override
  public List<Task> getAll(Integer page, Integer quantity) {
    Pageable pageable = PageRequest.of(page, quantity);
    return taskRepository.findAll(pageable).getContent();
  }

  @Override
  public Task getById(Long id) {
    return taskRepository.findById(id)
        .orElseThrow(
            () -> new TaskNotFoundException(String.format(TASK_NOT_FOUND_MESSAGE, id)));
  }

  @Override
  public Task updateTask(Long id, Task taskUpdate) {
    Task task = taskRepository.findById(id)
        .orElseThrow(
            () -> new TaskNotFoundException(String.format(TASK_NOT_FOUND_MESSAGE, id)));

    taskUpdate.setId(task.getId());
    taskRepository.save(taskUpdate);
    return taskUpdate;

  }

  @Override
  @Transactional
  public void deleteTask(Long id) {
    if (taskRepository.existsById(id)) {
      taskRepository.deleteById(id);
    } else {
      throw new TaskNotFoundException(String.format(TASK_NOT_FOUND_MESSAGE, id));
    }
  }
}
