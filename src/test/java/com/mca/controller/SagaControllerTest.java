package com.mca.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@DirtiesContext
@SpringBootTest
@WebAppConfiguration
class SagaControllerTest {
	@Autowired
	private WebApplicationContext webApplicationContext;
	private MockMvc mockMvc;
	private static String URL = "/game/{gameId}/saga";
	
	@BeforeEach
	public void setup() throws Exception {
	    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
	}

	@Test
	void testInput9() throws Exception {
		mockMvc.perform(get(URL, 9)).andExpect(status().is(HttpStatus.OK.value()))
				.andExpect(jsonPath("$.[0].id", is("11"))).andExpect(jsonPath("$.[0].title", is("Relic Hunter: Curse of the Emerald Eye")))
				.andExpect(jsonPath("$.[0].price", is(39.99))).andExpect(jsonPath("$.[0].availability", is(false)))
				.andExpect(jsonPath("$.[1].id", is("15"))).andExpect(jsonPath("$.[1].title", is("Cosmic Empires: Twilight of the Overlords")))
				.andExpect(jsonPath("$.[1].price", is(49.99))).andExpect(jsonPath("$.[1].availability", is(true)))
				.andExpect(jsonPath("$.[2].id", is("19"))).andExpect(jsonPath("$.[2].title", is("Arcane Realms Online: Siege of the Sorcerer")))
				.andExpect(jsonPath("$.[2].price", is(29.99))).andExpect(jsonPath("$.[2].availability", is(true)))
				.andReturn().getResponse().getContentAsString();
	}

	@Test
	void testInput12() throws Exception {
		System.out.println(mockMvc.perform(get(URL, 12)).andReturn().getResponse().getContentAsString());
		mockMvc.perform(get(URL, 12)).andExpect(status().is(HttpStatus.OK.value()))
				.andExpect(jsonPath("$.[0].id", is("20"))).andExpect(jsonPath("$.[0].title", is("Wasteland Chronicles: Edge of Survival")))
				.andExpect(jsonPath("$.[0].price", is(24.99))).andExpect(jsonPath("$.[0].availability", is(false)))
				.andExpect(jsonPath("$.[1].id", is("18"))).andExpect(jsonPath("$.[1].title", is("Eternal Battlefront: Rise of the Phoenix")))
				.andExpect(jsonPath("$.[1].price", is(59.99))).andExpect(jsonPath("$.[1].availability", is(true)))
				.andExpect(jsonPath("$.[2].id", is("19"))).andExpect(jsonPath("$.[2].title", is("Arcane Realms Online: Siege of the Sorcerer")))
				.andExpect(jsonPath("$.[2].price", is(29.99))).andExpect(jsonPath("$.[2].availability", is(true)))
				.andReturn().getResponse().getContentAsString();
	}

}