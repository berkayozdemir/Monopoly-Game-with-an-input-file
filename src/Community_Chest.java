import java.util.ArrayList;

public class Community_Chest extends Card{
    private int number;
    ArrayList<String> communityList;


    public Community_Chest(String name, int id) {
        super(name, id);
        communityList=new ArrayList<String>();
        setClass("community");
    }

    public int getNumber() {
        return number;
    }

    public void moveNumber() {
        number++;
        if(number==11) {number=0;}
    }
}
