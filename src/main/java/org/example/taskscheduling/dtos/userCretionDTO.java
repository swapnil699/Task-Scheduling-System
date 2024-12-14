package org.example.taskscheduling.dtos;

import lombok.Data;
import org.example.taskscheduling.models.Roles;

@Data
public class userCretionDTO {
    String name;
    String email;
    Roles roles;
}
