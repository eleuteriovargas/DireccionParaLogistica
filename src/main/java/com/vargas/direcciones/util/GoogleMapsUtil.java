package com.vargas.direcciones.util;

import org.springframework.stereotype.Component;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class GoogleMapsUtil {

    public String generarLinkMapa(String direccion, String ciudad, String pais) {
        try {
            String query = URLEncoder.encode(
                    String.format("%s, %s, %s",
                            direccion != null ? direccion : "",
                            ciudad != null ? ciudad : "",
                            pais != null ? pais : ""),
                    StandardCharsets.UTF_8.toString()
            );
            return "https://www.google.com/maps?q=" + query;
        } catch (Exception e) {
            return "https://www.google.com/maps";
        }
    }
}