<%@ page import="com.diloso.web.negocio.utils.Utils" %>
	<div id="pie">
		<div class="pie_logo"><img src="/web/img/logo.jpg" width="50px" heights="50px" border="0"></div>	
		<div class="pie_copyright">
			Copyright &copy; <%=Utils.getCurrentDate("yyyy")%> <b>Diloso</b>. <spring:message code="pie.reserved"/>.
		</div>	
	</div>
