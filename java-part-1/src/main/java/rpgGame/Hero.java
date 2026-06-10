package rpgGame;

public class Hero extends Character {

    public Hero(String name) {
        super(name);
    }

    public Hero(String name, int hp, int power, int defense) {
        super(name, hp, power, defense);
    }

    @Override
    public void attack(Character target) {
        System.out.println(getName() + "의 공격! " + target.getName() + "에게 " + getPower() + " 피해");
        target.takeDamage(getPower());
    }

    @Override
    public void takeDamage() {

    }

}
