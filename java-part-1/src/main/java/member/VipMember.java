package member;

public class VipMember extends AbstractMember{
    public VipMember(String name, String email, String phone) {
        super(name, email, phone);
    }

    @Override
    public String getGrade() {
        return "VIP";
    }

    @Override
    public String getDiscountRate() {
        return "10% 할인 + 무료배송";
    }
}
