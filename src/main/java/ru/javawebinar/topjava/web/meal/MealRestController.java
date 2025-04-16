package ru.javawebinar.topjava.web.meal;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.util.List;

public class MealRestController {
    private MealService mealService;
    public List<Meal> getAll(int userId) {
        return mealService.getAll(userId);
    }

    public Meal get(int id, int userId) {
        return mealService.get(id, userId);
    }

    public Meal create(Meal meal, int userId) {
        return mealService.create(meal, userId);
    }

    public void update(Meal meal, int userId) {
        mealService.update(meal, userId);
    }

    public void delete(int id, int userId) {
        mealService.delete(id, userId);
    }

}