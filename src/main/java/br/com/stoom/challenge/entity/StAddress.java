package br.com.stoom.challenge.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;

import br.com.stoom.challenge.model.StAddressModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Data
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class StAddress implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
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

	public StAddressModel fromEntityToModel() {

		StAddressModel stAddressModel = new StAddressModel();
		BeanUtils.copyProperties(this, stAddressModel);

		return stAddressModel;
	}

	public StAddress fromModelToEntity(StAddressModel stAddressModel) {
		BeanUtils.copyProperties(stAddressModel, this);
		return this;
	}
}
