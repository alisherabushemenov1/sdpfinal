package restaurant.factory_and_absfactory;

public class JapaneseRestaurantFactory implements RestaurantFactory {
    @Override
    public Dish createDefaultMain() {
        return new Sushi("Classic set");
    }
    @Override
    public Dish createDrink() {
        return new Sake("Sake");
    }
    @Override
    public Dish createDessert() {
        return new Mochi();
    }
}
