package abstractMember;

public class NormalMember implements Member {
    private String name;
    private String email;
    private String phone;

    // 생성자에서 본인의 필드를 직접 초기화합니다. (super 필요 없음)
    public NormalMember(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public void update(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    @Override
    public String getGrade() {
        return "일반";
    }

    @Override
    public String getBenefit() {
        return "기본 서비스";
    }

    @Override
    public int getMonthlyFee() {
        return 9900;
    }


}