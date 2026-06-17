package semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Dungeon {
    private final Semaphore slots;
    private final int capacity;

    public Dungeon(int capacity){
        this.capacity = capacity;
        //동시에 capa만큼 사용 가능
        this.slots = new Semaphore(capacity,true);
    }

    public void enter(String name) throws InterruptedException {
//        slots.acquire();                 // 빈자리 1개 차지 (없으면 대기)
        if(slots.tryAcquire(5, TimeUnit.SECONDS)) {
            System.out.println(name + " 던전 입장 대기...");
            try {
                System.out.println("[입장] " + name
                        + " (남은 자리: " + slots.availablePermits() + "/" + capacity + ")");

                int playTime =(int) (Math.random() * 2000) + 1000;  // 1~3초 탐험
                int gold = (int) (Math.random() * 400) + 100;       // 100~500 골드
                System.out.println("[클리어] " + name + " → " + gold + " 골드 획득");

                int bossChance = (int) (Math.random() * 5) + 1;
                if (bossChance == 1) {
                    System.out.println("[이벤트] " + name + "가 보스를 조우했습니다!");
                    playTime *= 2;
                    gold *= 2;

                    Thread.sleep(playTime);

                    System.out.println("[클리어] " + name + " → " + gold + " 골드 획득");
                }
            } finally {
                System.out.println("[퇴장] " + name);
                slots.release();             // 자리 반납 (예외가 나도 반드시!)
            }
        } else {
            // 5초 동안 기다렸지만 결국 자리가 안 난 경우
            System.out.println("[입장 실패] " + name + " -> 5초 동안 기다렸지만 자리가 없어서 돌아갑니다.");
        }
    }
}
