package com.project.theatermanager.infrastructure.rest;

import com.project.theatermanager.acl.dto.MovieDTO;
import com.project.theatermanager.acl.dto.SeatDTO;
import com.project.theatermanager.acl.dto.SessionDTO;
import com.project.theatermanager.acl.dto.TheaterDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Collections;
import java.util.List;

import static com.project.theatermanager.infrastructure.rest.MovieRestControllerTests.stubMovieDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TheaterRestControllerTests {

	private static final String THEATER_ENDPOINT = "/theater";
	private static final String MOVIE_ENDPOINT = "/movie";

	@Autowired
	private MockMvc mockMvc;

	@Test
	void createAndUpdateTheater() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		// EMPTY
		mockMvc.perform(get(THEATER_ENDPOINT).contentType(APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk())
				.andExpect(content().json("[]"));
		//CREATE
		TheaterDTO testTheater = stubTheaterDTO("TestName");

		ResultActions createResponse = mockMvc.perform(post(THEATER_ENDPOINT).contentType(APPLICATION_JSON)
				.content(mapper.writeValueAsString(testTheater)))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isCreated());
		String createdJson = createResponse.andReturn().getResponse().getContentAsString();
		TheaterDTO movieResult = mapper.readValue(createdJson, TheaterDTO.class);
		compareTheatersDTO(testTheater, movieResult);

		// UPDATE
		TheaterDTO testTheaterUpdate = stubTheaterDTO(1L,"TestName2", Collections.emptyList());

		ResultActions updateResponse = mockMvc.perform(put(THEATER_ENDPOINT +"/1").contentType(APPLICATION_JSON)
						.content(mapper.writeValueAsString(testTheaterUpdate)))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk());
		String updatedJson = updateResponse.andReturn().getResponse().getContentAsString();
		TheaterDTO updateResult = mapper.readValue(updatedJson, TheaterDTO.class);
		compareTheatersDTO(testTheaterUpdate, updateResult);

		ResultActions retrieveResponse = mockMvc.perform(get(THEATER_ENDPOINT +"/1").contentType(APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk());

		String retrieveJson = retrieveResponse.andReturn().getResponse().getContentAsString();
		TheaterDTO retrievedResult = mapper.readValue(retrieveJson, TheaterDTO.class);
		compareTheatersDTO(testTheaterUpdate, retrievedResult);

	}

	@Test
	void assignSeat() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		//CREATE
		TheaterDTO testTheater = stubTheaterDTO("TheaterName");

		mockMvc.perform(post(THEATER_ENDPOINT).contentType(APPLICATION_JSON)
						.content(mapper.writeValueAsString(testTheater)))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isCreated());

		MovieDTO testMovie = stubMovieDTO("TestName", "TestDescription");

		mockMvc.perform(post(MOVIE_ENDPOINT).contentType(APPLICATION_JSON)
						.content(mapper.writeValueAsString(testMovie)))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isCreated());

		ResultActions sessionResponse =mockMvc.perform(post(THEATER_ENDPOINT+ "/1/create-session/1").contentType(APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isCreated());

		String retrieveJson = sessionResponse.andReturn().getResponse().getContentAsString();
		SessionDTO retrievedResult = mapper.readValue(retrieveJson, SessionDTO.class);
		SeatDTO seat = retrievedResult.seats().stream().filter(seatDTO -> seatDTO.code().equals("a1")).findAny().orElseThrow();
		SeatDTO expectedSeat = stubSeatDTO(seat, "Gustavo");
		ResultActions theaterResponse = mockMvc.perform(put(THEATER_ENDPOINT+ "/1/1/reserve-seat/"+ expectedSeat.id()).contentType(APPLICATION_JSON)
						.content(mapper.writeValueAsString(expectedSeat)))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk());

		mockMvc.perform(get(THEATER_ENDPOINT+ "/1/retrieve-sessions/1").contentType(APPLICATION_JSON)
						.content(mapper.writeValueAsString(expectedSeat)))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk());

		String resultJson = theaterResponse.andReturn().getResponse().getContentAsString();
		TheaterDTO theaterResult = mapper.readValue(resultJson, TheaterDTO.class);
		SeatDTO seatResult = theaterResult.sessions().get(0).seats().stream().filter(seatDTO -> seatDTO.code().equals("a1")).findAny().orElseThrow();
		compareSeatsDTO(expectedSeat, seatResult);

	}

	private void compareTheatersDTO(TheaterDTO expected, TheaterDTO actual){
		assertEquals(expected.name(), actual.name());
	}

	private void compareSeatsDTO(SeatDTO expected, SeatDTO actual){
		assertEquals(expected.id(), actual.id());
		assertEquals(expected.code(), actual.code());
		assertEquals(expected.user(), actual.user());
	}

	private TheaterDTO stubTheaterDTO(String name){
		return new TheaterDTO(null, name, null);
	}

	private TheaterDTO stubTheaterDTO(Long id, String name, List<SessionDTO> sessions){
		return new TheaterDTO(id, name, sessions);
	}

	private SeatDTO stubSeatDTO(SeatDTO dto, String userData){
		return new SeatDTO(dto.id(), userData, dto.code());
	}

}
