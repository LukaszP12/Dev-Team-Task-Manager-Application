package pl.piwowarski.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import pl.piwowarski.repository.UserRepository;

import java.time.LocalDate;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class UserTest {
    @Mock
    private UserRepository userRepository;

    private LocalDate time = LocalDate.now();

    @Before
    public void setUp() {
        User userRichard = new User("richard12@onet.pl", "rick12", "demo123");

        Mockito.when(userRepository.findByEmail(userRichard.getEmail()))
                .thenReturn(userRichard);
    }

    @Test
    public void whenValidEmail_thenUserShouldBeFound() {
        String email = "richard12@onet.pl";
        User found = userRepository.findByEmail(email);

        Assert.assertEquals(found.getEmail(), email);
    }

    @Test
    public void user_new_tasks_are_in_progress() {
        // given
        User user = new User("richard@onet.pl", "Richard", "richard123");

        // when
        user.setTasksOwned(Set.of(new Task("sprint 3", "spring for the 3. week", time.plusDays(7), "projecct manager tom"),
                new Task("sprint 4", "spring for the 4. week", time.plusDays(14), "projecct manager tom")));

        // then
        Assert.assertEquals(2, user.getTasksInProgress().size());
    }

    @Test
    public void user_one_task_marked_as_completed() {
        // given
        User user = new User("richard@onet.pl", "Richard", "richard123");

        Task task = new Task("sprint 3", "spring for the 3. week", time.plusDays(7), "projecct manager tom");
        Task task1 = new Task("sprint 4", "spring for the 4. week", time.plusDays(14), "projecct manager tom");
        task1.setCompleted(true);

        Set<Task> tasks_set = Set.of(task, task1);

        // when
        user.setTasksOwned(tasks_set);

        // then
        Assert.assertEquals(1, user.getTasksCompleted().size());
    }

}