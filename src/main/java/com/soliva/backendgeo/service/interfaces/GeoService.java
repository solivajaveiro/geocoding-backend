package com.soliva.backendgeo.service.interfaces;

import com.soliva.backendgeo.dto.GeocodeDto;

import java.util.List;

public interface GeoService {

    List<Double> getDistanceReturnGeocode(GeocodeDto address);
}
