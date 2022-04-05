package com.filter.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.filter.PocFilterApplication;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = PocFilterApplication.class)
@AutoConfigureMockMvc
public class FilterControllerTest {

	@Autowired
	private MockMvc mvc;

	@Test
	public void testRequestWithFilter() throws Exception {

		mvc.perform(MockMvcRequestBuilders.get("/with-filter")).andExpect(MockMvcResultMatchers.status().isOk());

	}

	@Test
	public void testRequestWithoutFilter() throws Exception {

		mvc.perform(MockMvcRequestBuilders.get("/without-filter")).andExpect(MockMvcResultMatchers.status().isOk());

	}

}
