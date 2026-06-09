package snailRace;

public class Race {
    private String winnerName = null;
    private int winnerNum = 0;
    private int currentRank = 1;

    public synchronized void finish(Snail snail){
        if(this.winnerName == null){
            this.winnerName = snail.getSnailName();
            this.winnerNum = snail.getSnailNum();
        }
        snail.setRank(currentRank++);
    }

    public int getWinnerNum() {
        return winnerNum;
    }
}
