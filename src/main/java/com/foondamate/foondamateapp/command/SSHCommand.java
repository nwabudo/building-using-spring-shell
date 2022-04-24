package com.foondamate.foondamateapp.command;

import com.foondamate.foondamateapp.helpers.LocalDateFormatter;
import com.foondamate.foondamateapp.helpers.ShellHelper;
import com.foondamate.foondamateapp.service.DataService;
import com.foondamate.foondamateapp.validator.DateValidator;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.shell.table.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.util.Map;

@ShellComponent
public class SSHCommand {

    private final ShellHelper shellHelper;
    private final DataService dataService;

    public SSHCommand(ShellHelper shellHelper, DataService dataService) {
        this.shellHelper = shellHelper;
        this.dataService = dataService;
    }

    @ShellMethod(prefix = "-", value = "connect to remote server")
    public String ssh(@ShellOption(value = "-n") String name) throws UnknownHostException {
        // get system name
        String SystemName = InetAddress.getLocalHost().getHostName();
        return shellHelper.success(String.format("'%s' has Logged on to machine: '%s'", name, SystemName));
    }

    @ShellMethod(prefix = "-", value = "Take in dates as strings and run search with them")
    public void sshShowData(@ShellOption(value = "--fromDate") @DateValidator String fromDate,
                              @ShellOption(value = "--toDate") @DateValidator String toDate) {
        shellHelper.printInfo(String.format("Search for data between '%s' and '%s'", fromDate, toDate));
        Map<LocalDate, Integer> map = dataService.filterDataByDates(fromDate, toDate);

        if(map.isEmpty()) {
            shellHelper.printError("Error Obtaining Data from API");
            return;
        }

        Object[][] data = new Object[map.size() + 1][2];
        data[0][0] = "Date";
        data[0][1] = "Number of Active Users";
        Object[] keys = map.keySet().toArray();
        Object[] values = map.values().toArray();
        for (int i = 1, j = 0; i < data.length; i++, j++) {
            data[i][0] = keys[j];
            data[i][1] = values[j];
        }

        LocalDateFormatter dateFormatter = new LocalDateFormatter("dd-MM-YYYY");
        TableModel model = new ArrayTableModel(data);
        TableBuilder tableBuilder = new TableBuilder(model);
        tableBuilder.on(CellMatchers.ofType(LocalDate.class)).addFormatter(dateFormatter);
        tableBuilder.addInnerBorder(BorderStyle.fancy_light);
        tableBuilder.addHeaderBorder(BorderStyle.fancy_double);
        tableBuilder.on(CellMatchers.column(0)).addSizer(new AbsoluteWidthSizeConstraints(20));
        tableBuilder.on(CellMatchers.column(1)).addSizer(new AbsoluteWidthSizeConstraints(30));
        shellHelper.print(tableBuilder.build().render(80));
    }

}
