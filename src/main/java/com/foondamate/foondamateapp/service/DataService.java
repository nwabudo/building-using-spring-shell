package com.foondamate.foondamateapp.service;

import java.time.LocalDate;
import java.util.Map;

public interface DataService {

    Map<LocalDate, Integer> filterDataByDates(String fromDate, String toDate);
}
