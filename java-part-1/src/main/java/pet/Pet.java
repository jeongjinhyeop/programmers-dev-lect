package pet;

public class Pet {
    private String name;
    private int fullness;
    private int happiness;
    private String type;

    public Pet(String name, String type) {
        this.name = name;
        fullness = 50;
        happiness = 50;
        this.type = type;
    }

    public void showStatus() {
        System.out.println("[" + name + "] 포만감: " + fullness + " / 행복: " + happiness);
    }

    public void feed(){
        fullness += 20;
        if (fullness > 100) fullness = 100;
        happiness += 5;
        if(happiness > 100) happiness = 100;
        stateOfHappiness(name);
        System.out.println(name + "에게 먹이를 줬어요! 냠냠");
    }

    public void play(){
        happiness += 20;
        if (happiness > 100) happiness = 100;
        fullness -= 10;
        if (fullness < 0) fullness = 0;
        stateOfHappiness(name);
        System.out.println(name + "와(과) 신나게 놀았어요!");
    }

    public void sleep(){
        happiness += 10;
        if (happiness > 100) happiness = 100;
        fullness += 5;
        if (fullness > 100) fullness = 100;
        stateOfHappiness(name);
        System.out.println(name + "은 푹 잤어요!");
    }

    public void stateOfHappiness(String name){
        if(fullness >= 80) System.out.println(name + "의 상태는 행복해요");
        if(fullness >= 50 && fullness < 80) System.out.println(name + "의 상태는 그저그래요");
        if(fullness < 50) System.out.println(name + "의 상태는 시무룩");
    }

    public void defaultCost(String name){
        fullness -= 2;
        System.out.println("시간이 흘러 " + name + "의 포만감이 감소했습니다.");
        showStatus();
    }

    public String getName() {
        return name;
    }

    public int getFullness() {
        return fullness;
    }

    public int getHappiness() {
        return happiness;
    }

    public String getType() {
        return type;
    }
}
