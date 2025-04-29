package ru.javawebinar.topjava.repository;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface MealRepository {
    Meal save(Meal meal, int userId);

    Meal get(int id, int userId);

    List<Meal> getAll(int userId);

    boolean delete(int id, int userId);
    List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId);
}
