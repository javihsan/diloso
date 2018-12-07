
/**Reemplaza pattern por repla dentro de la cadena str**/
function replace(str, pattern, repla) {

	s = 0;
	result = "";
    	e = str.indexOf(pattern, s)
	while (e >= 0) {
		result += str.substring(s, e);
		result += repla;
		s = e+pattern.length;
		e = str.indexOf(pattern, s)
	}

	result += str.substring(s);

	return result;
}	

function getValueSelected (sel){
	return sel.options[sel.options.selectedIndex].value
}

function getTextSelected (sel){ 
  return sel.options[sel.options.selectedIndex].text
}

function getValue (sel,num){
	return sel.options[num].value
}

function getText (sel,num){
  return sel.options[num].text
}


function cerrar(){
	window.opener=self
	window.close()
}	

var focoDes=true
function foco (obj){
	if (focoDes){
		obj.blur()
	}	
}

function custom_print() {
    if (document.all) {
        if (navigator.appVersion.indexOf("MSIE 5.0") == -1) {
            var OLECMDID_PRINT = 6;
            var OLECMDEXECOPT_DONTPROMPTUSER = 2;
            var OLECMDEXECOPT_PROMPTUSER = 1;
            var WebBrowser = "<OBJECT ID=\"WebBrowser1\" WIDTH=0 HEIGHT=0 CLASSID=\"CLSID:8856F961-340A-11D0-A96B-00C04FD705A2\"></OBJECT>";
            document.body.insertAdjacentHTML("beforeEnd", WebBrowser);
            WebBrowser1.ExecWB(6, 2);
            WebBrowser1.outerHTML = "";
        } 
	else {
            print();
        }
    }
    else {
        print();
    }
}