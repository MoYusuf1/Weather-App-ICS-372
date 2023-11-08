package edu.metrostate;

import edu.metrostate.model.City2;
import edu.metrostate.service.CityApiService;
import edu.metrostate.utils.IpUtils;

public class Test {

    public static void main(String[] args) {
        String ipAddress = IpUtils.getIpAddress();
        CityApiService cityApiService = new CityApiService();
        City2 city = cityApiService.getCity(ipAddress);
        System.out.println(city);
    }
}
