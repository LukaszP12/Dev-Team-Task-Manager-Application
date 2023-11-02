package pl.piwowarski.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.security.test.context.support.WithMockUser;
import pl.piwowarski.model.Task;
import pl.piwowarski.service.TaskService;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


public class TaskControllerTest {

    @Mock
    TaskService taskServiceMock;

    @InjectMocks
    TaskController taskController;

    private MockMvc mockMvc;

    @Before
    @WithMockUser(username = "admin", authorities = {"ADMIN_USER"})
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
    }

    @Test
    public void whenEditingTask_ShouldReturnViewWithTaskAsModelAttributeAndStatusOK() throws Exception {
        // given
        Long id = 2L;

        // when
        when(taskServiceMock.getTaskById(id)).thenReturn(new Task());

        // then
        mockMvc.perform(get("/tasks/edit/2"))
                .andExpect(status().isOk())
                .andExpect(view().name("taskedit"))
                .andExpect(model().attribute("editedTask", instanceOf(Task.class)));
    }

    @Test
    public void when_setTaskComplete_redirection_invoke_and_task_set_as_completed() throws Exception {
        // given
        Long id = 1L;

        // when
        mockMvc.perform(get("/tasks/set-task-completed/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/assign/show-free-task"));
        // then
        verify(taskServiceMock, times(1)).setTaskToCompleted(id);
    }

}
