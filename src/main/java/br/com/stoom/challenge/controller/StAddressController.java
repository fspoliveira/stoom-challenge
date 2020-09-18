package br.com.stoom.challenge.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

	@GetMapping("/address")
	public ResponseEntity<List<StAddress>> getAllAdddress() {

		List<StAddress> listStAddress = stAddressService.getAll();

		return listStAddress.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
				: new ResponseEntity<>(listStAddress, HttpStatus.OK);
	}

	@PostMapping("/tutorials")
	public ResponseEntity<StAddressModel> createTutorial(@RequestBody StAddressModel stAddressModel) {
		return stAddressService.createAddress(stAddressModel);
	}
}
