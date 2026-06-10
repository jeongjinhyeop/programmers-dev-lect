package rpgGame;

public class Monster extends Character {
    //기본 몬스터 생성
    public Monster(String name) {
        super(name);
    }

    //특수 몬스터 생성
    public Monster(String name, int hp, int power, int defense) {
        super(name, hp, power, defense);
    }

    @Override
    public void takeDamage(){

    }

    @Override
    public void attack(Character target) {
        if (getName().equals("드래곤")) {
            System.out.println("드래곤이 하늘로 솟구쳐 화염 브레스를 뿜습니다!");
            // 드래곤은 특수하게 공격력의 1.2배 피해
            target.takeDamage((int)(getPower() * 1.2));
        } else if (getName().equals("슬라임")) {
            System.out.println("슬라임이 몸통 박치기를 합니다!");
            target.takeDamage(getPower());
        } else {
            System.out.println(getName() + "이(가) 공격합니다!");
            target.takeDamage(getPower());
        }
    }

}
