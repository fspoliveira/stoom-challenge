package br.com.stoom.challenge.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.stoom.challenge.entity.StAddress;
import br.com.stoom.challenge.model.StAddressModel;
import br.com.stoom.challenge.service.StAddressService;

@RestController
@RequestMapping("/stoom/api/ecommerce")
public class StAddressController {

	@Autowired
	StAddressService stAddressService;

	@PostMapping
	public ResponseEntity<StAddressModel> updateAddress(@Valid 	@RequestBody StAddressModel stAddressModel) {
		return stAddressService.save(stAddressModel);
	}

	@GetMapping
	public ResponseEntity<?> getAllAdddress() {
		return ResponseEntity.ok(stAddressService.getAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<StAddressModel> getAddressById(@PathVariable("id") String id) {
		return ResponseEntity.ok(stAddressService.findById(UUID.fromString(id)).fromEntityToModel());
	}

	@PutMapping("/{id}")
	public ResponseEntity<StAddressModel> updateAddress(@PathVariable("id") String id,
			@Valid @RequestBody StAddressModel stAddressModel) {

		StAddress stAddress = new StAddress().fromModelToEntity(stAddressModel).toBuilder().id(UUID.fromString(id))
				.build();
		return ResponseEntity.ok(stAddressService.update(stAddress).fromEntityToModel());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteAddressById(@PathVariable("id") String id) {
		stAddressService.delete(UUID.fromString(id));
		return ResponseEntity.noContent().build();
	}
}
