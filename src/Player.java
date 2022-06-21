import java.util.ArrayList;

public class Player extends User{
    public boolean onJail=false;
    public int jailCount=0;

    ArrayList<Property> properties=new ArrayList<Property>();
    public int id;
    public Player(String name, int money,int id) {
        super(name, money);
        this.id=id;

    }


}
