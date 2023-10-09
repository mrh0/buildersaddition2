package github.mrh0.buildersaddition2;

public class Utils {
    public static String capitalizeWords(String input) {
        // Split the input string into an array of words using underscores as the delimiter
        String[] words = input.split("_");
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            if (word.length() > 0) {
                // Capitalize the first letter of each word
                String capitalizedWord = word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
                result.append(capitalizedWord).append(" ");
            }
        }

        // Remove the trailing space and return the result
        return result.toString().trim();
    }
}
