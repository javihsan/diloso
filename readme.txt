grunt serve  arrancar normal
grunt serve:dist --force construir y arrancar con construido
grunt build --force construir

hay que contruir con el grunt build cualquier cambio en la static app si queremos que lo coja el pom del "java"

en app: 

var protocol_url = "https://"

//var domainLocalOfi = 'localhost:8888';
var domainLocalOfi = 'localhost:9001';

$rootScope.changeLang ruta de los txtos 
var url = protocol_url + appHost + "/multiText/listLocaleTexts";
//var url = "/js/lang_es.json"

				
				console.log ("Sin dominio propio");
				
				a = location.pathname.split("/");
				appFirmDomain = a[1];
cuidado con esto				//appFirmDomain = 'demo'
				
cuidado con esto				appHost = 'r8-0-0-dot-dilosohairapp.appspot.com';//'r8-0-0-dot-dilosohairapp.appspot.com';// 'localhost:8888' A quitar en la normalización			
