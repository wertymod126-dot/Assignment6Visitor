package equipment;

public interface Equipment {
    void accept(Visitor visitor);
    String getName();
}
