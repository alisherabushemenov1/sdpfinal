package restaurant.factory_and_absfactory;

public class Wine implements Dish {
    private final String name;
    public Wine(String name) { this.name = name; }
    @Override
    public String getDescription() { return name + " (Wine)"; }
    @Override
    public double getCost() { return 800.0; }
}
