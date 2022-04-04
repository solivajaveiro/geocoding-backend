package com.soliva.backendgeo.controller;

import com.soliva.backendgeo.dto.GeocodeDto;
import com.soliva.backendgeo.service.interfaces.GeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/geoCoding")
public class GeoCodingController {
    private final GeoService geoCodingService;

    @Autowired
    public GeoCodingController(GeoService geoCodingService) {
        this.geoCodingService = geoCodingService;
    }

    @PostMapping
    public List<Double> getGeoCodingForLoc(@RequestBody @Valid GeocodeDto address) {
        return geoCodingService.getDistanceReturnGeocode(address);
    }
}
