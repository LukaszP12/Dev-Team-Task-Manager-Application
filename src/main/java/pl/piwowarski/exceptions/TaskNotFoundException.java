package pl.piwowarski.exceptions;

public class TaskNotFoundException extends Exception{
    public TaskNotFoundException() {
        super("Task not found in repo");
    }
}
