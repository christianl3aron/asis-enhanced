/**
 * Created by Christian Baron on 29/03/2015.
 */

self.addEventListener('message', function(e) {
    var data = e.data;
    var xmlhttp;

    if (XMLHttpRequest) {
        // code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp = new XMLHttpRequest();
    } else {
        // code for IE6, IE5
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }

    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState == 4 ) {
            if(xmlhttp.status == 200){
                self.postMessage(xmlhttp.responseText);
            }
            else if(xmlhttp.status == 400) {
                alert('There was an error 400')
            }
            else {
                alert('something else other than 200 was returned')
            }
        }
    }

    xmlhttp.open("GET","/MantenimientoServlet?act=showAsi&ti="+data.ti+"&tf="+data.tf+"&cods="+data.cods, true);
    xmlhttp.send();

}, false);