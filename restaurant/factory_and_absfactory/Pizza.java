package restaurant.factory_and_absfactory;

public class Pizza implements Dish {
    private final String type;
    public Pizza(String type) {
        this.type = type;
    }
    @Override
    public String getDescription() {
        return "Pizza " + type;
    }
    @Override
    public double getCost() {
        // simple pricing by type
        switch (type.toLowerCase()) {
            case "Pepperoni": return 1700.0;
            case "Margarita": return 1500.0;
            case "Four Cheeses": return 1800.0;
            default: return 1500.0;
        }
    }
}
