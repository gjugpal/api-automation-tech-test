package com.geek.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class FlightDetailsByToken {

    private String token;
    private List<Segment> segments;
}
