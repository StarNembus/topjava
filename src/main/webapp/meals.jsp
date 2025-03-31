<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import ="ru.javawebinar.topjava.util.TimeUtil"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html lang="ru">
<head>
    <title>Meals</title>
    <style>
            .green-row-normal {color:green
                    }
            .red-row-not-normal {
                      color:red
                    }
        </style>
</head>
<body>
<section>
<h3><a href="index.html">Home</a></h3>

<table border="1" cellpadding="8" cellspacing="0">
   <thread>
   <tr>
    <th>Date</th>
    <th>Description</th>
    <th>Calories</th>
    <th></th>
    <th></th>
   </tr>
   </thread>
   <c:forEach items="${mealList}" var="meal">
        <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.model.UserMealWithExceed"/>
        <tr class="${meal.excess ? 'red-row-not-normal' : 'green-row-normal'}">
                   <td>
                     <fmt:parseDate value="${meal.dateTime}" pattern="y-M-dd'T'H:m" var="parseDate"/>
                     <fmt:formatDate value="${parseDate}" pattern="yyyy.MM.dd HH:mm"/>
                   </td>
                   <td>${meal.description}</td>
                   <td>${meal.calories}</td>
        </tr>
    </c:forEach>
  </table>
<section>
</body>
</html>