package edu.metrostate.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import edu.metrostate.model.City2;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.util.Scanner;

/*
 https://ipstack.com/quickstart
 */
public class CityApiService {

    private static final String API_URL =
            "http://api.ipstack.com/%s?access_key=%s";
    private static final String API_KEY = "121a52ed2e8ee407074ae7b20af7888e";

    public City2 getCity(String ipAddress) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            String url = String.format(API_URL, ipAddress, API_KEY);
            HttpGet request = new HttpGet(url);

            HttpResponse response = client.execute(request);
            String jsonResponse = new Scanner(response.getEntity().getContent()).useDelimiter("\\Z").next();
            System.out.println(jsonResponse);

            // TODO: replace JsonParser with ObjectMapper
            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();

            /*
             TODO: We need another way to get timeZone info or we could just leave it off
             The free plan does NOT support getting time_zone info and receive an error
             when adding it to list of fields to return in the response

             http://api.ipstack.com/75.168.97.227?access_key=121a52ed2e8ee407074ae7b20af7888e&fields=main,time_zone

 {"success":false,"error":{"code":105,"type":"function_access_restricted",
 "info":"The current subscription plan does not support this API endpoint."}}

            String timeZone = jsonObject.getAsJsonObject("time_zone").get("id").getAsString();
             */

            String continent = jsonObject.get("continent_name").getAsString();
            String country = jsonObject.get("country_name").getAsString();
            String region = jsonObject.get("region_name").getAsString();
            String city = jsonObject.get("city").getAsString();
            String zip = jsonObject.get("zip").getAsString();
            double latitude = jsonObject.get("latitude").getAsDouble();
            double longitude = jsonObject.get("longitude").getAsDouble();

            return new City2()
                    .setContinent(continent)
                    .setCountry(country)
                    .setRegion(region)
                    .setCity(city)
                    .setZip(zip)
                    .setLatitude(latitude)
                    .setLongitude(longitude);
        } catch (Exception ex) {
            ex.printStackTrace();
            return City2.CITY_NOT_FOUND.UNKNOWN;
        }
    }

}
