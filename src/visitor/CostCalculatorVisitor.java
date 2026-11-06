package visitor;

import equipment.Grenade;
import equipment.Rifle;
import equipment.Visitor;

public class CostCalculatorVisitor implements Visitor {
    private int totalCost = 0;
    @Override
    public void visitRifle(Rifle rifle) {
        totalCost += rifle.getPrice();
    }

    @Override
    public void visitGrenade(Grenade grenade) {
        totalCost += grenade.getPrice();
    }

    public int getTotalCost() {
        return totalCost;
    }
}
