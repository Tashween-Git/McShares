package com.mcshares.demo.utils;

import com.mcshares.demo.McSharesConstants;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Component
public class CSVUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(CSVUtils.class);

    public File createCSVFile(String fileName, String[] headers, List<String> contents) throws IOException {
        LOGGER.info("START createCSVFile, fileName: {}, headers : {}, contents: {}", fileName, headers, contents);
        File csvFile = new File(fileName);
        FileWriter out = null;
        CSVPrinter printer = null;
        try {
            out = new FileWriter(csvFile);
            printer = new CSVPrinter(out, CSVFormat.DEFAULT
                    .withHeader(headers));
            for (String content : contents) {
                String[] contentSplitted = content.split(McSharesConstants.CUSTOM_DELIMETER);
                printer.printRecord(contentSplitted);
            }
        } catch (Exception e) {
            LOGGER.error("Exception occurred", e);
            csvFile = null;
        } finally {
            if (printer != null)
                printer.close();
            if (out != null)
                out.close();
        }

        LOGGER.info("END createCSVFile, fileName: {}, headers : {}, contents: {}, csvFile: {}", fileName, headers, contents, csvFile != null ? csvFile.getName() : null);
        return csvFile;
    }
}
