<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:set var="baseURL"
	   value="${fn:replace(pageContext.request.requestURL, pageContext.request.requestURI, pageContext.request.contextPath)}"/>
<!DOCTYPE html>
<html>
<head>
	<title>Facebook login</title>
</head>
<body>

<a href="https://www.facebook.com/dialog/oauth?client_id=735092636525455&redirect_uri=${baseURL}/socialauth/fb">Login with Facebook</a>

</body>
</html>