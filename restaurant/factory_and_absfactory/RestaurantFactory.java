package restaurant.factory_and_absfactory;

public interface RestaurantFactory {
    // keep simple factory methods; Main will ask user which concrete to create
    Dish createDefaultMain();
    Dish createDrink();
    Dish createDessert();
}
