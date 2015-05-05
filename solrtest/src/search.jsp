<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>   
<head>   
<title>json</title>   
<script type="text/javascript">   
function xmlhttpPost(strURL) {   
    var xmlHttpReq = false;   
    var self = this;   
    if (window.XMLHttpRequest) { // Mozilla/Safari   
        self.xmlHttpReq = new XMLHttpRequest();    
    }   
    else if (window.ActiveXObject) { // IE   
        self.xmlHttpReq = new ActiveXObject(“Microsoft.XMLHTTP”);   
    }   
       
    var params = getstandardargs().concat(getquerystring());   
    var strData = params.join(‘&’);   
       
    //var header = document.getElementById(“response”);   
    //header.innerHTML = strURL+’?'+strData;   
  
    self.xmlHttpReq.open(‘get’, strURL+’?'+strData   
        +’&time=’+new Date().getTime(), true);   
    self.xmlHttpReq.setRequestHeader(‘Content-Type’,   
        ’application/x-www-form-urlencoded’);   
    self.xmlHttpReq.onreadystatechange = function() {   
        if (self.xmlHttpReq.readyState == 4) {   
            updatepage(self.xmlHttpReq.responseText);   
        }   
    }   
    self.xmlHttpReq.send(null);   
}   
  
function getstandardargs() {   
    var params = [   
        'wt=json'   
        , 'indent=on'   
        , 'hl=true'   
        , 'hl.fl=title,content'   
        , 'fl=*,title,content'   
        , 'start=0'   
        , 'rows=10'   
        ];   
  
    return params;   
}   
function getquerystring() {   
  var form = document.forms['f1'];   
  var query = form.q.value;   
  qstr = ’q=’ + encodeURI(query);    //escape   
  return qstr;   
}   
  
// this function does all the work of parsing the solr    
//response and updating the page.   
function updatepage(str){   
  //document.getElementById(“response”).innerHTML = str;   
  // use eval to parse Solr’s JSON response   
  var rsp = eval(“(“+str+“)”);   
  parse(rsp);   
}   
  
function parse(j) {   
    var header = document.getElementById(“header”);   
    var rh = j.responseHeader;   
    try{   
    var hl = j.highlighting;   
    }catch(exception){}   
    var header_str = “ 搜索: \”"+rh.params.q+“\”, 花了: ”+rh.QTime+“ms, 共显示: ”  
    +j.response.numFound+“条记录”;  //+rh.params.rows;   
    header.innerHTML = header_str;   
    var docs = j.response.docs;     
    var tab = document.getElementById(“docs”);   
    for(; tab.rows.length >1; ) {   
        tab.deleteRow(-1);   
    }   
    var tr;   
    var td;   
    var hid;   
    for(var i=0; i<docs.length; i++) {   
           
        tr = tab.insertRow(-1);   
           
        td = tr.insertCell(-1);   
        td.innerHTML = docs[i].id;   
        hid = docs[i].id;   
           
        td = tr.insertCell(-1);   
        if(hl != null){   
            try{   
                if(hl[hid].title != null){   
                    td.innerHTML = hl[hid].title;      
                       
                }else{   
                    td.innerHTML = docs[i].title;   
                }   
            }catch(exception){   
            }   
        }else{   
            td.innerHTML = docs[i].title;   
        }   
           
        td = tr.insertCell(-1);   
        if(hl != null){   
            try{   
                if(hl[hid].content != null){   
                    td.innerHTML = hl[hid].content;    
                       
                }else{   
                    td.innerHTML = docs[i].content;   
                }   
            }catch(exception){   
            }   
        }else{   
            td.innerHTML = docs[i].content;   
        }   
    }   
       
       
   }   
</script>   
</head>   
<body>   
<form action=“select/” name=“f1″ method="get"    onsubmit="xmlhttpPost(‘/SolrIK/select’); return false;" >   
      Find:    
      <input type="text" name=“q” size=“80″ >   
      <input name=“start” type="hidden" value=“0″>   
      <input name=“rows” type="hidden" value=“10″>   
      <input name=“indent” type="hidden" value=“on”>   
      <input name=“wt” type="hidden"value=“”>   
      <input name=“hl” type="hidden" value=“true”>   
      <input name=“hl.fl” type="hidden" value=“title,content”>   
      <input name=“fl” type="hidden" value=“*,title,content”>   
      <input type="button" value=" 搜 索 " onclick=“xmlhttpPost(‘/SolrIK/select’);”>   
      <input type="button" value="get json "    onclick=“document.forms['f1'].wt.value=’json’;document.forms['f1'].submit();”>   
      <input type="button" value=" get xml "    
      onclick=“document.forms['f1'].wt
      .value=";document.forms['f1'].submit();">   
  </form>   
    <div id=“header”></div>   
    <div id=“response”></div>   
  <div style="background-color: #000000; height: 1px; margin-top:10px; margin-bottom:10px;">   
  </div>   
     
    <table id=“docs” class=“tab” cellspacing=“1″>   
        <tr>   
            <td width=“50″>编号</td>   
            <td width=“400″>标题</td>   
            <td >内容</td>   
        </tr>   
    </table>   
</body>   
</html>  