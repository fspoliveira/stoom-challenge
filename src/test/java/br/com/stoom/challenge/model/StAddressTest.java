package br.com.stoom.challenge.model;

import java.util.UUID;

import br.com.stoom.challenge.entity.StAddress;
import br.com.stoom.challenge.repository.StAddressRepository;

public class StAddressTest {

	public static StAddress createSimpleData(StAddressRepository StAddressRepository) {
		return StAddressRepository.save(aSimpleStAddress());
	}

	public static StAddress aSimpleStAddressWithoutLatLon() {
		return aSimpleStAddress().toBuilder().latitude(null).longitude(null).build();
	}

	public static StAddress aSimpleStAddressWithId(UUID uuid) {
		return aSimpleStAddress().toBuilder().id(uuid).build();
	}

	public static StAddress aSimpleStAddress() {
		return StAddress.builder().streetName("Some Street name").city("Some City").country("BR").latitude("-22.877083")
				.longitude("-47.048379").neighbourhood("Some neighbourhood").number("123").state("Some State")
				.zipcode("13076-418").complement("Some complement").build();
	}

	public static StAddress aRealStAddress() {
		return StAddress.builder().streetName("R. Campo Redondo").city("Campinas").country("BR").latitude("-22.877083")
				.longitude("-47.048379").neighbourhood("JMaria Eugência").number("277").state("SP").zipcode("13050-152")
				.build();
	}
}
