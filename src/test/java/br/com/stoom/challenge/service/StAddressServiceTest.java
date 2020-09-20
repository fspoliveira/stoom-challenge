package br.com.stoom.challenge.service;

import br.com.stoom.challenge.com.google.service.GoogleAPIModel;
import br.com.stoom.challenge.com.google.service.GoogleAPIService;
import br.com.stoom.challenge.entity.StAddress;
import br.com.stoom.challenge.exception.StAddressNotFoundException;
import br.com.stoom.challenge.model.StAddressModel;
import br.com.stoom.challenge.repository.StAddressRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class StAddressServiceTest {

	private StAddressRepository stAddressRepository = mock(StAddressRepository.class);
	private GoogleAPIService googleAPI = mock(GoogleAPIService.class);
	private StAddressService stAddressService = new StAddressService(stAddressRepository, googleAPI);

	@Test
    public void shouldSaveAddress() {
        var expected = stAddress();
        when(googleAPI.getLatAndLng(any())).thenReturn(new GoogleAPIModel("123","456"));           
       
        when(stAddressRepository.save(any())).thenReturn(expected);
        var actual = stAddressService.save(expected.fromEntityToModel());
        Assertions.assertThat(actual)
            .isEqualTo(expected.fromEntityToModel());
    }

	@Test
	public void shouldReturnList() {
		var expected = List.of(stAddress(), stAddress());
		when(stAddressRepository.findAll()).thenReturn(expected);
		var actual = stAddressService.getAll();
		Assertions.assertThat(actual)
				.isEqualTo(expected.stream().map(StAddress::fromEntityToModel).collect(Collectors.toList()));
	}

	@Test
	public void shouldfindById() {
		var expected = stAddress();
		when(stAddressRepository.findById(any())).thenReturn(Optional.of(expected));
		var actual = stAddressService.findById(UUID.randomUUID());
		Assertions.assertThat(actual).isEqualTo(expected.fromEntityToModel());
	}

	@Test
	public void shouldThrowWhenFindByIdReturnEmpty() {
		when(stAddressRepository.findById(any())).thenReturn(Optional.empty());
		org.junit.jupiter.api.Assertions.assertThrows(StAddressNotFoundException.class,
				() -> stAddressService.findById(UUID.randomUUID()));
	}

	@Test
	public void shouldDeleteById() {
		var id = UUID.randomUUID();
		stAddressService.delete(id);
		verify(stAddressRepository, times(1)).deleteById(id);
	}

	private StAddress stAddress() {
		return StAddress.builder().streetName("StreetName").number("123").neighbourhood("123").city("123").state("13")
				.country("123").zipcode("123").build();
	}
}
