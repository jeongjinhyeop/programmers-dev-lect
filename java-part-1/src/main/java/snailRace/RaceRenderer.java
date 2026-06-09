package snailRace;

import java.util.List;

public class RaceRenderer extends Thread {
    private List<Snail> snailList;
    private int totalCount;
    private int snailSpeed;

    public RaceRenderer(List<Snail> snailList, int totalCount, int snailSpeed) {
        this.snailList = snailList;
        this.totalCount = totalCount;
        this.snailSpeed = snailSpeed;
    }

    @Override
    public void run() {
        boolean gameRunning = true;
        while (gameRunning) {

            System.out.println("====== LIVE 달팽이 레이스 ======");
            System.out.println("---------------------------------------");

            int finishCount = 0;
            for (Snail snail : snailList) {
                StringBuilder bar = new StringBuilder();
                int pos = snail.getPosition();
                int maxDistance = snail.getFinishDistance();
                if (pos > maxDistance) pos = maxDistance;

                for (int j = 0; j < pos; j++) bar.append("=");
                bar.append(">");
                for (int j = pos; j < maxDistance; j++) bar.append(" ");
                bar.append("| FINISH");

                System.out.println(snail.getSnailName() + ": " + bar);

                if (snail.getPosition() >= maxDistance) {
                    finishCount++;
                }
            }
            System.out.println("---------------------------------------\n");

            if (finishCount == totalCount) {
                gameRunning = false; // 모든 달팽이가 들어오면 루프 종료 -> run() 종료 -> 스레드 사망
            }

            try {
                Thread.sleep((int)(snailSpeed * 0.8));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}