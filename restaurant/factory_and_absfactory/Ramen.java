package restaurant.factory_and_absfactory;

public class Ramen implements Dish {
    private final String type;
    public Ramen(String type) { this.type = type; }
    @Override
    public String getDescription() { return "Ramen " + type; }
    @Override
    public double getCost() { return 1400.0; }
}
