package com.foondamate.foondamateapp.service.impl;

import com.foondamate.foondamateapp.client.DataClient;
import com.foondamate.foondamateapp.service.DataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import static com.foondamate.foondamateapp.utils.Utils.toDate;

@Service
@Slf4j
public class DataServiceImpl implements DataService {

    private final DataClient dataClient;

    public DataServiceImpl(DataClient dataClient) {
        this.dataClient = dataClient;
    }

    @Override
    public Map<LocalDate, Integer> filterDataByDates(String fromDate, String toDate) {
        try{
            Map<String, Integer> clientResponse =  dataClient.fetchApiData();
            LocalDate from = toDate(fromDate), to = toDate(toDate);

            if(from.isAfter(to)) return Collections.emptyMap();//TODO: throw exception at this point with reasons

            Map<LocalDate, Integer> map = new TreeMap<>();
            for (Map.Entry<String, Integer> entry : clientResponse.entrySet()) {
                LocalDate date = toDate(entry.getKey());
                if((date.isEqual(from) || date.isAfter(from)) && (date.isEqual(to) || date.isBefore(to))){
                    map.put(date, entry.getValue());
                }
            }
            return map;
        }catch (Exception ex){
            log.error("Error Occurred: {}", ex.getMessage());
        }
        return Collections.emptyMap();
    }


}
