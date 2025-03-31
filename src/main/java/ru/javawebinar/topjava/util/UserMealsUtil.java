package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static final List<UserMeal> MEAL_LIST = Arrays.asList(
            new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
    );

    public static void main(String[] args) {

        List<UserMealWithExceed> filteredWithExceed = getFilteredWithExceed(MEAL_LIST, LocalTime.of(7, 0),
                LocalTime.of(12, 0), 2000);
        filteredWithExceed.forEach(System.out::println);

        System.out.println(filteredMealsByCycle(MEAL_LIST, LocalTime.of(7, 0),
                LocalTime.of(12, 0), 2000));

}

    public static List<UserMealWithExceed> getFilteredWithExceed(List<UserMeal> meals, LocalTime startTime,
                                                                 LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(UserMeal::getDate, Collectors.summingInt(UserMeal::getCalories))
                );

        return meals.stream()
                .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime))
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static UserMealWithExceed createTo(UserMeal meal, boolean excess) {
        return new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }

    public static List<UserMealWithExceed> getExceeded(List<UserMeal> meals, int caloriesPerDay) {
        return getFilteredWithExceed(meals, LocalTime.MIN, LocalTime.MAX, caloriesPerDay);
    }

    public static List<UserMealWithExceed> filteredMealsByCycle(List<UserMeal> meals, LocalTime startTime,
                                                                LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> sumCaloriesAllDay = new HashMap<>();
        for (UserMeal meal : meals) {
            LocalDate date = meal.getDateTime().toLocalDate();
            sumCaloriesAllDay.merge(date, meal.getCalories(), Integer::sum);
        }
        List<UserMealWithExceed> userMealWithExcesses = new ArrayList<>();
        for (UserMeal meal : meals) {
            if (meal.getDateTime().getHour() > startTime.getHour() && meal.getDateTime().getHour() < endTime.getHour()) {
                LocalTime mealTime = meal.getDateTime().toLocalTime();
                if (TimeUtil.isBetweenHalfOpen(mealTime, startTime, endTime)) {
                    LocalDate mealDate = meal.getDateTime().toLocalDate();
                    UserMealWithExceed mealWithExceed = new UserMealWithExceed(meal.getDateTime(), meal.getDescription(),
                            meal.getCalories(), sumCaloriesAllDay.get(mealDate) > caloriesPerDay);
                    userMealWithExcesses.add(mealWithExceed);
                }

            }
        }
        return userMealWithExcesses;
    }


}