package br.com.stoom.challenge.com.google.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.stoom.challenge.httpclient.StHttpClient;

@Service
public class GoogleAPI {

	@Autowired
	private StHttpClient stHttpClient;

	public Map<String, String> getLatAndLng() {

		JsonNode rootNode;
		Map<String, String> latAndLngMap = new HashMap<String, String>();

		rootNode = this.callGeocodingApi();

		latAndLngMap.put("lat", rootNode.get("results").get(0).get("geometry").get("location").get("lat").asText());
		latAndLngMap.put("lng", rootNode.get("results").get(0).get("geometry").get("location").get("lng").asText());

		return latAndLngMap;

	}

	private JsonNode callGeocodingApi() {

		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = null;

		try {
			rootNode = mapper.readTree(stHttpClient.callHttpClient(""));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rootNode;
	}
}
