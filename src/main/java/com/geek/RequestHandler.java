package com.geek;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.HttpRequestWithBody;


public class RequestHandler {

    public static HttpResponse sendRequest(AbstractHttpSpecification request) {

        HttpRequest r = new HttpRequestWithBody(request.getHttpMethod(), request.getEndpointUrl());
        String reqLog = String.format("\nREQUEST\n-----------\nMETHOD: %s\nURL: %s\nHEADERS: %s\n", new Object[]{request.getHttpMethod(), r.getUrl(), r.getHeaders()});

        ExtentTest reporter = BaseTest.getTestReporter();
        reporter.info(MarkupHelper.createCodeBlock(reqLog));

        HttpResponse response;
        try {
            response = r.asJson();
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }

        String respLog = String.format("\nRESPONSE\n-----------\nSTATUS_CODE: %d\nSTATUS_TEXT: %s\nHEADERS: %s\nBODY: %s\n",response.getStatus(), response.getStatusText(), response.getHeaders(), response.getBody());
        reporter.info(MarkupHelper.createCodeBlock(respLog));
        return response;
    }
}
