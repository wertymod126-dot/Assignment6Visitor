package equipment;

public interface Visitor {
    void visitRifle(Rifle rifle);
    void visitGrenade(Grenade grenade);
}
