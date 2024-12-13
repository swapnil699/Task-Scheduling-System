package org.example.taskscheduling.repositorys;

import org.example.taskscheduling.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project,Long> {

}
