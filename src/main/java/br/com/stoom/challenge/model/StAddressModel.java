package br.com.stoom.challenge.model;

import org.springframework.lang.NonNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class StAddressModel {

	@NonNull
	private String streetName;

	@NonNull
	private String number;
	private String complement;

	@NonNull
	private String neighbourhood;
	
	@NonNull
	private String city;

	@NonNull
	private String state;
	@NonNull
	private String country;
	@NonNull
	private String zipcode;
	private String latitude;
	private String longitude;	
}
