package com.foondamate.foondamateapp.client;

import com.foondamate.foondamateapp.config.DataMocks;
import com.foondamate.foondamateapp.config.WireMockConfig;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(properties = {
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false",
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false"
})
@ActiveProfiles("test")
@EnableConfigurationProperties
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { WireMockConfig.class })
class DataClientIntegrationTest {

    @Autowired
    private WireMockServer mockDataService;

    @Autowired
    private DataClient dataClient;

    @BeforeEach
    void setUp() throws IOException {
        DataMocks.setupMockDataResponse(mockDataService);
    }

    @Test
    public void whenFetchData_thenDataMapShouldBeReturned() {
        assertFalse(dataClient.fetchApiData().isEmpty());
    }

    @Test
    public void whenFetchData_thenTheCorrectEntriesShouldBeContained() {
        assertTrue(dataClient.fetchApiData().containsKey("01-01-2022"));
        assertTrue(dataClient.fetchApiData().get("01-01-2022").equals(300));
        assertTrue(dataClient.fetchApiData().get("12-01-2022").equals(35000));
        assertTrue(dataClient.fetchApiData().get("04-01-2022").equals(1300));
        assertTrue(dataClient.fetchApiData().get("07-01-2022").equals(3500));
    }
}