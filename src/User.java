public class User {
    private String name;
    private int money;
    public int location=0;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void payMoney(int pay) {this.money-=pay;}

    public void takeMoney(int take) {this.money+=take;}

    public User(String name, int money) {
        setName(name);
        setMoney(money);
    }

    public void move(int dice) {
        location+=dice;
        if(location>=40) {location-=40; takeMoney(200); Main.banker.payMoney(200);}

    }

}
