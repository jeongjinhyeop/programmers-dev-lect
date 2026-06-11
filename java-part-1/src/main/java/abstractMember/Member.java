package abstractMember;

public abstract class Member implements Comparable<Member> {
    protected String name;
    protected String email;
    protected String phone;

    public Member(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public String getName()  { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }

    public abstract String getGrade();
    public abstract String getBenefit();
    public abstract int getMonthlyFee();

    public void printInfo() {
        System.out.println("[" + getGrade() + "] " + name + " / " + email
                + " / " + phone + " (혜택: " + getBenefit() + ")");
    }

    public void update(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public String toFileString() {
        return String.format("등급:%s,이름:%s,이메일:%s,연락처:%s\n", getGrade(), name, email, phone);
    }

    @Override
    public int compareTo(Member o) {
        return this.name.compareTo(o.name);
    }
}
