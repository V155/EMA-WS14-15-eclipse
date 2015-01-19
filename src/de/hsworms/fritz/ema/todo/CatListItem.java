package de.hsworms.fritz.ema.todo;

/**
 * Created by fritz on 18/11/14.
 */
public class CatListItem {

    private String name;
    private int numOfItems;

    public CatListItem (String name, int numOfItems){
        this.name = name;
        this.numOfItems = numOfItems;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumOfItems() {
        return numOfItems;
    }

    public void setNumOfItems(int numOfItems) {
        this.numOfItems = numOfItems;
    }

}
