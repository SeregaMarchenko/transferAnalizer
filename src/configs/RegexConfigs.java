package configs;

public class RegexConfigs {
    public static final String NUMBER_REGEX = "\\d{5}-\\d{5}";
    public static final String COUNT_REGEX = "-?\\d+$";
    public static final String CORRECT_COUNT_REGEX = "[|]\\d+";
    public static final String CORRECT_INFORMATION_REGEX = "\\d{5}-\\d{5}[|]\\d{5}-\\d{5}[|]-?\\d+";
}
