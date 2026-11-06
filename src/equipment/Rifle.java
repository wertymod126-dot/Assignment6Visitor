package equipment;

public class Rifle implements Equipment {
    private final String name;
    private final int price;
    private final int dmgPerBullet;


    public Rifle(String name, int price, int dmgPerBullet) {
        this.name = name;
        this.price = price;
        this.dmgPerBullet = dmgPerBullet;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitRifle(this);
    }

    public int getPrice() {
        return price;
    }
    public int getDmgPerBullet() {
        return dmgPerBullet;
    }
}
