package com.myproject.model.entity;


import com.myproject.model.enums.TaskUrgencyType;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table
public class Task {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  public Task() {
  }

  public Task(String name, String description, LocalDate localDateTime,
      TaskUrgencyType taskUrgencyType) {
    this.name = name;
    this.description = description;
    this.date = localDateTime;
    this.taskUrgencyType = taskUrgencyType;
  }

  @NotEmpty
  @NotNull
  private String name;

  public void setId(Long id) {
    this.id = id;
  }

  @NotEmpty(message = "description is empty")
  @NotNull
  private String description;

  @NotNull
  @DateTimeFormat(pattern = "yyyy-mm-dd")
  private LocalDate date;

  @Enumerated
  @NotNull
  private TaskUrgencyType taskUrgencyType;

  public String getName() {
    return name;
  }

  public Long getId() {
    return id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public TaskUrgencyType getTaskUrgencyType() {
    return taskUrgencyType;
  }

  public void setTaskUrgencyType(TaskUrgencyType taskUrgencyType) {
    this.taskUrgencyType = taskUrgencyType;
  }
}
