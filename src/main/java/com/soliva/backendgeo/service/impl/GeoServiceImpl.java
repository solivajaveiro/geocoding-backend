package com.soliva.backendgeo.service.impl;

import com.soliva.backendgeo.clientfeign.GeocodingFeign;
import com.soliva.backendgeo.dto.GeocodeDto;
import com.soliva.backendgeo.entities.GeolocationResponse;
import com.soliva.backendgeo.entities.Results;
import com.soliva.backendgeo.service.interfaces.GeoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class GeoServiceImpl implements GeoService {

    private static Logger logger = LoggerFactory.getLogger(GeoServiceImpl.class);

    private List<Double> resultsDistance = new ArrayList<>();

    @Value("${key}")
    private String key;

    @Value("${geocoding}")
    private String url;

    private final GeocodingFeign geocodingFeign;

    @Autowired
    public GeoServiceImpl(GeocodingFeign geocodingFeign) {
        this.geocodingFeign = geocodingFeign;
    }

    @Override
    public List<Double> getDistanceReturnGeocode(GeocodeDto address) {
        address.getAddress().forEach(x -> {
            x = concatenatingUrl(x);

            GeolocationResponse geolocationResponse = sendingFeign(x);

            geolocationResponse.getResults().forEach(this::euclideanDistanceCalculation);
        });

        Collections.sort(resultsDistance);
        return resultsDistance;
    }

    public GeolocationResponse sendingFeign(String address){
        logger.info("Buscando geolocalização...");
        GeolocationResponse geolocationResponse = geocodingFeign.getGeocoding(URI.create(address)).getBody();
        if(geolocationResponse == null){
            throw new RuntimeException("Não foi possível retornar a geolocalização!");
        }
        logger.info("Geolocalização encontrada!");
        return geolocationResponse;
    }

    public String concatenatingUrl(String address){
        return url + address.replace(" ", "+") + "&key=" + key;
    }

    public void euclideanDistanceCalculation(Results results) {
        Long lat1 = results.getGeometry().getViewport().getNortheast().getLat();
        Long lng1 = results.getGeometry().getViewport().getNortheast().getLng();

        Long lat2 = results.getGeometry().getViewport().getSouthwest().getLat();
        Long lng2 = results.getGeometry().getViewport().getSouthwest().getLng();

        if ((lat1.equals(lat2)) && (lng1.equals(lng2))) {
            throw new RuntimeException("same location!");
        } else {
            double theta = lng1 - lng2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            resultsDistance.add(dist);
        }
    }
}
