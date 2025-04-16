package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.springframework.util.StringUtils;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import ru.javawebinar.topjava.repository.inmemory.InMemoryMealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final int CALORIES_PER_DAY = 2000;
    private static final int DEFAULT_CALORIES = 0;
    private MealRepository mealRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        mealRepository = new InMemoryMealRepository();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        log.info("getAll");
        if(action == null) {
            request.setAttribute("meals", MealsUtil.getExceeded(mealRepository.getAll(SecurityUtil.authUserId()), CALORIES_PER_DAY));
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        } else if (action.equals("delete")) {
            int id = getId(request);
            log.info("delete {}", id);
            mealRepository.delete(id, SecurityUtil.authUserId());
            response.sendRedirect("meals");
        } else {
            log.info("begin action create meal");
            final Meal meal = action.equals("create") ? new Meal(LocalDateTime.now(), "", DEFAULT_CALORIES) :
                    mealRepository.get(getId(request), SecurityUtil.authUserId());
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("/mealsEdit.jsp").forward(request,response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        mealRepository.get(getId(request), SecurityUtil.authUserId());
        log.info(meal.isNew() ? "create {}" : "update {}", meal);
        mealRepository.save(meal, SecurityUtil.authUserId());
        response.sendRedirect("meals");
    }

    private int getId(HttpServletRequest request) {
        String id = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(id);
    }


}
