package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.AbstractBaseEntity;
import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class SecurityUtil {
    private static int id = 1;
//    private static int id = AbstractBaseEntity.START_SEQ;

    public static int authUserId() {
        return id;
    }

}