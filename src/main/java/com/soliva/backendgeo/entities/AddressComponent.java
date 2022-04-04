package com.soliva.backendgeo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddressComponent implements Serializable {

    private String long_name;
    private String short_name;
    private Object types;
}
