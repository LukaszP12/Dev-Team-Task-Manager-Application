package pl.piwowarski.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.piwowarski.repository.TaskRepository;
import pl.piwowarski.service.TaskService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TaskTest {

    @Mock
    private TaskRepository taskRepository;

//    protected static EntityManagerFactory emf;

    @PersistenceContext
    EntityManager entityManager;

//    @BeforeClass
//    public static void createEntityManagerFactory() {
//        emf = Persistence.createEntityManagerFactory("task-demo");
//    }
//
//    @AfterClass
//    public static void closeEntityManagerFactory() {
//        emf.close();
//    }
//
//    @Before
//    public void beginTransaction() {
//        em = emf.createEntityManager();
//        em.getTransaction().begin();
//    }

//    @After
//    public void rollbackTransaction() {
//        if (em.getTransaction().isActive()) {
//            em.getTransaction().rollback();
//        }
//
//        if (em.isOpen()) {
//            em.close();
//        }
//    }

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


//    @Test
//    public void tasks_are_persisted_when_user_is_added() {
//        // given
//        User user = new User("dummy user", "dummy123", "pass123");
//
//        Task task = new Task("sprint 2", "2 week of sprint", time.plusDays(7), "project manager tom");
//        Task task1 = new Task("sprint 3", "3 week of sprint", time.plusDays(14), "project manager tom");
//
//        Set<Task> tasks = Set.of(
//                task, task1
//        );
//
//        user.setTasksOwned(tasks);
//
//        // when
//        entityManager.persist(user);
//
//        // then
//        taskRepository.findAll().containsAll(tasks);
//    }

}