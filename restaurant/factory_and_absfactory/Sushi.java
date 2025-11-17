package restaurant.factory_and_absfactory;

public class Sushi implements Dish {
    private final String type;
    public Sushi(String type) { this.type = type; }
    @Override
    public String getDescription() { return "Sushi " + type; }
    @Override
    public double getCost() { return 2000.0; }
}
