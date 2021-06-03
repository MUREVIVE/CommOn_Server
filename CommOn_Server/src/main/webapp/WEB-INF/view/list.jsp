<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
         pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
    <title>롤 프로게이머</title>
</head>

<body>
    <table border="1">
        <thead>
            <tr>
            	<th>id</th>
                <th>name</th>
                <th>email</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${memberList}" var="member">
                <tr>
		<td>${member.ident}</td>
                    <td>${member.full_name}</td>
                    <td>${member.email}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>

</html>