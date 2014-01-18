<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:set var="baseURL"
       value="${fn:replace(pageContext.request.requestURL, pageContext.request.requestURI, pageContext.request.contextPath)}"/>
<!DOCTYPE html>
<html>
<head>
    <title>Login to J2ee bootstrap</title>
    <link rel="stylesheet" href="${baseURL}/static/css/base.css">
    <link rel="stylesheet" href="${baseURL}/static/css/skeleton.css">
    <link rel="stylesheet" href="${baseURL}/static/css/layout.css">
</head>
<body>

<c:if test="${it.failed}">
    <div class="notice">
        <a href="" class="close">close</a>

        <p class="warn">Failed to register a new user. This username already exists.</p>
    </div>
</c:if>


<div class="container">

    <div class="form-bg">
        <form method="post">
            <h2>Register a new user</h2>

            <p><input type="text" name="login" placeholder="Username"></p>

            <p><input type="password" name="password" placeholder="Password"></p>
            <button type="submit"></button>
        </form>
    </div>

</div>

<script src="${baseURL}/static/js/jquery-1.5.1.min.js"></script>
<script src="${baseURL}/static/js/app.js"></script>

</body>
</html>