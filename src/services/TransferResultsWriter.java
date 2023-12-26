package services;

import configs.PathConfigs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

public class TransferResultsWriter {
    public void writeActivInfo(Map<String, Integer> infoActivMap) {
        File fileInfo = new File(PathConfigs.INFO_PATH);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileInfo))) {
            for (Map.Entry<String, Integer> entry : infoActivMap.entrySet()) {
                writer.write(entry.getKey() + "|" + entry.getValue() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeReport(String senderNumber, String recipientNumber, int amount, String message, String fileName) {
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        File report = new File(PathConfigs.REPORT_PATH);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(report, true))) {
            writer.write("Дата " + currentDate + " Время " + currentTime +
                    "|" + fileName + "|" + "перевод с " + senderNumber +
                    " на " + recipientNumber + " " + amount + "|" + message + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeReport(String transferInfoLine, String errorMessage, String fileName) {
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        File report = new File(PathConfigs.REPORT_PATH);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(report, true))) {
            writer.write("Дата " + currentDate + " Время " + currentTime +
                    "|" + fileName + "|" + transferInfoLine + "|" + errorMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
