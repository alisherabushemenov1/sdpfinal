package restaurant.factory_and_absfactory;

public class Tiramisu implements Dish {
    @Override
    public String getDescription() { return "Tiramisu"; }
    @Override
    public double getCost() { return 600.0; }
}
