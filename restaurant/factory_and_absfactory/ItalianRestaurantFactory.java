package restaurant.factory_and_absfactory;

public class ItalianRestaurantFactory implements RestaurantFactory {
    @Override
    public Dish createDefaultMain() {
        return new Pizza("Margarita");
    }
    @Override
    public Dish createDrink() {
        return new Wine("Red wine");
    }
    @Override
    public Dish createDessert() {
        return new Tiramisu();
    }
}
