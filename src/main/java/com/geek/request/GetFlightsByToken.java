package com.geek.request;

import com.geek.AbstractHttpSpecification;
import com.mashape.unirest.http.HttpMethod;
import lombok.Builder;

@Builder
public class GetFlightsByToken extends AbstractHttpSpecification {

    private String token;

    @Override
    protected HttpMethod getHttpMethod() {
        return HttpMethod.GET;
    }

    @Override
    protected String getEndpointUrl() {
        return "https://flights.booking.com/api/flight/" + token;
    }
}
