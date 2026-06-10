package rpgGame;

public abstract class Character {
    private String name;
    private int hp;
    private int maxHp;
    private int power;
    private int defense;

    public Character(String name, int hp, int power, int defense) {
        this.name = name;
        this.hp = hp;
        maxHp = hp;
        this.power = power;
        this.defense = defense;
    }

    public Character(String name) {
        this(name, 30, 5, 0);   // 같은 클래스의 다른 생성자 호출 (생성자 체이닝)
    }

    public void showStatus() {
        System.out.println(name + " (HP: " + hp + ")");
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public void takeDamage(int dmg) {
        int actualDamage = dmg - this.defense;
        if (actualDamage <= 0) actualDamage = 0;
        hp -= actualDamage;
        if (hp < 0) hp = 0;
    }

    //메서드 오버라이딩 강제하
    public abstract void attack(Character target);

    public void heal(int amount){
        //정확한 체력 회복 안내를 위한 회복량 계산
        amount = this.hp + amount > maxHp ? maxHp - this.hp  : amount;
        this.hp += amount;

        if(this.hp > maxHp) this.hp = maxHp;
        System.out.println(name + "이(가) 체력을 " + amount + "만큼 회복했습니다!");
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public int getPower() {
        return power;
    }

    public abstract void takeDamage();
}
