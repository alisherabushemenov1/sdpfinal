package restaurant.factory_and_absfactory;

public class Pasta implements Dish {
    private final String type;
    public Pasta(String type) { this.type = type; }
    @Override
    public String getDescription() { return "Pasta " + type; }
    @Override
    public double getCost() { return 1200.0; }
}
