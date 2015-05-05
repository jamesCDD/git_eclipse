<%@  page language="java" import = "java.util.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
function xmlhttpPost(strURL) {
	var xmlHttpReq = false;
	var self = this;
	if (window.XMLHttpRequest)
	{
		self.xmlHttpReq = new ActiveXObject ("Microsoft.XMLHTTP");
		
	}
	var params = getstandardargs().concat(getquerystring());
	var strData = params.join(‘&’); 
	
	self.xmlHttpReq.open('get', strUrl+'?' +strData +'&time='+new Date().getTime(),true);
	self.xmlHttpReq.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
	self.xmlHttpReq.onreadystatechange = function(){
		if (self.xmlHttpReq.readyState == 4)
			{
			updatepage(self.xmlHttpReq.responseText);
			
			}
	}
	self.xmlHttpReq.send(null);
	
	
}

function getstandardargs()
{
	var params = [
	              
	              'wt=json',
	              'indent=on',
	              'hl=true',
	              'hl.fl=tilte,content',
	              'fl=*,title,content',
	              'start=0',
	              'rows=10'
	              ];
	return params;
	}

function getquerystring() 
{
var form = document.forms['fl'];
var query = form.q.value;
qstr = 'q='+encodeURI(query)
return qstr;
	
	}
	
function updatepage(str){
	
	var rsp = eval ("("+str+")");
	parse(rsp);
}

function parse(j){
	var header = document.getElementById("header");
	var rh j.responseHeader;
	try{
		
		var hl = j.highlighting;
		
	} catch (exception) {
		
	}
	var header_str = "搜索: \" "+rh.params.q+" \", 花了:"+rh.QTime+"ms,共显示: " +j.response.numFound+ "条记录";
	header.innerHTML=header_str;
	var docs =j.response.docs;
	var tab=document.getElementById("docs";)
	for (;tab.rows.length>1; ){
		
		tab.deleteRow(-1);
	}
	var tr;
	var td;
	var hid;
	for (var i=0;i<docs.length;i++){
		tr=tab.insertRow(-1)	;
		td=tr.insertCell(-1);
		hid=docs[i].id;
		td=tr.insertCell(-1);
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
<form action="select/" name="fl" method="get" onsubmit="xmlhttpPost('solr/select');return false;">
Find:
<input type="text" name="q" size="80">
<input name="start" type="hidden" value ="0">
<input name="row" type="hidden" value ="10">
<input name="indent" type="hidden" value ="on">
<input name="wt" type="hidden" value ="">
<input name="hl" type="hidden" value ="true">
<input name="hl.fl" type="hidden" value ="title,content">
<input name="fl" type="hidden" value ="*,title,content">
<input type="button"  value =" 搜索"
         onclick="xmlhttpPost('/solr/select');"       >
<input type="button" value= "get json" onclick="document.forms['fl'].wt.value='json';document.forms['fl'].submit();">
<input type="button" value="get xml" onclick="document.forms['fl'].wt.value='';document.forms['fl].submit();">

</form>
<div id="header"></div>
<div id="response"></div>
<div style="background-color: #000000;height: 1px;margin-top: 10px;margin-bottom: 10px;"></div>
<table id ="docs" class="tab" cellspacing="1">
<tr>
<td width="50">编号</td>
<td width="400">标题</td>
<td>内容</td>


</table>

</body>
</html>