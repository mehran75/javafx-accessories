
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlDetector {

    private static final String regex =
            "(?:(?:https?|ftp):\\/\\/|\\b(?:[a-z\\d]+\\.))(?:(?:[^\\s()<>]+|\\((?:[^\\s()<>]+|(?:\\([^\\s()<>]+\\)))?\\))+(?:\\((?:[^\\s()<>]+|(?:\\(?:[^\\s()<>]+\\)))?\\)|[^\\s`!()\\[\\]{};:'\".,<>?«»“”‘’]))?";

    public static String detectURL(String rawText) {

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(rawText);

//        StringBuilder builder = new StringBuilder(rawText);

        while (matcher.find()) {
            String m = matcher.group();
//            System.out.println("m = " + m);
//            builder.append('[');
//            builder.insert(rawText.indexOf(m.charAt(0)), '[');
//            builder.insert(rawText.indexOf(m.charAt(m.length() - 1)), ']');
            rawText = rawText.replace(m, '[' + m + ']');
        }
//        System.out.println(rawText);

        return rawText;
    }

    public static String lastUrl(String text) {
        String url = null;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

//        StringBuilder builder = new StringBuilder(rawText);

        while (matcher.find()) {
            url = matcher.group();
        }
        return url;
    }
}
