Array.prototype.contains = function(obj) {
    var i = this.length;
    while (i--) {
        if (this[i] == obj) {
            return true;
        }
    }
    return false;
}

String.prototype.trim = function () {
    return this.replace(/^\s+|\s+$/g, '');
};


function createElement(element,attribute,inner){if(typeof(element) === "undefined"){return false;}if(typeof(attribute) === "undefined"){attribute = "";}if(typeof(inner) === "undefined"){inner = "";}var el = document.createElement(element);if(attribute.length > 1 && attribute[0] == "[" && attribute[attribute.length-1] == "]"){var attr = attribute.split("][");attr[0] = attr[0].substr(1);attr[attr.length-1] = attr[attr.length-1].substr(0,attr[attr.length-1].length-1);for(var k = 0, len = attr.length; k < len; k++){var el_attr,el_attr_val="",ind=attr[k].indexOf(":");if(ind > 0){el_attr = attr[k].substr(0,ind);el_attr_val = attr[k].substr(ind+1);}else{el_attr = attr[k].substr(0);}el.setAttribute(el_attr,el_attr_val);}}if(Array.isArray(inner)){for(var k = 0;k < inner.length;k++){if(inner[k].tagName){el.appendChild(inner[k]);}else{el.appendChild(document.createTextNode(inner[k]));}}}else{if(inner.tagName){el.appendChild(inner);}else{el.innerHTML = inner;}}return el;}

function convertArrayToString(array, semanticField){
var text = "";
var i;

for(i=0; i<array.length;i++){
	if(i < array.length - 1){
		text = text + "<a href=\"#\" onclick=\"javascript:colorText(\'"+semanticField+"\',\'"+array[i]+"\'); return false;\">"+array[i]+"</a>, ";
	}else{
		text = text + "<a href=\"#\" onclick=\"javascript:colorText(\'"+semanticField+"\',\'"+array[i]+"\'); return false;\">"+array[i]+"</a>";
	}
}
return text;
}


function sortMapByValue(map)
{
    var tupleArray = [];
    for (var key in map) tupleArray.push([key, map[key]]);
    tupleArray.sort(function (a, b) { return b[1] - a[1]});
    return tupleArray;
}

function sortAndconvertArrayToString(array, semanticField){

var text = "";
var i;
var termFrequency = {};

for(i=0; i<array.length;i++){
		
	if((array[i] in termFrequency) == false){
		termFrequency[array[i]] = 1;
	}else{
		var f = termFrequency[array[i]];
		termFrequency[array[i]] = f + 1;
	
	}
}

var termFrequency = sortMapByValue(termFrequency);
i = 0;
for(var key in termFrequency){
	var label = termFrequency[key][0];
	var frequency = termFrequency[key][1];
	if(!(label == undefined)){
		if(i < termFrequency.length-1){
		text = text + "<a href=\"#\" style=\"padding:0\" onclick=\"javascript:colorText(this, \'"+semanticField+"\',\'"+label+"\'); return false;\" title=\""+frequency+" occurrences\">"+label+"</a>, ";
	}else{
		text = text + "<a href=\"#\" style=\"padding:0\" onclick=\"javascript:colorText(this, \'"+semanticField+"\',\'"+label+"\'); return false;\"title=\""+frequency+"\" occurrences>"+label+"</a>";
	
	}
	i = i+1;
	}
	
}


return text;
}


function colorText(currentElement,currentSemanticField,currentValue){
	
	//delete all color
	var terms = document.getElementsByClassName("term");
	var i = 0;
	var labelChanged = false;
	for(i = 0; i < terms.length; i++)
	{
		var element = terms[i];
		var className = element.className.replace("term ","");
		var semanticField = className.split(" ")[0].trim();
		var value = className.split(" ")[1];
		value = value.replace("_"," ");
		value = value.trim();
		var color = semanticFieldColors[currentSemanticField];
		if(semanticField == currentSemanticField && value == currentValue){
			if(element.getAttribute("style") != null){
				element.removeAttribute("style");
				element.removeAttribute("title");
				if(labelChanged == false){
					currentElement.innerHTML = currentElement.innerHTML.replace("<b>","");
					currentElement.innerHTML = currentElement.innerHTML.replace("</b>","");
					labelChanged = true;
				}
				
			}else{
				element.setAttribute("style","background-color:"+color+"; color:white");
				element.setAttribute("title",currentSemanticField.replace("_"," "));
				if(labelChanged == false){
					currentElement.innerHTML = "<b>"+ currentElement.innerHTML + "</b>";
					labelChanged = true;
				}
				
			}
			
		}
		
	}
   
   //
	
}

function colorTextSemanticField(currentElement, currentSemanticField){
	//delete all color
	var terms = document.getElementsByClassName("term");
	var i = 0;
	var liste = currentElement.parentNode.getElementsByTagName("a");
	var allNotColored = true;
	
	for(i=1;i<liste.length;i++){
		
		if(liste[i].innerHTML.indexOf("<b>") > -1){
			allNotColored = false;
			break;
		
		}
	}
	if(allNotColored == false){
		for(i = 0; i < terms.length; i++)
	    {
		  var element = terms[i];
		  var className = element.className.replace("term ","");
		  var semanticField = className.split(" ")[0].trim();
		  var color = semanticFieldColors[currentSemanticField];
		  if(semanticField == currentSemanticField){
				element.removeAttribute("style");
				element.removeAttribute("title");
		  }
		}
	    
		for(i=1;i<liste.length;i++){
			liste[i].innerHTML = liste[i].innerHTML.replace("<b>","");
			liste[i].innerHTML = liste[i].innerHTML.replace("</b>","");
	    }
	}else{
	  for(i = 0; i < terms.length; i++)
	  {
		var element = terms[i];
		var className = element.className.replace("term ","");
		var semanticField = className.split(" ")[0].trim();
		var color = semanticFieldColors[currentSemanticField];
		if(semanticField == currentSemanticField){
			element.setAttribute("style","background-color:"+color+"; color:white");
			element.setAttribute("title",currentSemanticField.replace("_"," "));
			}
			
		}
		for(i=1;i<liste.length;i++){
			liste[i].innerHTML = "<b>"+liste[i].innerHTML+"</b>";
			
	    }
		
	}
	
}

var terms = document.getElementsByClassName("term");
var i = 0 ;
var ctr = 0;
var array = [];
var semanticFieldLabel = {};
var semanticFieldInstance = {};
var semanticFieldAllInstance = {};
var semanticFieldStatistics = {};
var semanticFieldColors = {};

//init semantic field label
//init semantic field colors
//init semantic field instance
//init semantic field statistics
//init semantic field all instance

$VAR_DECLARATIONS

for(i = 0; i < terms.length; i++)
{
   var element = terms[i];
   //element.setAttribute("style","background-color:lightgrey; color:white");
   //element.removeAttribute("style");
   //element.addEventListener("onclick",colorText());
   var className = element.className.replace("term ","");
   var semanticField = className.split(" ")[0].trim();
   var value = className.split(" ")[1];
   value = value.replace("_"," ");
   value = value.trim();
   //alert("semanticField "+semanticField);
   //alert("value "+value);
   
   var liste = semanticFieldInstance[semanticField];
   if (liste != null){
   var count = semanticFieldStatistics[semanticField];
   semanticFieldStatistics[semanticField] = count + 1;
   // use liste 2 to sort all element by frequence
   var liste2 = semanticFieldAllInstance[semanticField];
   liste2[liste2.length] = value;
   
   //alert (liste.length);
   if(liste.contains(value) == false){
		liste[liste.length] = value;
		semanticFieldInstance[semanticField] = liste;
   }
   }
   
	
  
      
}

//creating semantic word list
i = 0;
for(semanticField in semanticFieldLabel){
	var liste = semanticFieldInstance[semanticField];
	var text = sortAndconvertArrayToString(semanticFieldAllInstance[semanticField],semanticField);
	if(liste.length > 0){
	 //var element = createElement("li","","<a href=\"#\" onclick=\"javascript:colorTextSemanticField(\'"+semanticField+"\'); return //false;\">"+semanticFieldLabel[semanticField]+"</a>"+"<b>["+semanticFieldStatistics[semanticField]+" occurrences] :</b><br/>"+"["+convertArrayToString(liste, semanticField)+"]");
	 var element = createElement("li","","<a href=\"#\" onclick=\"javascript:colorTextSemanticField(this, \'"+semanticField+"\'); return false;\">"+semanticFieldLabel[semanticField]+"</a>"+"<b>["+semanticFieldStatistics[semanticField]+" occurrences] :</b><br/>"+"["+text+"]");
	 
	 array[i] = element;
	 i = i+1;
	}
	
}
if(array.length > 0){
	var motCles = createElement("ul","",array);
    var motClesListe = createElement("li","[class:  less]",["Annotations associées:",motCles]);
    var treeClass = createElement("ul","[class:tree treejs]",motClesListe);
    var divTags = createElement("div","[id:tags]",treeClass);
    //alert(divTags.id);
    var article = document.getElementById("article");
    //alert(article.innerHTML);
    article.insertBefore(divTags,article.childNodes[0]);
}
