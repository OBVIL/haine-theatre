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
		text = text + "<a href=\"#\" onclick=\"javascript:colorText(this, \'"+semanticField+"\',\'"+label+"\'); return false;\" title=\""+frequency+" occurrences\">"+label+"</a>, ";
	}else{
		text = text + "<a href=\"#\" onclick=\"javascript:colorText(this, \'"+semanticField+"\',\'"+label+"\'); return false;\"title=\""+frequency+"\" occurrences>"+label+"</a>";
	
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
semanticFieldLabel['Autorité'] = "<b>Autorités citées</b>";
semanticFieldLabel['Acteur'] = "<b>Acteurs cités</b>";
semanticFieldLabel['Théâtre'] = "<b>Théâtre</b>";
semanticFieldLabel['Théorie_Dramatique'] = "<b>Théorie dramatique</b>";
semanticFieldLabel['Lieu_des_spectacles'] = "<b>Lieux des spectacles</b>";
semanticFieldLabel['Qualité_Positive'] = "<b>Qualités positives</b>";
semanticFieldLabel['Qualité_Négative'] = "<b>Qualités négatives</b>";
semanticFieldLabel['Société'] = "<b>Société</b>";
semanticFieldLabel['Morale_Positive'] = "<b>Morale positive</b>";
semanticFieldLabel['Morale_Négative'] = "<b>Morale négative</b>";
//semanticFieldLabel['Passion'] = "<b>Passion</b>";
semanticFieldLabel['Religion'] = "<b>Religion</b>";
semanticFieldLabel['Fête'] = "<b>Fête et musique</b>";
semanticFieldLabel['Guerre'] = "<b>Guerre</b>";
semanticFieldLabel['Origine'] = "<b>Nationalité</b>";
semanticFieldLabel['Droit'] = "<b>Droit</b>";
semanticFieldLabel['Economie'] = "<b>Economie et argent</b>";
semanticFieldLabel['Femme'] = "<b>Femme</b>";
semanticFieldLabel['Passion_Négative'] = "<b>Passion négative</b>";
semanticFieldLabel['Passion_Positive'] = "<b>Passion positive</b>";

//init semantic field colors
semanticFieldColors['Autorité'] = "#006600";
semanticFieldColors['Théâtre'] = "#00CC00";
semanticFieldColors['Qualité_Positive'] = "#336666";
semanticFieldColors['Qualité_Négative'] = "#009999";
semanticFieldColors['Société'] = "#CC0066";
semanticFieldColors['Morale_Positive'] = "#3300FF";
semanticFieldColors['Morale_Négative'] = "#330099";
//semanticFieldColors['Passion'] = "#333366";
semanticFieldColors['Religion'] = "#663300";
semanticFieldColors['Fête'] = "#CC0000";
semanticFieldColors['Guerre'] = "#000033";
semanticFieldColors['Origine'] = "#006666";
semanticFieldColors['Droit'] = "#0066CC";
semanticFieldColors['Economie'] = "#0066FF";
semanticFieldColors['Femme'] = "#0060FF";
semanticFieldColors['Acteur'] = "#006699";
semanticFieldColors['Lieu_des_spectacles'] = "#CC0099";
semanticFieldColors['Théorie_Dramatique'] = "#00CC99";
semanticFieldColors['Passion_Négative'] = "#333366";
semanticFieldColors['Passion_Positive'] = "#333399";

//init semantic field instance
semanticFieldInstance['Autorité'] =  new Array();
semanticFieldInstance['Théâtre'] = new Array() ;
semanticFieldInstance['Qualité_Positive'] = new Array();
semanticFieldInstance['Qualité_Négative'] = new Array();
semanticFieldInstance['Société'] = new Array();
semanticFieldInstance['Morale_Positive'] = new Array();
semanticFieldInstance['Morale_Négative'] = new Array();
//semanticFieldInstance['Passion'] = new Array();
semanticFieldInstance['Religion'] = new Array();
semanticFieldInstance['Fête'] = new Array();
semanticFieldInstance['Guerre'] = new Array();
semanticFieldInstance['Origine'] = new Array();
semanticFieldInstance['Droit'] = new Array();
semanticFieldInstance['Economie'] = new Array();
semanticFieldInstance['Femme'] = new Array();
semanticFieldInstance['Acteur'] = new Array();
semanticFieldInstance['Lieu_des_spectacles'] = new Array();
semanticFieldInstance['Théorie_Dramatique'] = new Array();
semanticFieldInstance['Passion_Négative'] = new Array();
semanticFieldInstance['Passion_Positive'] = new Array();

//init semantic field statistics
semanticFieldStatistics['Autorité'] =  0;
semanticFieldStatistics['Théâtre'] = 0 ;
semanticFieldStatistics['Qualité_Positive'] = 0;
semanticFieldStatistics['Qualité_Négative'] = 0;
semanticFieldStatistics['Société'] = 0;
semanticFieldStatistics['Morale_Positive'] = 0;
semanticFieldStatistics['Morale_Négative'] = 0;
//semanticFieldStatistics['Passion'] = 0;
semanticFieldStatistics['Religion'] = 0;
semanticFieldStatistics['Fête'] = 0;
semanticFieldStatistics['Guerre'] = 0;
semanticFieldStatistics['Origine'] = 0;
semanticFieldStatistics['Droit'] = 0;
semanticFieldStatistics['Economie'] = 0;
semanticFieldStatistics['Femme'] = 0;
semanticFieldStatistics['Acteur'] = 0;
semanticFieldStatistics['Lieu_des_spectacles'] = 0;
semanticFieldStatistics['Théorie_Dramatique'] = 0;
semanticFieldStatistics['Passion_Négative'] = 0;
semanticFieldStatistics['Passion_Positive'] = 0;

//init semantic field all instance
semanticFieldAllInstance['Autorité'] =  new Array();
semanticFieldAllInstance['Théâtre'] = new Array() ;
semanticFieldAllInstance['Qualité_Positive'] = new Array();
semanticFieldAllInstance['Qualité_Négative'] = new Array();
semanticFieldAllInstance['Société'] = new Array();
semanticFieldAllInstance['Morale_Positive'] = new Array();
semanticFieldAllInstance['Morale_Négative'] = new Array();
//semanticFieldAllInstance['Passion'] = new Array();
semanticFieldAllInstance['Religion'] = new Array();
semanticFieldAllInstance['Fête'] = new Array();
semanticFieldAllInstance['Guerre'] = new Array();
semanticFieldAllInstance['Origine'] = new Array();
semanticFieldAllInstance['Droit'] = new Array();
semanticFieldAllInstance['Economie'] = new Array();
semanticFieldAllInstance['Femme'] = new Array();
semanticFieldAllInstance['Acteur'] = new Array();
semanticFieldAllInstance['Lieu_des_spectacles'] = new Array();
semanticFieldAllInstance['Théorie_Dramatique'] = new Array();
semanticFieldAllInstance['Passion_Négative'] = new Array();
semanticFieldAllInstance['Passion_Positive'] = new Array();

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
