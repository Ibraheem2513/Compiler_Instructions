import java.util.*;
import java.util.regex.*;

public class SimpleLexer {
    // Token types
    public enum TokenType {
        KEYWORD, IDENTIFIER, LITERAL, OPERATOR, PUNCTUATION, COMMENT, WHITESPACE
    }

    // Token class
    public static class Token {
        public final TokenType type;
        public final String value;

        public Token(TokenType type, String value) {
            this.type = type;
            this.value = value;
        }

        @Override
        public String toString() {
            return String.format("Token(%s, %s)", type, value);
        }
    }

    // List of Java keywords
    private static final Set<String> KEYWORDS = Set.of(
            "public", "class", "void", "static", "int", "String", "System", "out", "println"
    );

    // Regular expressions for token patterns
    private static final Pattern TOKEN_PATTERNS = Pattern.compile(
            "(?<KEYWORD>\\b(public|class|void|static|int|String|System|out|println)\\b)" + // Keywords
                    "|(?<IDENTIFIER>\\b[a-zA-Z_][a-zA-Z0-9_]*\\b)" + // Identifiers
                    "|(?<LITERAL>\"[^\"]*\"|\\d+)" + // Literals (strings and numbers)
                    "|(?<OPERATOR>[=+\\-*/])" + // Operators
                    "|(?<PUNCTUATION>[{}();,.])" + // Punctuation
                    "|(?<COMMENT>//.*|/\\*[^*]*(?:\\*(?!/)[^*]*)*\\*/)" + // Comments
                    "|(?<WHITESPACE>\\s+)" // Whitespace
    );

    // Lexer method
    public static List<Token> lex(String input) {
        List<Token> tokens = new ArrayList<>();
        Matcher matcher = TOKEN_PATTERNS.matcher(input);

        while (matcher.find()) {
            if (matcher.group("KEYWORD") != null) {
                tokens.add(new Token(TokenType.KEYWORD, matcher.group("KEYWORD")));
            } else if (matcher.group("IDENTIFIER") != null) {
                tokens.add(new Token(TokenType.IDENTIFIER, matcher.group("IDENTIFIER")));
            } else if (matcher.group("LITERAL") != null) {
                tokens.add(new Token(TokenType.LITERAL, matcher.group("LITERAL")));
            } else if (matcher.group("OPERATOR") != null) {
                tokens.add(new Token(TokenType.OPERATOR, matcher.group("OPERATOR")));
            } else if (matcher.group("PUNCTUATION") != null) {
                tokens.add(new Token(TokenType.PUNCTUATION, matcher.group("PUNCTUATION")));
            } else if (matcher.group("COMMENT") != null) {
                tokens.add(new Token(TokenType.COMMENT, matcher.group("COMMENT")));
            } else if (matcher.group("WHITESPACE") != null) {
                // Skip whitespace
            } else {
                throw new RuntimeException("Unexpected token: " + matcher.group());
            }
        }

        return tokens;
    }

    // Method to remove comments and whitespace
    public static String removeCommentsAndWhitespace(String input) {
        // Remove comments
        input = input.replaceAll("//.*|/\\*[^*]*(?:\\*(?!/)[^*]*)*\\*/", "");
        // Remove whitespace
        input = input.replaceAll("\\s+", "");
        return input;
    }

    // Main method for testing
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the code to be lexed (press Enter twice to finish):");
        StringBuilder inputBuilder = new StringBuilder();
        String line;
        while (true) {
            line = scanner.nextLine();
            if (line.isEmpty()) {
                break;
            }
            inputBuilder.append(line).append("\n");
        }
        String input = inputBuilder.toString();

        // Print original code
        System.out.println("\nOriginal Code:");
        System.out.println(input);

        // Remove comments and whitespace
        String cleanedCode = removeCommentsAndWhitespace(input);
        System.out.println("\nCode after removing comments and whitespace:");
        System.out.println(cleanedCode);

        // Lex the input
        List<Token> tokens = lex(input);
        System.out.println("\nTokens:");
        for (Token token : tokens) {
            System.out.println(token);
        }

        scanner.close();
    }
}