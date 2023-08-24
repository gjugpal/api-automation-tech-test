package com.geek;

import com.geek.dto.FlightOffers;
import com.geek.request.GetFlights;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class GetFlightsTest extends BaseTest {

    private FlightOffers flightOffers;

    private GetFlights getFlights = GetFlights.builder()
            .type("ONEWAY")
            .adults("1")
            .cabinClass("FIRST")
            .from("LHR.AIRPORT")
            .to("CDG.AIRPORT")
            .fromCountry("GB")
            .toCountry("FR")
            .stops("1")
            .depart("2023-09-23")
            .sort("BEST")
            .build();

    @Test()
    private void assertResponseCodeIs200() {
        HttpResponse response = RequestHandler.sendRequest(getFlights);
        assertThat(response.getStatus(),is(200));
    }

    @Test
    private void assertOnlyFlightsWhichHaveAvailabilityAreReturned() {
        HttpResponse response = RequestHandler.sendRequest(getFlights);
        FlightOffers flightOffers = new Gson().fromJson(response.getBody().toString(), FlightOffers.class);

        long numFlightsWithAvailability = flightOffers.getFlightOffers().stream()
                .filter(flight -> flight.getSeatAvailability().getNumberOfSeatsAvailable() > 0)
                .count();

        assertThat((int) numFlightsWithAvailability, is(flightOffers.getFlightOffers().size()));
    }

    @Test
    private void assertOnlyFirstClassFlightOptionsAreReturnedWhenQueryingForFirstClassFlightsOnly() {
        HttpResponse response = RequestHandler.sendRequest(getFlights);
        FlightOffers flightOffers = new Gson().fromJson(response.getBody().toString(), FlightOffers.class);

        long numOfFirstClassFlights = flightOffers.getFlightOffers().stream()
                .filter(flight -> flight.getBrandedFareInfo().getCabinClass().equalsIgnoreCase("FIRST"))
                .count();

        assertThat((int) numOfFirstClassFlights, is(flightOffers.getFlightOffers().size()));
    }

    @Test
    private void assertOnlyFlightsWhereTheDepartureAirportMatchesTheQueriedAirportAreReturned() {
        HttpResponse response = RequestHandler.sendRequest(getFlights);
        FlightOffers flightOffers = new Gson().fromJson(response.getBody().toString(), FlightOffers.class);

        flightOffers.getFlightOffers()
                .forEach(flight -> {
                    assertThat(flight.getSegments().get(0).getDepartureAirport().getCode(), is("LHR"));
                    assertThat(flight.getSegments().get(0).getDepartureAirport().getCityName(),is("London"));
                });
    }

    @Test
    private void assertOnlyFlightsWhereTheArrivalAirportMatchesTheQueriedAirportAreReturned() {
        HttpResponse response = RequestHandler.sendRequest(getFlights);
        FlightOffers flightOffers = new Gson().fromJson(response.getBody().toString(), FlightOffers.class);

        flightOffers.getFlightOffers()
                .forEach(flight -> {
                    assertThat(flight.getSegments().get(0).getArrivalAirport().getCode(), is("CDG"));
                    assertThat(flight.getSegments().get(0).getArrivalAirport().getCityName(),is("Paris"));
                });
    }

    @Test
    private void assertAllFlightsReturnedAreSetToOneWayWhenQueryingForOneWayFlightsOnly() {
        HttpResponse response = RequestHandler.sendRequest(getFlights);
        FlightOffers flightOffers = new Gson().fromJson(response.getBody().toString(), FlightOffers.class);

        long numFlightsOneWay = flightOffers.getFlightOffers()
                .stream()
                .filter(flight -> flight.getTripType().equalsIgnoreCase("ONEWAY"))
                .count();

        assertThat((int) numFlightsOneWay,is(flightOffers.getFlightOffers().size()));
    }
 }
