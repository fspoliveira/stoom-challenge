package br.com.stoom.challenge.httpclient;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.stereotype.Service;

@Service
public class StHttpClient {

	public String callHttpClient(String URL) {

		var url = "https://maps.googleapis.com/maps/api/geocode/json?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA&key=AIzaSyDTK0igIQTCi5EYKL9tzOIJ9N6FUASGZos";

		var request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
		var client = HttpClient.newHttpClient();
		HttpResponse<String> response = null;
		try {
			response = client.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(response.body());

		return response.body();

	}
}
