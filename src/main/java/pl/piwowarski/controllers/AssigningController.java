package pl.piwowarski.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.piwowarski.Dto.TaskToUserDto;
import pl.piwowarski.model.Task;
import pl.piwowarski.model.User;
import pl.piwowarski.service.TaskService;
import pl.piwowarski.service.UserService;

import java.util.Set;

@Controller
public class AssigningController {

    private TaskService taskService;

    private UserService userService;

    @Autowired
    public AssigningController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping("/assign/show-free-task")
    public String assignFreeTasks(Model model) {
        model.addAttribute("task", new Task());
        model.addAttribute("freeTasks", taskService.getAllFreeTask());
        model.addAttribute("completedTasks", taskService.getAllCompletedTask());
        return "tasksAssignForm";
    }

    @GetMapping("/assign/assign-task/{taskIt}")
    public String showAvailableUserForTask(@PathVariable("taskIt") Long taskIt, @Param("keyword") String keyword, Model model) {
        Task taskById = taskService.getTaskById(taskIt);
        model.addAttribute("taskToAssignName", taskById.getName());
        model.addAttribute("taskId", taskIt);
        model.addAttribute("allUsers", userService.findAllUsersByKeyword(keyword));

        TaskToUserDto taskToUserDto = new TaskToUserDto();
        taskToUserDto.setTaskId(taskIt);
        model.addAttribute("task", taskToUserDto);

        return "assignTask";
    }

    @PostMapping("/assign/assign-task")
    public String assignTask(@ModelAttribute(value = "task") TaskToUserDto dto, Model model) {
        Long taskId = (dto.getTaskId());
        Long userId = dto.getUserId();
        taskService.assignUserToTask(taskId, userId);
        return "redirect:/assign/show-free-task";
    }

    @GetMapping("/assign/remove-task/{taskIt}")
    public String removeTaskFromUser(@PathVariable("taskIt") Long taskIt, Model model) {
        Task taskById = taskService.getTaskById(taskIt);
        model.addAttribute("taskToRemoveName", taskById.getName());
        Set<User> allUsersWithTask = taskService.getTaskById(taskIt).getOwners();
        model.addAttribute("allUsers", allUsersWithTask);
        TaskToUserDto taskToUserDto = new TaskToUserDto();
        taskToUserDto.setTaskId(taskIt);
        model.addAttribute("taskToRemove", taskIt);
        model.addAttribute("task", taskToUserDto);
        return "removeTask";
    }

    @PostMapping("/assign/remove-task")
    public String removeTask(@ModelAttribute(value = "task") TaskToUserDto dto, Model model) {
        Long taskId = (dto.getTaskId());
        Long userId = dto.getUserId();
        taskService.removeUserTask(taskId, userId);
        return "redirect:/assign/show-free-task";
    }

}
