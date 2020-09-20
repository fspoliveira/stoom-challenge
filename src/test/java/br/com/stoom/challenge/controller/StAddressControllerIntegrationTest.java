package br.com.stoom.challenge.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.stoom.challenge.entity.StAddress;
import br.com.stoom.challenge.model.ErrorModel;
import br.com.stoom.challenge.model.StAddressModel;
import br.com.stoom.challenge.model.StAddressTest;
import br.com.stoom.challenge.repository.StAddressRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Integration Testing for Address API")
class StAddressControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private StAddressRepository repository;

	@Test
	@DisplayName("[Read] Testing the find all addresses flow returning empty")
	public void whenIQueryForAllAddressesOnAEmptyDataSet_thenItShouldReturnEmptyResponse() throws Exception {
		// Given a simple GET request
		String responseBody = mockMvc.perform(get("/stoom/api/ecommerce").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		List<StAddressModel> addressModel = objectMapper.readValue(responseBody,
				new TypeReference<List<StAddressModel>>() {
				});
		assertThat(addressModel).hasSize(0);
	}

	@Test
	@DisplayName("[Read] Testing the find address by id flow")
	public void whenIQueryForAddressesById_thenItShouldReturnSpecificAddress() throws Exception {
		// Set up
		StAddress address = StAddressTest.createSimpleData(repository);
		// Given a simple GET request
		String responseBody = mockMvc
				.perform(get("/stoom/api/ecommerce/" + address.getId().toString())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		// Then it should return
		StAddressModel addressModel = objectMapper.readValue(responseBody, StAddressModel.class);
		assertThat(addressModel)
				.extracting(StAddressModel::getCity, StAddressModel::getLatitude, StAddressModel::getLongitude)
				.contains("Some City", "-22.877083", "-47.048379");
	}

	@Test
	@DisplayName("[Read] Testing the find address by id flow with invalid ID")
	public void whenIQueryForAddressesByIdWithInvalidId_thenItShouldReturnNotFoundWithError() throws Exception {
		// Given a simple GET request
		String responseBody = mockMvc
				.perform(get("/stoom/api/ecommerce/" + UUID.randomUUID().toString())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andReturn().getResponse().getContentAsString();
		// Then it should return
		ErrorModel addressModel = objectMapper.readValue(responseBody, ErrorModel.class);
		assertThat(addressModel).extracting(ErrorModel::getMessage).isEqualTo("Address not found");
	}

	@Test
	@DisplayName("[Read] Testing the find address by specific field flow with empty datase")
	public void whenIQueryForAddressesBySpecificField_witAnEmptyDataser_thenItShould200() throws Exception {
		// Set up
		// Given a simple GET request
		String responseBody = mockMvc
				.perform(get("/stoom/api/ecommerce").queryParam("field", "city").queryParam("value", "Some")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		// Then it should return
		List<StAddressModel> addressModel = objectMapper.readValue(responseBody,
				new TypeReference<List<StAddressModel>>() {
				});
		assertThat(addressModel).hasSize(0);
	}

	@Test
	@DisplayName("[Read] Testing the find address by invalid field flow")
	public void whenIQueryForAddressesByIdWithInvalidField_thenItShouldReturnInternalServerErrorWithMessage()
			throws Exception {
		// Given a simple GET request
		String responseBody = mockMvc
				.perform(get("/stoom/api/ecommerce").queryParam("field", "invalidField").queryParam("value", "Some")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isInternalServerError()).andReturn().getResponse().getContentAsString();
		// Then it should return
		ErrorModel addressModel = objectMapper.readValue(responseBody, ErrorModel.class);
		assertThat(addressModel).extracting(ErrorModel::getMessage).isEqualTo(
				"Invalid field name passed in query string! The possible values are [zipcode, streetName, city, latitude, longitude, country, neighbourhood, number, state, complement]. Bear in mind that it is case sensitive.");
	}

	@Test
	@DisplayName("[Delete] Testing the delete address by invalid id")
	public void whenIDeleteAddressById_AndItDoesntExist_thenItShouldReturnNotFoundWithMessage() throws Exception {
		// Given a simple GET request
		String responseBody = mockMvc
				.perform(delete("/stoom/api/ecommerce/" + UUID.randomUUID().toString())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andReturn().getResponse().getContentAsString();
		// Then it should return
		ErrorModel addressModel = objectMapper.readValue(responseBody, ErrorModel.class);
		assertThat(addressModel).extracting(ErrorModel::getMessage).isEqualTo("Address not found");
	}

	@Test
	@DisplayName("[Delete] Testing the delete address by id")
	public void whenIDeleteAddressById_thenItShouldReturn200() throws Exception {
		StAddress address = StAddressTest.createSimpleData(repository);
		// Given a simple GET request
		String responseBody = mockMvc
				.perform(delete("/stoom/api/ecommerce/" + address.getId()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent()).andReturn().getResponse().getContentAsString();
	}

	@Test
	@DisplayName("[Create] Testing the create address")
	public void whenIPostAValidAddress_thenItShouldReturn201() throws Exception {
		StAddressModel addressModel = realAddressModel();
		mockMvc.perform(post("/stoom/api/ecommerce").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(addressModel))).andExpect(status().isCreated())
				.andExpect(header().exists("Location"));
		StAddress address = repository.findAll().stream().findFirst().orElse(null);
		assertThat(address).extracting(StAddress::getStreetName).isEqualTo(addressModel.getStreetName());
	}

	@Test
	@DisplayName("[Create] Testing the create address with mandatory field empty")
	public void whenIPostAnAddress_withMandatoryFieldEmpty_thenItShouldReturn400() throws Exception {
		StAddressModel addressModel = realAddressModel().toBuilder().streetName(null).build();
		mockMvc.perform(post("/stoom/api/ecommerce").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(addressModel))).andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("[Create] Testing the create address without Lat/Lon")
	public void whenIPostAnAddress_withoutLatLon_thenItShouldReturn201_andFetchFromGoogle() throws Exception {
		StAddressModel addressModel = realAddressModel();
		mockMvc.perform(post("/stoom/api/ecommerce").contentType(MediaType.APPLICATION_JSON).content(
				objectMapper.writeValueAsString(addressModel.toBuilder().latitude(null).longitude(null).build())))
				.andExpect(status().isCreated()).andExpect(header().exists("Location"));
		StAddress address = repository.findAll().stream().findFirst().orElse(null);
		assertThat(address).extracting(StAddress::getLatitude, StAddress::getLongitude)
				.contains(addressModel.getLatitude(), addressModel.getLongitude());
	}

	@Test
	@DisplayName("[Update] Testing the update address with invalid id")
	public void whenIUpdateAnAddress_withInvalidId_thenItShouldReturn404() throws Exception {
		StAddressModel addressModel = realAddressModel();
		mockMvc.perform(put("/stoom/api/ecommerce/" + UUID.randomUUID().toString())
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(addressModel)))
				.andExpect(status().isNotFound());
	}

	private StAddressModel realAddressModel() {
		return StAddressModel.builder().streetName("R. Campo Redondo").city("Campinas").country("BR")
				.latitude("-22.8354045").longitude("-47.0787762").neighbourhood("Jardim Maria Eugência").number("277")
				.state("SP").zipcode("13151-042").build();
	}
}