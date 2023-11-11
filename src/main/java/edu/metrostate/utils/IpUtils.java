package edu.metrostate.utils;

import com.google.common.net.InetAddresses;
import edu.metrostate.model.City2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

public final class IpUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(IpUtils.class);

    private static final List<String> IP_ADDRESS_LOOKUP_URLS = List.of(
            "http://checkip.amazonaws.com", "https://ipv4.icanhazip.com",
            "http://myexternalip.com/raw", "http://ipecho.net/plain"
    );

    private IpUtils() {
        throw new IllegalAccessError("No way, Jose!");
    }

    public static String getIpAddress() {
        for (String ipAddressLookupUrl : IP_ADDRESS_LOOKUP_URLS) {
            // https://www.baeldung.com/java-get-ip-address
            try (BufferedReader br = new BufferedReader(new InputStreamReader(new URL(ipAddressLookupUrl).openStream()))) {
                String response = br.readLine();
                if (InetAddresses.isInetAddress(response)) {
                    return response;
                }
            } catch (Exception ex) {
                LOGGER.error("Problem getting user's IP address using {}", ipAddressLookupUrl, ex);
            }
        }
        LOGGER.info("Unable to get user's IP address so using default {}", City2.METRO_STATE_UNIVERSITY.getIpAddress());
        return City2.METRO_STATE_UNIVERSITY.getIpAddress();
    }

}
