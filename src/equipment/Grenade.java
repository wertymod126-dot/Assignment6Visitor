package equipment;

public class Grenade implements Equipment {
    private final String name;
    private final int price;
    private final int damage;

    public Grenade(String name, int price, int damage) {
        this.name = name;
        this.price = price;
        this.damage = damage;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitGrenade(this);
    }

    @Override
    public String getName() {
        return name;
    }
    public int getPrice() {
        return price;
    }
    public int getDamage() {
        return damage;
    }

}
