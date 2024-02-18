package com.example.graphbackend.config;

import com.example.graphbackend.model.FeignClientDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;

public class FeignErrorDecoder implements ErrorDecoder {

    private static final Logger logger = LoggerFactory.getLogger(FeignErrorDecoder.class);

    private ErrorDecoder defaultErrorDecoder = new Default();
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Exception decode(String methodKey, Response response) {
        var httpStatus = HttpStatus.valueOf(response.status());
        FeignClientDTO feignClientDTO;
        try {
            feignClientDTO = objectMapper.readValue(response.body().asInputStream(), FeignClientDTO.class);
        } catch (Exception e) {
            logger.debug("Error occurred while parsing response body", e);
            feignClientDTO = new FeignClientDTO(response.toString());
        }
        feignClientDTO.setHttpStatus(httpStatus);


        return defaultErrorDecoder.decode(methodKey, response);
    }
}