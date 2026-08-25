package com.template.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataService {

    private static final DateTimeFormatter formatoBr = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static String formatarParaBR(LocalDate data) {
        return data.format(formatoBr);
    }

    public static LocalDate parseDaString(String dataString) {
        return LocalDate.parse(dataString.trim(), formatoBr);
    }
}
