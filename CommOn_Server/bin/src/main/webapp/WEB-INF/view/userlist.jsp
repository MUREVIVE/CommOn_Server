<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
         pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
    <title>loaction</title>
</head>

<body>
    <table border="1">
        <thead>
            <tr>
		<th>ident</th>
                <th>longitude</th>
                <th>latitude</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${userList}" var="m_user">
                <tr>
		<td>${m_user.ident}</td>
		<td>${m_user.longitude}</td>
                    <td>${m_user.latitude}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>

</html>