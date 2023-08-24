package com.geek.request;

import com.geek.AbstractHttpSpecification;
import com.mashape.unirest.http.HttpMethod;
import lombok.Builder;
import lombok.ToString;

@Builder
@ToString
public class GetFlights extends AbstractHttpSpecification {

    private String type;
    private String cabinClass;
    private String sort;
    private String adults;
    private String from;
    private String to;
    private String fromCountry;
    private String toCountry;
    private String stops;
    private String depart;

    @Override
    protected HttpMethod getHttpMethod() {
        return HttpMethod.GET;
    }

    @Override
    protected String getEndpointUrl() {
        StringBuilder sb = new StringBuilder("https://flights.booking.com/api/flights/")
                .append("?")
                .append("type=").append(type).append("&")
                .append("cabinClass=").append(cabinClass).append("&")
                .append("sort=").append(sort).append("&")
                .append("adults=").append(adults).append("&")
                .append("from=").append(from).append("&")
                .append("to=").append(to).append("&")
                .append("fromCountry=").append(fromCountry).append("&")
                .append("toCountry=").append(toCountry).append("&")
                .append("stops=").append(stops).append("&")
                .append("depart=").append(depart);

        return sb.toString();
    }
}
