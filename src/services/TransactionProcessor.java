package services;

import configs.PathConfigs;
import configs.RegexConfigs;
import customException.IncorrectEntryException;
import customException.UnknownNumberException;
import customException.WrongAmountException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TransactionProcessor {
    private final TransferDataReader transferDataReader = new TransferDataReader();
    private final TransferResultsWriter transferResultsWriter = new TransferResultsWriter();

    public void processTransactions() {
        File inputPath = new File(PathConfigs.FOLDER_PATH_INPUT);
        File[] files = inputPath.listFiles();
        Map<String, Integer> activeInfo = transferDataReader.getActualInformation();
        for (File file : files) {
            processFile(file, activeInfo);
        }
        transferResultsWriter.writeActivInfo(activeInfo);
    }

    private void processFile(File file, Map<String, Integer> activeInfo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                processLine(line, activeInfo, file.getName());
            }
            Path destinationPath = Paths.get(PathConfigs.ARCHIVE_FOLDER_PATH, file.getName());
            Files.copy(file.toPath(), destinationPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Files.delete(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void processLine(String line, Map<String, Integer> activeInfo, String fileName) {
        Matcher matcher = Pattern.compile(RegexConfigs.CORRECT_INFORMATION_REGEX).matcher(line);
        try {
            if (matcher.find()) {
                processTransaction(matcher.group(), activeInfo, fileName);
            } else {
                throw new IncorrectEntryException();
            }
        } catch (IncorrectEntryException | WrongAmountException | UnknownNumberException exception) {
            transferResultsWriter.writeReport(line, exception.getMessage(), fileName);
        }
    }

    private void processTransaction(String transactionData, Map<String, Integer> activeInfo, String fileName) throws WrongAmountException, UnknownNumberException {
        Matcher matcherNumber = Pattern.compile(RegexConfigs.NUMBER_REGEX).matcher(transactionData);
        Matcher matcherCount = Pattern.compile(RegexConfigs.COUNT_REGEX).matcher(transactionData);
        matcherNumber.find();
        matcherCount.find();
        String senderNumber = matcherNumber.group();
        matcherNumber.find();
        String recipientNumber = matcherNumber.group();
        int transferAmount = Integer.parseInt(matcherCount.group());
        if(!(activeInfo.containsKey(senderNumber)&&activeInfo.containsKey(recipientNumber))){
            throw new UnknownNumberException();
        }
        if ((transferAmount < 0) || (activeInfo.get(senderNumber) < transferAmount)) {
            throw new WrongAmountException();
        }
        activeInfo.replace(senderNumber, activeInfo.get(senderNumber) - transferAmount);
        activeInfo.replace(recipientNumber, activeInfo.get(recipientNumber) + transferAmount);
        transferResultsWriter.writeReport(senderNumber, recipientNumber, transferAmount, "успешно обработан", fileName);
    }
}
