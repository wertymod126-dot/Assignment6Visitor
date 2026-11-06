import equipment.Grenade;
import equipment.Rifle;
import equipment.Visitor;
import visitor.CostCalculatorVisitor;
import visitor.LoadoutReportVisitor;

public class Main {
    public static void main(String[] args) {
        Loadout loadout = new Loadout("Monesy");
        loadout.add(new Rifle("AWP", 4750, 115))
                .add(new Grenade("Flashbang", 200, 1))
                .add(new Grenade("Smoke", 300, 1))
                .add(new Grenade("HE Grenade", 300, 67));

        CostCalculatorVisitor costVisitor = new CostCalculatorVisitor();
        LoadoutReportVisitor reportVisitor = new LoadoutReportVisitor();

        loadout.accept(costVisitor);
        loadout.accept(reportVisitor);
        System.out.println("PLAYER: " + loadout.getPlayerName());
        System.out.print("Total Price: " + costVisitor.getTotalCost());
        System.out.println(reportVisitor.getReport());



    }
}
