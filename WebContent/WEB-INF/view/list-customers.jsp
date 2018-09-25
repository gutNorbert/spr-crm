<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>List Customer</title>
	<!-- CSS referencia -->
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css" />
</head>
<body>
	<div id="wrapper">
		<div id= "header">
			<h2>CRM - Customer Relationship Manager</h2>
		</div>
	</div>

	<div id="container">
	
		<div id="content">
		 <!--  keres�men� -->
            <form:form action="search" method="POST">
                Search customer: <input type="text" name="theSearchName" />
                
                <input type="submit" value="Search" class="add-button" />
            </form:form>
            
			<!-- �j gomb hozz�ad�sa -->
			
			<input type="button" value="Add Customer" 
					onClick="window.location.href='showFormForAdd';return false;"
					class="add-button"/>
			
			<table>
				<tr>
					<th>First Name </th>
					<th>Last Name </th>
					<th>Email </th>
					<th>Action</th>
				</tr>
				
				<!-- V�gigmegy�nk a v�s�rl�kon -->
				<c:forEach var="tempCustomer" items="${customers}">
				
				<!-- update link l�trehoz�sa -->
				<c:url var="updateLink" value="/customer/showFormForUpdate">
					<c:param name="customerId" value="${tempCustomer.id}"/>
				</c:url>
				
				<!-- delete link l�trehoz�sa -->
				<c:url var="deleteLink" value="/customer/delete">
					<c:param name="customerId" value="${tempCustomer.id}"/>
				</c:url>
				
					<tr>
						<td>${tempCustomer.firstName}</td>
						<td>${tempCustomer.lastName}</td>
						<td>${tempCustomer.email}</td>
						
						<td> 
							<!-- megjelen�tj�k az update linket (fel�l url, updateLink v�ltoz� -->
							<a href="${updateLink}">Update</a> 
							|
							<a href="${deleteLink}" onClick="if (!(confirm('Are you sure you want to delete this customer?'))) return false">Delete</a> 
						</td>
						
					</tr>
				</c:forEach>
				
				
			</table>
			
			
		</div>
	
	</div>

</body>
</html>