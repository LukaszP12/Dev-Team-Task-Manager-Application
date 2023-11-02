package pl.piwowarski.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import pl.piwowarski.repository.TaskRepository;

import java.time.LocalDate;
import java.util.Set;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TaskIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TaskRepository taskRepository;

    private LocalDate time = LocalDate.now();

    @Test
    public void whenFindByName_thenReturnTask() {
        // given
        Task task = new Task("onboarding for new members",
                "lets welcome new members on board",
                LocalDate.now().plusDays(5),
                "Project Manager Tim");
        entityManager.persist(task);
        entityManager.flush();

        // when
        Task found = taskRepository.findById(task.getId()).get();

        // then
        Assert.assertEquals("onboarding for new members", found.getName());
    }

    @Test
    public void tasks_are_persisted_when_user_is_added() {
        // given
        User user = new User("mattcoder@yahoo.com", "Matty", "pass123");

        Task task = new Task("sprint 2", "2 week of sprint", time.plusDays(7), "project manager tom");
        Task task1 = new Task("sprint 3", "3 week of sprint", time.plusDays(14), "project manager tom");

        Set<Task> tasks = Set.of(
                task, task1
        );

        user.setTasksOwned(tasks);

        // when
        entityManager.persist(user);

        // then
        taskRepository.findAll().containsAll(tasks);
    }

}
