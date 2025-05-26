package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;

import static java.time.Month.JANUARY;
import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration(locations = {"classpath:spring/spring-app.xml", "classpath:spring/spring-db.xml"})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    private MealService mealService;

    @Test
    public void deleteMeal() {
        mealService.delete(MEAL1_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> mealService.get(MEAL1_ID, USER_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> mealService.delete(MealTestData.NOT_FOUND, USER_ID));
    }

    @Test
    public void deleteNotOwn() {
        assertThrows(NotFoundException.class, () -> mealService.delete(MEAL1_ID, ADMIN_ID));
    }

    @Test
    public void getMeal() {
        Meal meal = mealService.get(MEAL1_ID, USER_ID);
        assertMatch(meal, meal1);
    }

    @Test
    public void getMealNotFound() {
        assertThrows(NotFoundException.class, () -> mealService.get(MealTestData.NOT_FOUND, USER_ID));
    }

    @Test
    public void getMealNotOwn() {
        assertThrows(NotFoundException.class, () -> mealService.get(MEAL1_ID, ADMIN_ID));
    }

    @Test
    public void getAllMeals() {
        assertMatch(mealService.getAll(USER_ID), meals);
    }

    @Test
    public void updateMeal() {
        Meal updatedMeal = getUpdatedMeal();
        mealService.update(updatedMeal, USER_ID);
        assertMatch(mealService.get(MEAL1_ID, USER_ID), updatedMeal);
    }
    @Test
    public void updateNotOwn() {
        assertThrows(NotFoundException.class, () -> mealService.update(meal1, ADMIN_ID));
        assertMatch(mealService.get(MEAL1_ID, USER_ID), meal1);
    }

    @Test
    public void createMeal() {
        Meal createdMeal = mealService.create(getNew(), USER_ID);
        int newId = createdMeal.getId();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        assertMatch(createdMeal, newMeal);
        assertMatch(mealService.get(newId, USER_ID), newMeal);
    }

    @Test
    public void getBetweenInclusive() {
        assertMatch(mealService.getBetweenInclusive(LocalDate.of(2020, JANUARY, 31),
                LocalDate.of(2020, JANUARY, 31), USER_ID),  meal7, meal6, meal5, meal4);
    }
    @Test
    public void getBetweenNull() {
        assertMatch(mealService.getBetweenInclusive(null, null, USER_ID), meals);
    }


}
