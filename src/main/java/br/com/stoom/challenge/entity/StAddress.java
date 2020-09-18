package br.com.stoom.challenge.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;

import br.com.stoom.challenge.model.StAddressModel;


import java.io.Serializable;
import java.util.UUID;

@Entity
public class StAddress implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	@NonNull
	private String streetName;
	@NonNull
	private Integer number;
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

	public StAddressModel fromEntityToModel() {
		StAddressModel stAddressModel = new StAddressModel();
		BeanUtils.copyProperties(stAddressModel, this);

		return stAddressModel;
	}
	
	public StAddress fromModelToEntity(StAddressModel stAddressModel) {
		StAddress stAddress = new StAddress();
		BeanUtils.copyProperties(this, stAddressModel);
		
		return stAddress;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	public String getNeighbourhood() {
		return neighbourhood;
	}

	public void setNeighbourhood(String neighbourhood) {
		this.neighbourhood = neighbourhood;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

}
