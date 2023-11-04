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
import pl.piwowarski.exceptions.TaskNotFoundException;
import pl.piwowarski.exceptions.UserNotFoundException;
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
    public String showAvailableUserForTask(@PathVariable("taskIt") Long taskIt, @Param("keyword") String keyword, Model model) throws TaskNotFoundException {
        Task taskById = taskService.getTaskById(taskIt).orElseThrow(() -> new TaskNotFoundException());
        model.addAttribute("taskToAssignName", taskById.getName());
        model.addAttribute("taskId", taskById.getId());
        model.addAttribute("allUsers", userService.findAllUsersByKeyword(keyword));

        TaskToUserDto taskToUserDto = new TaskToUserDto();
        taskToUserDto.setTaskId(taskById.getId());
        model.addAttribute("task", taskToUserDto);

        return "assignTask";
    }

    @PostMapping("/assign/assign-task")
    public String assignTask(@ModelAttribute(value = "task") TaskToUserDto dto, Model model) throws TaskNotFoundException, UserNotFoundException {
        Task taskById = taskService.getTaskById(dto.getTaskId()).orElseThrow(() -> new TaskNotFoundException());
        User userById = userService.getUserById(dto.getUserId()).orElseThrow(() -> new UserNotFoundException());

        Long taskId = taskById.getId();
        Long userId = userById.getId();
        taskService.assignUserToTask(taskId, userId);
        return "redirect:/assign/show-free-task";
    }

    @GetMapping("/assign/remove-task/{taskIt}")
    public String removeTaskFromUser(@PathVariable("taskIt") Long taskIt, Model model) throws TaskNotFoundException {
        Task taskById = taskService.getTaskById(taskIt).orElseThrow(() -> new TaskNotFoundException());

        model.addAttribute("taskToRemoveName", taskById.getName());
        Set<User> allUsersWithTask = taskById.getOwners();
        model.addAttribute("allUsers", allUsersWithTask);
        TaskToUserDto taskToUserDto = new TaskToUserDto();
        taskToUserDto.setTaskId(taskById.getId());
        model.addAttribute("taskToRemove", taskById.getId());
        model.addAttribute("task", taskToUserDto);
        return "removeTask";
    }

    @PostMapping("/assign/remove-task")
    public String removeTask(@ModelAttribute(value = "task") TaskToUserDto dto, Model model) throws TaskNotFoundException, UserNotFoundException {
        Task taskById = taskService.getTaskById(dto.getTaskId()).orElseThrow(() -> new TaskNotFoundException());
        User userById = userService.getUserById(dto.getUserId()).orElseThrow(() -> new UserNotFoundException());

        Long taskId = taskById.getId();
        Long userId = userById.getId();
        taskService.removeUserTask(taskId, userId);
        return "redirect:/assign/show-free-task";
    }

}
