package com.soliva.backendgeo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Geometry {
    private Location location;
    private String location_type;
    private ViewPort viewport;
}
