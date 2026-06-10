package rpgGame;

public class Character {
    private String name;
    private int hp;
    private int power;

    public Character(String name, int hp, int power) {
        this.name = name;
        this.hp = hp;
        this.power = power;
    }

    public Character(String name) {
        this(name, 30, 5);   // 같은 클래스의 다른 생성자 호출 (생성자 체이닝)
    }

    public void showStatus() {
        System.out.println(name + " (HP: " + hp + ")");
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public void takeDamage(int dmg) {
        hp -= dmg;
        if (hp < 0) hp = 0;
    }

    public void attack(Character target) {
        System.out.println(name + "의 공격! " + target.name + "에게 " + power + " 피해");
        target.takeDamage(power);
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
}
