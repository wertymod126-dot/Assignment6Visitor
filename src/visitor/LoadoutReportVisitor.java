package visitor;

import equipment.Grenade;
import equipment.Rifle;
import equipment.Visitor;


public class LoadoutReportVisitor implements Visitor {
    private final StringBuilder report = new StringBuilder("\n----Loadout Report----");

    @Override
    public void visitRifle(Rifle rifle) {
        report.append("\n Rifle - Name: ").append(rifle.getName())
                .append(" - Price: ").append(rifle.getPrice())
                .append(" - DMG per bullet: ").append(rifle.getDmgPerBullet());
    }

    @Override
    public void visitGrenade(Grenade grenade) {
        report.append("\n Grenade - Name: ").append(grenade.getName())
                .append(" - Price: ").append(grenade.getPrice())
                .append(" - DMG: ").append(grenade.getDamage());
    }

    public String getReport() {
        return report.toString();
    }
}
