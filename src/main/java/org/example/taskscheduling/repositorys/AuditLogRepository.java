package org.example.taskscheduling.repositorys;

import org.example.taskscheduling.models.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}
