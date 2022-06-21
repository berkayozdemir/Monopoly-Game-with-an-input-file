public class Company extends Property{


    public Company(String name, int id, int cost,String type) {
        super(name, id, cost,type);

        setClass("company");
    }
}
