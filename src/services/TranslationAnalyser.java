package services;

import java.util.Scanner;

public class TranslationAnalyser {
    private static final TransactionProcessor transactionProcessor = new TransactionProcessor();
    private static final TransferDataReader transferDataReader = new TransferDataReader();

    public static void main(String[] args) {
        boolean flag = true;
        while (flag) {
            System.out.println("1.Начать парсинг\n2.Вывод списка файл отчета\n3.Завершить программу");
            Scanner scanner = new Scanner(System.in);
            if (scanner.hasNextInt()) {
                switch (scanner.nextInt()) {
                    case 1 -> transactionProcessor.processTransactions();
                    case 2 -> System.out.println(transferDataReader.getReportInfo());
                    case 3 -> flag = false;
                    default -> System.out.println("Введите корректное число");
                }
            } else {
                System.out.println("Введите корректное число");
            }
        }
    }
}