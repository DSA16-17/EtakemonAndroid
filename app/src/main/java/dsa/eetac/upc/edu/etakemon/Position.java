package dsa.eetac.upc.edu.etakemon;

/**
 * Created by Alberto on 28/12/2016.
 */

public class Position {
    public float xposition;
    public float yposition;
    public int id;
    public String name;

    public Position(String name, int id, float yposition, float xposition) {
        this.name = name;
        this.id = id;
        this.yposition = yposition;
        this.xposition = xposition;
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

    public float getYposition() {
        return yposition;
    }

    public void setYposition(float yposition) {
        this.yposition = yposition;
    }

    public float getXposition() {
        return xposition;
    }

    public void setXposition(float xposition) {
        this.xposition = xposition;
    }
}
