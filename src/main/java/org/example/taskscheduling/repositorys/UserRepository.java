package org.example.taskscheduling.repositorys;

import org.example.taskscheduling.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
