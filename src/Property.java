public class Property extends Card{
    private String name;
    private int id;
    private int cost;
    public int owner=0;
    public String upperType="property";
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Property(String name, int id, int cost, String type) {
        super(name, id);
        setCost(cost);
        setType(type);
    }


    public void setOwner(int owner) {
        this.owner=owner;
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }


}
