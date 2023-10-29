package pl.piwowarski;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.piwowarski.model.Role;
import pl.piwowarski.model.Task;
import pl.piwowarski.model.User;
import pl.piwowarski.repository.RoleRepository;
import pl.piwowarski.repository.TaskRepository;
import pl.piwowarski.repository.UserRepository;
import pl.piwowarski.service.UserService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Set;

@Component
public class StartInit implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserService userService;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        entityManager.persist(new Role("ADMIN_USER"));
        entityManager.persist(new Role("COMMON_USER"));

        User userLukaszP12 = new User("lukaszp4@onet.eu", "LukaszP12");
        userLukaszP12.setPassword(passwordEncoder.encode("dummy123"));
        userService.setRoleAsAdmin(userLukaszP12);

        Task task = new Task("Complete sprint", "spring lasting 2 week", LocalDate.now().plusDays(14), "Project Manager");
        Task task2 = new Task("Complete sprint 2", "spring lasting 2 week", LocalDate.now().plusDays(28), "Project Manager");

        Task task3 = new Task("Complete sprint 3", "spring lasting 3 week", LocalDate.now().plusDays(28), "Project Manager");

        userLukaszP12.setTasksOwned(Set.of(task, task2));
        entityManager.persist(userLukaszP12);

        taskRepository.save(task3);
    }
}
