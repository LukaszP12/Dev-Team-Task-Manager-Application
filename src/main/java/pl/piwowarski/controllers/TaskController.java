package pl.piwowarski.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.piwowarski.model.Task;
import pl.piwowarski.service.TaskService;
import pl.piwowarski.service.UserService;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class TaskController {

    private TaskService taskService;

    private UserService userService;

    @Autowired
    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping("/tasks/allTasks")
    public String displayAllTask(Model model, @Param("keyword") String keyword) {
        model.addAttribute("allTasks", taskService.findAllTasksByKeyword(keyword));

        return "tasks";
    }

    @GetMapping("/tasks/create")
    public String taskCreationForm(Model model, Principal principal) {
        Task task = new Task();
        task.setCreatorName(principal.getName());

        model.addAttribute("newtask", task);
        return "newTask";
    }

    @PostMapping("/tasks/create")
    public String createTask(@Valid @ModelAttribute(value = "newtask") Task task, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> {
                System.out.println(error.getObjectName() + " " + error.getDefaultMessage());
            });
            return "newTask";
        }
        {
            taskService.createTask(task);
            return "redirect:/tasks/allTasks";
        }
    }

    @GetMapping("/tasks/edit/{id}")
    public String editingTaskForm(@PathVariable Long id, Model model) {
        model.addAttribute("editedTask", taskService.getTaskById(id));
        return "taskedit";
    }

    @PostMapping("/tasks/edit/{id}")
    public String updateTask(@Valid @ModelAttribute(value = "editedTask") Task task, BindingResult bindingResult, @PathVariable Long id) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> {
                System.out.println(error.getObjectName() + " " + error.getDefaultMessage());
            });
            return "taskedit";
        }
        taskService.updateTask(id, task);
        return "redirect:/assign/show-free-task";
    }

    @GetMapping("/tasks/set-task-completed/{id}")
    public String setTaskCompleted(@PathVariable Long id) {
        taskService.setTaskToCompleted(id);
        return "redirect:/assign/show-free-task";
    }

    @GetMapping("/tasks/set-task-not-completed/{id}")
    public String setTaskToNotCompleted(@PathVariable Long id) {
        taskService.setTaskToNotCompleted(id);
        return "redirect:/assign/show-free-task";
    }

}
