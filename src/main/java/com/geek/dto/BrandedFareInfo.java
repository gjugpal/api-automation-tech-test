package com.geek.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class BrandedFareInfo {

    private String fareName;
    private String cabinClass;
    private List<Object> features;
    private List<Object> fareAttributes;
    private List<Object> nonIncludedFeatures;
}
