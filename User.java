import java.util.ArrayList;
public class User {
    private int money;
    private int turns;
    private ArrayList<Integer> turndata;
    public User() {
        money = 3000;
        turns = 0;
        turndata = new ArrayList<Integer>();
    }
    public User(int money, int turns, ArrayList<Integer> turndata) {
        this.money = money;
        this.turns = turns;
        this.turndata = turndata;
    }
    public int getTurns() {
        return turns;
    }
    public int getMoney() {
        return money;
    }
    public ArrayList<Integer> getTurnData() {
        return turndata;
    }
    public void addMoney(int money) {
        this.money += money;
    }
    public void addTurn() {
        turns++;
    }
    public void addTurnData(int money) {
        turndata.add(money);
    }
}