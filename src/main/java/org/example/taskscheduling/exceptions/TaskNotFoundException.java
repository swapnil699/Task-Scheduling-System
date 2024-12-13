package org.example.taskscheduling.exceptions;

public class TaskNotFoundException extends Exception{
    public TaskNotFoundException(String msg){
        super(msg);
    }
}
