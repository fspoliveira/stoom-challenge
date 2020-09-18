package br.com.stoom.challenge.service;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.stoom.challenge.entity.StAddress;
import br.com.stoom.challenge.model.StAddressModel;
import br.com.stoom.challenge.repository.StAddressRepository;

@Service
public class StAddressService {

	@Autowired
	StAddressRepository stAddressRepository;

	@GetMapping
	public List<StAddress> getAll() {
		return stAddressRepository.findAll();
	}

	@PostMapping
	public ResponseEntity<StAddressModel> createAddress(@RequestBody StAddressModel stAddressModel) {

		StAddress stAddress = stAddressRepository.save(new StAddress().fromModelToEntity(stAddressModel));

		return new ResponseEntity<>(stAddress.fromEntityToModel(), HttpStatus.CREATED);
	}
}
