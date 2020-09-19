package br.com.stoom.challenge.com.google.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.stoom.challenge.entity.StAddress;
import br.com.stoom.challenge.httpclient.StHttpClient;

@Service
public class GoogleAPIService {

	@Autowired
	private StHttpClient stHttpClient;

	@Value("${google.api.base-url}")
	private String URL;

	@Value("${google.api.api-key}")
	private String apiKey;

	public Map<String, String> getLatAndLng(StAddress stAddress) {

		JsonNode rootNode;
		Map<String, String> latAndLngMap = new HashMap<String, String>();

		rootNode = this.callGeocodingApi(stAddress);

		if (rootNode != null) {
			latAndLngMap.put("lat", rootNode.get("results").get(0).get("geometry").get("location").get("lat").asText());
			latAndLngMap.put("lng", rootNode.get("results").get(0).get("geometry").get("location").get("lng").asText());
		}

		return latAndLngMap;

	}

	private JsonNode callGeocodingApi(StAddress stAddress) {

		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = null;

		try {
			rootNode = mapper.readTree(stHttpClient.callHttpClient(this.createUrl(stAddress)));
		} catch (JsonProcessingException e) {			
			e.printStackTrace();
		}

		return rootNode;
	}

	private String createUrl(StAddress stAddress) {
		StringBuffer sb = new StringBuffer();
		sb.append(URL);
		sb.append(this.createParams(stAddress));
		sb.append(apiKey);

		return sb.toString();

	}

	private String createParams(StAddress address) {

		StringBuffer sb = new StringBuffer();
		sb.append("address=");
		sb.append(address.getStreetName());
		sb.append("+");
		sb.append(address.getNumber());
		sb.append("+");
		sb.append(address.getCity());
		sb.append("+");
		sb.append(address.getState());
		sb.append("+");
		sb.append(address.getCountry());
		sb.append("+");
		sb.append(address.getZipcode());
		sb.append("&key=");

		return sb.toString().replaceAll(" ", "+");
	}
}
