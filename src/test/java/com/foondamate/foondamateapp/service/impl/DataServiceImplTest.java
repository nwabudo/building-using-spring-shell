package com.foondamate.foondamateapp.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foondamate.foondamateapp.client.DataClient;
import com.foondamate.foondamateapp.service.DataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.Map;

import static com.foondamate.foondamateapp.utils.Utils.toDate;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class DataServiceImplTest {

    @Mock
    private DataClient dataClient;

    private DataService dataService ;

    @BeforeEach
    public void setup(){
        dataService = new DataServiceImpl(dataClient);
        lenient().when(dataClient.fetchApiData()).thenReturn(clientResponse());
    }

    @Test
    void shouldFetch_MapForSpecifiedDates() {
        Map<LocalDate, Integer> response = dataService.filterDataByDates("01-01-2022", "07-01-2022");
        assertTrue(!response.isEmpty(), "Response should not be Empty");
        assertTrue(response.get(toDate("01-01-2022")).equals(300));//3000
        assertTrue(response.get(toDate("06-01-2022")).equals(3000));
    }

    @Test
    void shouldReturn_NullForDatesOutsideSpecifiedDate() {
        Map<LocalDate, Integer> response = dataService.filterDataByDates("01-02-2022", "07-02-2022");
        assertTrue(response.isEmpty(), "Response should be Empty");
        assertNull(response.get(toDate("16-01-2022")));
    }

    private Map<String, Integer> clientResponse(){
        try {
            // create object mapper instance
            ObjectMapper mapper = new ObjectMapper();
            // convert JSON file to map
            InputStream input = DataServiceImplTest.class.getClassLoader()
                            .getResourceAsStream("payload/data.json");
            return mapper.readValue(input, Map.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}