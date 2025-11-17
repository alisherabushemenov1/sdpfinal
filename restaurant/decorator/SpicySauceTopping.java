package restaurant.decorator;

import restaurant.factory_and_absfactory.Dish;

public class SpicySauceTopping extends DishDecorator {
    public SpicySauceTopping(Dish dish) { super(dish); }
    @Override public String getDescription() { return dish.getDescription() + " + Hot sauce"; }
    @Override public double getCost() { return dish.getCost() + 30.0; }
}
