package br.com.stoom.challenge.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.stoom.challenge.entity.StAddress;
import br.com.stoom.challenge.model.StAddressModel;
import br.com.stoom.challenge.repository.StAddressRepository;

@Service
public class StAddressService {

	@Autowired
	StAddressRepository stAddressRepository;

	public ResponseEntity<StAddressModel> createAddress(@RequestBody StAddressModel stAddressModel) {
		StAddress stAddress = stAddressRepository.save(new StAddress().fromModelToEntity(stAddressModel));
		return new ResponseEntity<>(stAddress.fromEntityToModel(), HttpStatus.CREATED);
	}

	public List<StAddress> getAll() {
		return stAddressRepository.findAll();
	}

	public StAddress findById(UUID id) {
		return stAddressRepository.findById(id).orElseThrow(IllegalArgumentException::new);
	}

	public void delete(UUID id) {
		stAddressRepository.deleteById(id);
	}
}
