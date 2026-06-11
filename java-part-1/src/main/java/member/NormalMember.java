package member;

public class NormalMember extends AbstractMember{

    public NormalMember(String name, String email, String phone) {
        super(name, email, phone);
    }

    @Override
    public String getGrade() {
        return "일반";
    }

    @Override
    public String getDiscountRate() {
        return "기본 서비스";
    }
}
