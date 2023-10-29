package pl.piwowarski.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.piwowarski.model.Task;
import pl.piwowarski.repository.TaskRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private LocalDate time = LocalDate.now();

    @Test
    public void task_set_one_tasks_as_completed() {
        // given
        Task task = new Task("sprint 2", "sprint during 2. week", time.plusDays(7), "project manager tom");

        Task task1 = new Task("sprint 3", "sprint during 3. week", time.plusDays(14), "project manager tom");
        task1.setCompleted(true);

        Task task2 = new Task("sprint 4", "sprint during 4. week", time.plusDays(21), "project manager tom");

        List<Task> upcomingTasks = new ArrayList<>();
        upcomingTasks.add(task);
        upcomingTasks.add(task1);
        upcomingTasks.add(task2);

        // when
        when(taskRepository.findAll()).thenReturn(upcomingTasks);
        TaskService ts = new TaskService(taskRepository);

        // then
        Assert.assertEquals(1, ts.getAllCompletedTask().size());
    }

    @Test
    public void task_set_two_tasks_as_completed() {
        // given
        Task task = new Task("sprint 2", "sprint during 2. week", time.plusDays(7), "project manager tom");

        Task task1 = new Task("sprint 3", "sprint during 3. week", time.plusDays(14), "project manager tom");
        task1.setCompleted(true);

        Task task2 = new Task("sprint 4", "sprint during 4. week", time.plusDays(21), "project manager tom");

        List<Task> upcomingTasks = new ArrayList<>();
        upcomingTasks.add(task);
        upcomingTasks.add(task1);
        upcomingTasks.add(task2);

        // when
        when(taskRepository.findAll()).thenReturn(upcomingTasks);
        TaskService ts = new TaskService(taskRepository);

        // then
        Assert.assertEquals(2, ts.getAllFreeTask().size());
    }

    @Test
    public void test_one_task_marked_as_completed() {
        // given
        Task task = new Task("onboarding for new member of development team", "first day with the team", time.plusDays(2), "team manager");
        task.setCompleted(false);
        task.setOwners(null);

        Task task1 = new Task("retro after spring", "quick review of progress so far and preparation for next sprint", time.plusDays(3), "team manager michael");
        task1.setCompleted(true);
        task1.setOwners(null);

        List<Task> tasks = new ArrayList<>();
        tasks.add(task);
        tasks.add(task1);

        // when
        when(taskRepository.findAll()).thenReturn(tasks);
        TaskService ts = new TaskService(taskRepository);

        // then
        Assert.assertEquals(1, ts.getAllCompletedTask().size());
    }

}