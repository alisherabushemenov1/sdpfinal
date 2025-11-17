package restaurant.decorator;

import restaurant.factory_and_absfactory.Dish;

public class CheeseTopping extends DishDecorator {
    public CheeseTopping(Dish dish) { super(dish); }
    @Override public String getDescription() { return dish.getDescription() + " + Cheese"; }
    @Override public double getCost() { return dish.getCost() + 50.0; }
}
