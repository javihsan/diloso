<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page import="org.springframework.web.servlet.support.RequestContextUtils"%>
<html>
<head>
<meta name="organization" content="Diloso">
<meta name="author" content="Diloso">
<meta name="origen" content="Diloso">
<meta name="locality" content="Madrid, España">
<meta name="lang" content="es">
<meta name="description" content="Diloso, Software de Productividad - Productivity Software">
<meta name="keywords" content="diloso, software, eclipse, productividad, productivity, plugin, plugins, codigo, code, generación, generate">
<meta http-equiv=Content-Type content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="/web/css/estilos.css">
<script language="JavaScript" SRC="/web/util/util.js"></script>
<link rel="SHORTCUT ICON" href="/web/img/logo.png">

<title>Diloso</title>

</head>

<body>
<div id="contenedora">
	<div id="cabecera_corpo">
		<div id="imagen_logo_titulo_corpo">
			<img src="/web/img/cabecera.jpg" width="500px" height="150px" border="0"/>
		</div>
		<div id="idioma">
			
			<div class="texto_idioma"><ul><li>
			<% if (!RequestContextUtils.getLocale(request).getLanguage().equals("en")){ %>
			<a href="?lang=en" class="link_idioma"><img src="/web/img/english.jpg" width="110px" height="45px" border="0"/></a></li></ul></div>
			<% } else if (!RequestContextUtils.getLocale(request).getLanguage().equals("es_ES")){ %>
			<a href="?lang=es" class="link_idioma"><img src="/web/img/espanol.jpg" width="110px" height="45px" border="0"/></a></li></ul></div>
			<% } %>
		</div> 	
	</div>
	
	<!-- inicio menu --> 
	<%@include file="Menu.jsp"%>
	<!-- fin menu -->
			
	<div id="contenido_centro">

    
