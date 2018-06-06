package pull.util;

public class EmailAsPathReady {
    public static String convert(String emailAddress) {
        return emailAddress.replaceAll("\\.", "DOT");
    }
}
