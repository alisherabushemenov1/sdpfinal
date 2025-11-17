package restaurant.decorator;

import restaurant.factory_and_absfactory.Dish;

public abstract class DishDecorator implements Dish {
    protected final Dish dish;
    public DishDecorator(Dish dish) { this.dish = dish; }
    @Override public String getDescription() { return dish.getDescription(); }
    @Override public double getCost() { return dish.getCost(); }
}
