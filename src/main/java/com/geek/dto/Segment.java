package com.geek.dto;

import com.geek.dto.ArrivalAirport;
import com.geek.dto.DepartureAirport;
import lombok.Getter;

@Getter
public class Segment {

    private DepartureAirport departureAirport;
    private ArrivalAirport arrivalAirport;
    private String departureTime;
    private String arrivalTime;

}
