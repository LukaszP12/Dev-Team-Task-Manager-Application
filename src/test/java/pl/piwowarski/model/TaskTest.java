package pl.piwowarski.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.piwowarski.repository.TaskRepository;
import pl.piwowarski.service.TaskService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TaskTest {

    @Mock
    private TaskRepository taskRepository;

    private LocalDate time = LocalDate.now();

    @Test
    public void test_created_task_is_not_completed_by_default() {
        // given
        Task task = new Task("sprint 5", "sprint for 5.week for development team", time.plusDays(5), "team manager paul");
        List<Task> tasks = new ArrayList<>();
        tasks.add(task);

        // when
        when(taskRepository.findAll()).thenReturn(tasks);
        TaskService ts = new TaskService(taskRepository);

        // then
        Assert.assertEquals(1, ts.getAllFreeTask().size());
    }

}
