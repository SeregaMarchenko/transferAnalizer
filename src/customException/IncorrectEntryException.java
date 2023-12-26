package customException;

public class IncorrectEntryException extends Exception {
    String message = "Некорректная для чтения информация\n";

    @Override
    public String getMessage() {
        return message;
    }
}
