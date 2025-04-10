package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.util.CollectionUtils;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> userMealsMap = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);


    @Override
    public Meal save(Meal meal, int userId) {
        Map<Integer, Meal> meals = userMealsMap.computeIfAbsent(userId, ConcurrentHashMap::new);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meals.put(meal.getId(), meal);
            return meal;
        }
        return meals.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> meals = userMealsMap.get(userId);
        if (meals == null) {
            return null;
        }
        return meals.get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        Map<Integer, Meal> meals = userMealsMap.get(userId);
        return CollectionUtils.isEmpty(meals) ? Collections.emptyList() : meals.values().stream()
                .sorted(Comparator.comparing(Meal:: getDateTime).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer, Meal> meals = userMealsMap.get(userId);
        return meals != null && meals.remove(id) != null ;
    }

}
