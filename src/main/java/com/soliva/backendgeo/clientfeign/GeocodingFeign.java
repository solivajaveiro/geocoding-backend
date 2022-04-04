package com.soliva.backendgeo.clientfeign;

import com.soliva.backendgeo.entities.GeolocationResponse;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "geocoding", url = "${geocoding}")
public interface GeocodingFeign {

    @GetMapping
    @RequestLine(" GET /{address}")
    ResponseEntity<GeolocationResponse> getGeocoding(@Param java.net.URI address);
}
