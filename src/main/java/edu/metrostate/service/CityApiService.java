package edu.metrostate.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.metrostate.model.city.City;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class CityApiService {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    // https://ipstack.com/quickstart
    private static final String API_URL = "http://api.ipstack.com/%s?access_key=%s";
    private static final String API_KEY = "121a52ed2e8ee407074ae7b20af7888e";

    public City getCity(String ipAddress) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            String url = String.format(API_URL, ipAddress, API_KEY);
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);
            String jsonString = EntityUtils.toString(response.getEntity());
            City city = OBJECT_MAPPER.readValue(jsonString, City.class);
            System.out.printf("Found city for ipAddress=%s -- %s", ipAddress, city);
            return city;
        } catch (Exception ex) {
            System.out.printf("Problem finding city for ipAddress=%s so using default -- %s", ipAddress, ex.getMessage());
            return City.METRO_STATE_UNIVERSITY;
        }
    }

}
