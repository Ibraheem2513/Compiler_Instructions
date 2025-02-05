import java.util.Scanner;

public class COMPILER {
    public static String removeComments(String code) {
        return code.replaceAll("//.*|/\\*[^*]*(?:\\*(?!/)[^*]*)*\\*/", "");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the code (end input with an empty line):");
        StringBuilder code = new StringBuilder();
        String line;
        while (!(line = scanner.nextLine()).isEmpty()) {
            code.append(line).append("\n");
        }
        scanner.close();

        String result = removeComments(code.toString());
        System.out.println("Code without comments:\n" + result);
    }
}
