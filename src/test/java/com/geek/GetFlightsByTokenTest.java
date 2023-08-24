package com.geek;

import com.geek.dto.ErrorResponse;
import com.geek.dto.FlightDetailsByToken;
import com.geek.dto.FlightOffers;
import com.geek.request.GetFlights;
import com.geek.request.GetFlightsByToken;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class GetFlightsByTokenTest extends BaseTest {

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


    @Test
    private void assertValidTokenReturns200OK() {
        HttpResponse response = RequestHandler.sendRequest(getFlights);
        FlightOffers flightOffers = new Gson().fromJson(response.getBody().toString(), FlightOffers.class);

        String token = flightOffers.getFlightOffers().get(0).getToken();

        GetFlightsByToken getFlightsByToken = GetFlightsByToken
                .builder()
                .token(token)
                .build();

        HttpResponse resp = RequestHandler.sendRequest(getFlightsByToken);
        assertThat(resp.getStatus(),is(200));
    }

    @Test
    private void assertInvalidTokenReturnsError() {
        GetFlightsByToken getFlightsByToken = GetFlightsByToken
                .builder()
                .token("abc-def")
                .build();

        HttpResponse resp = RequestHandler.sendRequest(getFlightsByToken);
        ErrorResponse errorMsg = new Gson().fromJson(resp.getBody().toString(), ErrorResponse.class);
        assertThat(errorMsg.getError().getCode(),is("SEARCH_GETFLIGHTDETAILS_EXPIRED"));
        assertThat(errorMsg.getError().getRequestId(),is(notNullValue()));
    }

    @Test
    private void assertTokenReturnsCorrectFlightDetails() {
        HttpResponse response = RequestHandler.sendRequest(getFlights);
        FlightOffers flightOffers = new Gson().fromJson(response.getBody().toString(), FlightOffers.class);

        String token = flightOffers.getFlightOffers().get(0).getToken();

        GetFlightsByToken getFlightsByToken = GetFlightsByToken
                .builder()
                .token(token)
                .build();

        HttpResponse resp = RequestHandler.sendRequest(getFlightsByToken);
        FlightDetailsByToken flightsByToken = new Gson().fromJson(resp.getBody().toString(), FlightDetailsByToken.class);

        assertThat(flightsByToken.getToken(),is(token));
        assertThat(flightsByToken.getSegments().get(0).getDepartureAirport().getCode(),is(flightOffers.getFlightOffers().get(0).getSegments().get(0).getDepartureAirport().getCode()));
        assertThat(flightsByToken.getSegments().get(0).getDepartureAirport().getName(),is(flightOffers.getFlightOffers().get(0).getSegments().get(0).getDepartureAirport().getName()));
    }
}
