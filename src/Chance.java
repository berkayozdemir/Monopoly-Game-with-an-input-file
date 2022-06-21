import java.util.ArrayList;

public class Chance extends Card{

   private int number;
    ArrayList<String> chanceList;
    public Chance(String name, int id) {
        super(name, id);
        chanceList=new ArrayList<String>();
        _class="chance";
    }

    public int getNumber() {
        return number;
    }

    public void moveNumber() {
        number++;
        if(number==6) {number=0;}
    }
}
