package pl.piwowarski.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.piwowarski.model.User;
import pl.piwowarski.service.UserService;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class RegisterControllerTest {

    @Mock
    UserService userService;

    @InjectMocks
    RegisterController registerController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(registerController).build();
    }

    @Test
    public void newuserform_returnsstatusOKAndFormSetAsViewAndUserAsAttributeInModel() throws Exception {
        mockMvc.perform(get("/registration/create-new-user"))
                .andExpect(status().isOk())
                .andExpect(view().name("newUser"))
                .andExpect(model().attribute("newUser", instanceOf(User.class)));
    }

}