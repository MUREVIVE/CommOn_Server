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
		<th>name</th>
                <th>longitude</th>
                <th>latitude</th>
                <th>distance</th>
                <th>time</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${facilities}" var="m_facility">
                <tr>
		<td>${m_facility.name}</td>
		<td>${m_facility.longitude}</td>
                    <td>${m_facility.latitude}</td>
                    <td>${m_facility.distance}</td>
                    <td>${m_facility.time}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>

</html>