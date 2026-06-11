package abstractMember;

public class VipMember extends Member {
    public VipMember(String name, String email, String phone) {
        super(name, email, phone); // 부모 생성자 호출
    }

    @Override
    public String getGrade()   { return "VIP"; }

    @Override
    public String getBenefit() { return "10% 할인 + 무료배송"; }

    @Override
    public int getMonthlyFee() {
        return 19900;
    }
}
