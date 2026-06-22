package member;

import java.io.Serializable;

public interface Member extends Serializable {
    String getName();
    String getEmail();
    String getPhone();
    String getGrade();
    String getBenefit();
    void update(String name, String email, String phone);

    default void printInfo() {
        System.out.println("[" + getGrade() + "] " + getName() + " / "
                + getEmail() + " / " + getPhone() + " (혜택: " + getBenefit() + ")");
    }

    default String toFileString() {
        String safeGrade = CsvUtil.escapeCsvField(getGrade());
        String safeName  = CsvUtil.escapeCsvField(getName());
        String safeEmail = CsvUtil.escapeCsvField(getEmail());
        String safePhone = CsvUtil.escapeCsvField(getPhone());
        return String.join(",", safeGrade, safeName, safeEmail, safePhone);
    }
}