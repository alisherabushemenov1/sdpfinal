package restaurant.factory_and_absfactory;

public class Mochi implements Dish {
    @Override
    public String getDescription() { return "Mochi"; }
    @Override
    public double getCost() { return 500.0; }
}
