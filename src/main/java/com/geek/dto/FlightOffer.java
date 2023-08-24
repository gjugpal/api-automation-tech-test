package com.geek.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class FlightOffer {

        private String token;
        private List<Segment> segments;
        private String tripType;
        private BrandedFareInfo brandedFareInfo;
        private SeatAvailability seatAvailability;

}
