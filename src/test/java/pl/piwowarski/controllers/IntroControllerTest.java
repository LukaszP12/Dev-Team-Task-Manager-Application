package pl.piwowarski.controllers;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class IntroControllerTest {
    private MockMvc mockMvc;

    @Before
    public void setup() {
        IntroController introController = new IntroController();
        mockMvc = MockMvcBuilders.standaloneSetup(introController).build();
    }

    @Test
    public void testShowIndex() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk()).andExpect(view()
                        .name("index"));
    }
}