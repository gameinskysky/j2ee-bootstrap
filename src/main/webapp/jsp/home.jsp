<%@ page import="java.util.Enumeration" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>
</head>
<body>
<h1>Welcome home, ${it.name}</h1>

<%
    Enumeration e = pageContext.getAttributeNamesInScope(PageContext.PAGE_SCOPE);
    while (e.hasMoreElements()) {
        String attrName = (String) e.nextElement();
        out.println(attrName + ": " + pageContext.getAttribute(attrName) + "<br/>");
    }

    out.println("Name is: " + pageContext.findAttribute("it"));
%>

</body>
</html>