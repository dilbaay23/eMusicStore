<%--
  Created by IntelliJ IDEA.
  User: Moon
  Date: 12/15/2020
  
--%>

<%@include file="/WEB-INF/views/template/header.jsp"%>


<div class="container-wrapper">

    <div class ="container">
        <div class ="page-header">

            <h1>Administrator Page</h1>

            <p class="lead">This is the administrator page!</p>
        </div>
        <h3>
        <a href="<c:url value="/admin/productInventory" />" >Product Inventory </a>
        </h3>

        <p>Here you can view, check and modify the product inventory! </p>



        <%@include file="/WEB-INF/views/template/footer.jsp"%>
