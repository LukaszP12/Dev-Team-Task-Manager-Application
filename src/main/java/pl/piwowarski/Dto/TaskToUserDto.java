package pl.piwowarski.Dto;

public class TaskToUserDto {

    private Long taskId;

    private Long userId;

    public TaskToUserDto() {
    }

    public TaskToUserDto(Long taskId, Long userId) {
        this.taskId = taskId;
        this.userId = userId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
