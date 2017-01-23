package dsa.eetac.upc.edu.etakemon;


public class Etakemon {
    public String name,description,type;
    public int id,health;

    public Etakemon(String name, String description, String type, int id, int health) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.id = id;
        this.health = health;
    }
    public Etakemon(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
