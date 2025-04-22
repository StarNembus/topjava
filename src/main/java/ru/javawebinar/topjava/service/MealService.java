package ru.javawebinar.topjava.service;


import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.MealServlet;

import java.util.List;
import java.util.OptionalInt;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;

@Service
public class MealService {
    private final MealRepository mealRepository;

    private static final Logger log = getLogger(MealService.class);

    public MealService(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    public Meal create(Meal meal, int userId) {
        return mealRepository.save(meal, userId);
    }

    public void delete(int id, int userId) {
        checkNotFound(mealRepository.delete(id, userId), id);
    }

    public Meal get(int id, int userId) {
         return checkNotFound(mealRepository.get(id, userId), userId);
    }

    public List<Meal> getAll(int userId) {
        return mealRepository.getAll(userId);
    }

    public void update(Meal meal, int userId) {
        checkNotFound(mealRepository.save(meal, userId), meal.getId());
    }
}