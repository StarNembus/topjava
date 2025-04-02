package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;
import static ru.javawebinar.topjava.util.TimeUtil.isBetween;

public class MealsUtil {

    public static List<MealTo> getFilteredWithExceed(Collection<Meal> meals, LocalTime startTime,
                                                     LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
                );

        return meals.stream()
                .filter(meal -> isBetween(meal.getTime(), startTime, endTime))
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }

    public static List<MealTo> getExceeded(Collection<Meal> meals, int caloriesPerDay) {
        return getFilteredWithExceed(meals, LocalTime.MIN, LocalTime.MAX, caloriesPerDay);
    }

}