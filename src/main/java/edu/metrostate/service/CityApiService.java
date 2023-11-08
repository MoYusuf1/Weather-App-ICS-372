package edu.metrostate.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.metrostate.model.City2;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

// https://ipstack.com/quickstart
public class CityApiService {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final String API_URL = "http://api.ipstack.com/%s?access_key=%s";
    private static final String API_KEY = "121a52ed2e8ee407074ae7b20af7888e";


    public City2 getCity(String ipAddress) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            String url = String.format(API_URL, ipAddress, API_KEY);
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);
            String jsonString = EntityUtils.toString(response.getEntity());
            City2 city2 = OBJECT_MAPPER.readValue(jsonString, City2.class);
            System.out.printf("Found city for ipAddress=%s : %s", ipAddress, city2);
            return city2;
        } catch (Exception ex) {
            System.out.printf("Problem finding city for ipAddress=%s so using default : %s", ipAddress, ex.getMessage());
            return City2.DEFAULT;
        }
    }

}
