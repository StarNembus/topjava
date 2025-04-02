<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import ="ru.javawebinar.topjava.util.TimeUtil"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<html lang="ru">
<head>
    <title>Meals</title>

</head>
<body>
<section>
<h3><a href="">Home</a></h3>
<h3>Edit</a></h3>

    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <form action="meals" method="post">
        <input type="hidden" name="id" value="${meal.id}"/><br/>
        <dl>
            <dt>DateTime:</dt>
            <dd><input type="datetime-local" name="dateTime" value="${meal.dateTime}"></dd>
        </dl>
        <dl>
             <dt>Description:</dt>
             <dd><input type="text" name="description" size=50 value="${meal.description}"></dd>
        </dl>
        <dl>
              <dt>Calories:</dt>
              <dd><input type="number" name="calories" value="${meal.calories}"></dd>
        </dl>
        <button type="submit">Add</button>
        <button onclick="window.history.back()">Cancel</button>
    </form>
<section>
</body>
</html>