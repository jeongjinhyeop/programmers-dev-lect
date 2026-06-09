package snailRace;

import java.util.Random;
import java.util.stream.DoubleStream;

public class Snail extends Thread{
    private String snailName;
    private int position = 0;
    private final int FINISH = 30;
    private int snailNum = 0;
    private Random rand = new Random();
    private Race race;
    private int rank = 1;
    private final int finishDistance;
    private final int sleepTime;

    public Snail(String snailName, int  snailNum, Race race, int finishDistance, int sleepTime) {
        this.snailName = snailName;
        this.snailNum = snailNum;
        this.race = race;
        this.finishDistance = finishDistance;
        this.sleepTime = sleepTime;
    }

    public String getSnailName() {
        return snailName;
    }

    public int getSnailNum() {
        return snailNum;
    }

    public int getPosition() {
        return position;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getSleepTime() {
        return sleepTime;
    }

    public int getFinishDistance() {
        return finishDistance;
    }

    @Override
    public void run(){
        while (position < finishDistance){
            position += rand.nextInt(3) + 1;
//            printProgress();

            try {
                Thread.sleep(sleepTime);
            }catch (InterruptedException e){
                System.out.println("인터럽트 문제 발생!" + e.getMessage());
            }

            if (position >= finishDistance) {
                System.out.println(snailName + " 결승선 도착!");
                race.finish(this);
                break;
            }
        }
    }
}
