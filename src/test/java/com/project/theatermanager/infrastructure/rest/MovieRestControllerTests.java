package com.project.theatermanager.infrastructure.rest;

import com.project.theatermanager.acl.dto.MovieDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class MovieRestControllerTests {

	private static final String MOVIE_ENDPOINT = "/movie";

	@Autowired
	private MockMvc mockMvc;

	@Test
	void createAndUpdateMovie() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		// EMPTY
		mockMvc.perform(get(MOVIE_ENDPOINT).contentType(APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk())
				.andExpect(content().json("[]"));
		//CREATE
		MovieDTO testMovie = stubMovieDTO("TestName", "TestDescription");

		ResultActions createResponse = mockMvc.perform(post(MOVIE_ENDPOINT).contentType(APPLICATION_JSON)
				.content(mapper.writeValueAsString(testMovie)))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isCreated());
		String createdJson = createResponse.andReturn().getResponse().getContentAsString();
		MovieDTO movieResult = mapper.readValue(createdJson, MovieDTO.class);
		compareMoviesDTO(testMovie, movieResult);

		// UPDATE
		MovieDTO testMovieUpdate = stubMovieDTO(1L,"TestName2", "TestDescription2");

		ResultActions updateResponse = mockMvc.perform(put(MOVIE_ENDPOINT+"/1").contentType(APPLICATION_JSON)
						.content(mapper.writeValueAsString(testMovieUpdate)))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk());
		String updatedJson = updateResponse.andReturn().getResponse().getContentAsString();
		MovieDTO updateResult = mapper.readValue(updatedJson, MovieDTO.class);
		compareMoviesDTO(testMovieUpdate, updateResult);

		ResultActions retrieveResponse = mockMvc.perform(get(MOVIE_ENDPOINT+"/1").contentType(APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk());

		String retrieveJson = retrieveResponse.andReturn().getResponse().getContentAsString();
		MovieDTO retrievedResult = mapper.readValue(retrieveJson, MovieDTO.class);
		compareMoviesDTO(testMovieUpdate, retrievedResult);

	}

	private void compareMoviesDTO(MovieDTO expected, MovieDTO actual){
		assertEquals(expected.name(), actual.name());
		assertEquals(expected.description(), actual.description());
	}

	protected static MovieDTO stubMovieDTO(String name, String description){
		return new MovieDTO(null, name, description);
	}

	private MovieDTO stubMovieDTO(Long id, String name, String description){
		return new MovieDTO(id, name, description);
	}

}
