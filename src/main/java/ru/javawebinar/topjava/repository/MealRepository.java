package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.*;

public interface MealRepository {
    Meal save(Meal meal);
    Meal get(int id);
    void remove(int id);
    Collection<Meal> getAll();


}
