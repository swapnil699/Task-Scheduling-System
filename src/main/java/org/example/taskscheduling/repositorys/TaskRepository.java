package org.example.taskscheduling.repositorys;

import org.example.taskscheduling.models.Status;
import org.example.taskscheduling.models.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStatus(Status status);
    Page<Task> findByProjectId(Long projectId, Pageable pageable);
}
