package abstractMember;

public interface Member extends Comparable<Member> {

    String getName();
    String getEmail();
    String getPhone();

    String getGrade();
    String getBenefit();
    int getMonthlyFee();

    default void printInfo() {
        System.out.println("[" + getGrade() + "] " + getName() + " / " + getEmail()
                + " / " + getPhone() + " (혜택: " + getBenefit() + ")");
    }

    void update(String name, String email, String phone);

    default String toFileString() {
        return String.format("등급:%s,이름:%s,이메:%s,연락처:%s\n", getGrade(), getName(), getEmail(), getPhone());
    }

    @Override
    default int compareTo(Member o) {
        return this.getName().compareTo(o.getName());
    }
}
