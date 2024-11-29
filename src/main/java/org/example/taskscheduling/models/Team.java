package org.example.taskscheduling.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Team extends BaseModel {

    private String name;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<User> members;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<Project> projects;
}
