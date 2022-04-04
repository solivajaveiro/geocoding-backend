package com.soliva.backendgeo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ViewPort {

    private Northeast northeast;
    private Southwest southwest;
}
