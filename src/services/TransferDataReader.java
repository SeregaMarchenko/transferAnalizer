package services;

import configs.PathConfigs;
import configs.RegexConfigs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TransferDataReader {
    public Map<String, Integer> getActualInformation() {
        File fileInfo = new File(PathConfigs.INFO_PATH);
        Map<String, Integer> infoMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileInfo))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Matcher matcherNumber = Pattern.compile(RegexConfigs.NUMBER_REGEX).matcher(line);
                Matcher matcherCount = Pattern.compile(RegexConfigs.CORRECT_COUNT_REGEX).matcher(line);
                if (matcherNumber.find() && matcherCount.find()) {
                    infoMap.put(matcherNumber.group(), Integer.parseInt(matcherCount.group().replace("|", "0")));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return infoMap;
    }

    public String getReportInfo() {
        StringBuilder reportInfoBuilder = new StringBuilder();
        File reportFile = new File(PathConfigs.REPORT_PATH);
        try (BufferedReader reader = new BufferedReader(new FileReader(reportFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                reportInfoBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reportInfoBuilder.toString();
    }
}
