package pl.piwowarski.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.piwowarski.model.Task;
import pl.piwowarski.model.User;
import pl.piwowarski.repository.TaskRepository;
import pl.piwowarski.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private TaskRepository taskRepository;

    private UserRepository userRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void createTask(Task task) {
        taskRepository.save(task);
    }

    public Task getTaskById(Long taskId) {
        return taskRepository.findById(taskId).get();
    }

    public void updateTask(Long id, Task task) {
        Task taskById = taskRepository.findById(id).get();
        taskById.setName(task.getName());
        taskById.setDescription(task.getDescription());
        taskById.setDate(task.getDate());
        taskRepository.save(taskById);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public void setTaskToCompleted(Long id) {
        Task task = taskRepository.getOne(id);
        task.setCompleted(true);
        task.getOwners().clear();
        taskRepository.save(task);
    }

    public void setTaskToNotCompleted(Long id) {
        Task task = taskRepository.getOne(id);
        task.setCompleted(false);
        taskRepository.save(task);
    }

    public void assignUserToTask(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId).get();
        User user = userRepository.findById(userId).get();

        task.addOwner(user);
        taskRepository.save(task);
    }

    @Transactional
    public void removeUserTask(Long taskId, Long userId) {
        User user = userRepository.findById(userId).get();
        user.getTasksOwned().removeIf(t -> t.getId() == taskId);
    }

    public List<Task> getAllFreeTask() {
        return taskRepository.findAll().stream().filter(t -> !t.isCompleted()).collect(Collectors.toList());
    }

    public List<Task> getAllCompletedTask() {
        return taskRepository.findAll().stream().filter(t -> t.isCompleted()).collect(Collectors.toList());
    }

    public List<Task> findAllProducts(String keyword) {
        if (keyword != null) {
            return taskRepository.search(keyword);
        }
        return taskRepository.findAll();
    }
}
