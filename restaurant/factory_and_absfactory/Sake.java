package restaurant.factory_and_absfactory;

public class Sake implements Dish {
    private final String name;
    public Sake(String name) { this.name = name; }
    @Override
    public String getDescription() { return name + " (Sake)"; }
    @Override
    public double getCost() { return 900.0; }
}
