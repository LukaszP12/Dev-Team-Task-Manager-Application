package pl.piwowarski.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import pl.piwowarski.repository.TaskRepository;
import pl.piwowarski.repository.UserRepository;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TaskIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TaskRepository taskRepository;

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

}
