package customException;

public class WrongAmountException extends Exception{
    String message = "Неправильная сумма перевода\n";

    @Override
    public String getMessage() {
        return message;
    }
}
