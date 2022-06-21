public class Card {
    private String name;
    private int id;
    public String _class;
    public Card(String name,int id) {
        setName(name);
        setId(id);

    }

    public void setClass(String _class) {
        this._class=_class;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
