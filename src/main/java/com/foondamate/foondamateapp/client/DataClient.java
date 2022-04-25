package com.foondamate.foondamateapp.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@FeignClient(name = "data-service", url = "${data.service.url}")
public interface DataClient {

    @GetMapping("")
    Map<String, Integer> fetchApiData();
}

