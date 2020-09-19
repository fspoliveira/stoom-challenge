package br.com.stoom.challenge.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.stoom.challenge.com.google.service.GoogleAPI;
import br.com.stoom.challenge.entity.StAddress;
import br.com.stoom.challenge.exception.StAddressNotFoundException;
import br.com.stoom.challenge.model.StAddressModel;
import br.com.stoom.challenge.repository.StAddressRepository;

@Service
public class StAddressService {

	@Autowired
	StAddressRepository stAddressRepository;

	@Autowired
	GoogleAPI googleAPI;

	public ResponseEntity<StAddressModel> save(@RequestBody StAddressModel stAddressModel) {
		StAddress stAddress = stAddressRepository.save(handleLatitudeAndLongitude(new StAddress().fromModelToEntity(stAddressModel)));
		return new ResponseEntity<>(stAddress.fromEntityToModel(), HttpStatus.CREATED);
	}

	public List<StAddress> getAll() {
		return stAddressRepository.findAll();
	}

	public StAddress findById(UUID id) {
		return stAddressRepository.findById(id).orElseThrow(StAddressNotFoundException::new);
	}

	public void delete(UUID id) {
		stAddressRepository.deleteById(id);
	}

	public StAddress update(StAddress stAddress) {
		stAddressRepository.findById(stAddress.getId()).orElseThrow(StAddressNotFoundException::new);
		return stAddressRepository.save(handleLatitudeAndLongitude(stAddress));
	}

	private StAddress handleLatitudeAndLongitude(StAddress address) {
		
		Map<String,String> latAndlng; 

		if(address.getLatitude().isBlank() || address.getLongitude().isBlank()) {
			latAndlng = googleAPI.getLatAndLng();
			address.setLatitude(latAndlng.get("lat"));
			address.setLongitude(latAndlng.get("lng"));
		}
		
		return address;
	}
}
