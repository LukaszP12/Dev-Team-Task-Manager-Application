package pl.piwowarski.mapper;

import pl.piwowarski.Dto.TaskDto;
import pl.piwowarski.model.Task;
import pl.piwowarski.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class TaskMapper {

    public static TaskDto toDto(Task task) {
        TaskDto dto = new TaskDto();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus());
        dto.setDueDate(task.getDueDate());
        dto.setAssignedUserIds(
                task.getAssignedUsers().stream().map(User::getId).collect(Collectors.toList())
        );
        return dto;
    }

    public static Task toEntity(TaskDto dto, List<User> assignedUsers) {
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus());
        task.setDueDate(dto.getDueDate());
        task.setAssignedUsers(assignedUsers);
        return task;
    }
}
