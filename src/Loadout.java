import equipment.Equipment;
import equipment.Visitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Loadout {
    private final String playerName;
    private final List<Equipment> items = new ArrayList<>();

    public Loadout(String playerName) {
        this.playerName = playerName;
    }

    public Loadout add(Equipment item) {
        items.add(item);
        return this;
    }

    public void accept(Visitor visitor) {
        for (Equipment item : items) {
            item.accept(visitor);
        }
    }

    public String getPlayerName() {
        return playerName;
    }

    public List<Equipment> getItems() {
        return Collections.unmodifiableList(items);
    }

}
