package memberFinal;

public class CsvUtil {

    public static String escapeCsvField(String field) {
        // 1. 빈 값이나 null이면 그대로 반환
        if (field == null) {
            return "";
        }

        String escaped = field;
        boolean mustQuote = false;

        // 2. 데이터 내부에 큰따옴표(")가 있으면, 쌍따옴표("")로 치환하고 감싸야 함
        if (escaped.contains("\"")) {
            escaped = escaped.replace("\"", "\"\"");
            mustQuote = true;
        }

        // 3. 쉼표(,)나 줄바꿈(\n, \r)이 포함되어 있다면 무조건 감싸야 함
        if (escaped.contains(",") || escaped.contains("\n") || escaped.contains("\r")) {
            mustQuote = true;
        }

        // 4. 이스케이프 대상 문자(쉼표, 따옴표, 줄바꿈)가 있었다면 앞뒤를 큰따옴표로 감싸서 반환
        if (mustQuote) {
            return "\"" + escaped + "\"";
        }

        // 5. 아무것도 해당 안 되는 평범한 문자열은 그대로 반환
        return escaped;
    }

    public static String deEscapeCsvField(String field) {
        if (field == null || field.isEmpty()) {
            return "";
        }

        String unescaped = field.trim();

        // 규칙 1: 만약 앞뒤가 큰따옴표(")로 감싸져 있다면 제거한다.
        if (unescaped.startsWith("\"") && unescaped.endsWith("\"")) {
            unescaped = unescaped.substring(1, unescaped.length() - 1);
        }

        // 규칙 2: 연속된 두 개의 큰따옴표("")는 원래의 한 개(")로 돌려놓는다.
        unescaped = unescaped.replace("\"\"", "\"");

        return unescaped;
    }
}
