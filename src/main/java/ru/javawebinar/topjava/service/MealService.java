package ru.javawebinar.topjava.service;


import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;

@Service
public class MealService {
    private final MealRepository mealRepository;

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
        return mealRepository.get(id, userId);
    }

    public List<Meal> getAll(int userId) {
        return mealRepository.getAll(userId);
    }

    public void update(Meal meal, int userId) {
        mealRepository.save(meal, userId);
    }
    

}