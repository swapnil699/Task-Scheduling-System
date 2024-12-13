package org.example.taskscheduling.repositorys;

import org.example.taskscheduling.models.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
