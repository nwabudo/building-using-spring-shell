package com.foondamate.foondamateapp.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


import java.util.Collections;
import java.util.Map;

@Service
@Slf4j
public class DataClientImpl implements DataClient{

    private final RestTemplate restTemplate;

    private String url;

    @Autowired
    public DataClientImpl(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    @Value("${data.service.url}")
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public Map<String, Integer> fetchApiData() {
        try {
            String json = restTemplate.getForObject(url, String.class);
            Map<String, Integer> map
                    = new ObjectMapper().readValue(json, new TypeReference<>() {
            });
            return map;
        }
        catch (HttpClientErrorException ex) {
            log.error("Error Status Code: {}", ex.getRawStatusCode());
        } catch (JsonMappingException ex) {
            log.error("Error Status Code: {}", ex.getMessage());
        } catch (JsonProcessingException ex) {
            log.error("Error Status Code: {}", ex.getMessage());
        }
        return Collections.emptyMap();
    }
}
