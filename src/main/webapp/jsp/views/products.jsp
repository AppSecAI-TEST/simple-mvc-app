<!DOCTYPE html>
<%@ include file="/jsp/includes.jsp"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Products page</title>
        <script type="text/javascript" src="ui-resources/js/jquery-3.2.1.min.js"></script>
    </head>
    <body>
     <div style="float:right">
        <a href="logout">Logout</a>
    </div>
    <div>
        <table id="products" border="1">
            <thead>
                <tr><th>Title</th><th>Price</th></tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>
    <div>
        <a href="/logged">Go to previous page</a>
    </div>
    </body>
</html>
<script>
$(document).ready(function(){
    console.log("Products ready!");
    $.ajax({
        url: "/products",
        type: "GET",
        success: function(data, textStatus, jQxhr){
            console.log(data);
            tbody = "";
            $.each(data, function(index, value){
                tbody += "<tr><td>"+value.title+"</td><td>"+value.price+"</td></tr>";
            });
            $("#products tbody").html(tbody);
        },
        error: function(jqXhr, textStatus, errorThrown){
            console.log(errorThrown);
        }
   });
});
</script>