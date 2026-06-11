package abstractMember;

public class NormalMember extends Member {
    public NormalMember(String name, String email, String phone) {
        super(name, email, phone); // 부모 생성자 호출
    }

    @Override public String getGrade()   { return "일반"; }
    @Override public String getBenefit() { return "기본 서비스"; }
    @Override public int getMonthlyFee() { return 9900; }
}
