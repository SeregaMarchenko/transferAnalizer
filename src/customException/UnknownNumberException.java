package customException;

public class UnknownNumberException extends Exception{
    String message = "Неизвестный номер\n";

    @Override
    public String getMessage() {
        return message;
    }
}
