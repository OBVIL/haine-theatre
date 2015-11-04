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

semanticFieldLabel['Autorité'] = "<b>Autorité</b>";
semanticFieldColors['Autorité'] = "#006600";
semanticFieldInstance['Autorité'] =  new Array();
semanticFieldStatistics['Autorité'] =  0;
semanticFieldAllInstance['Autorité'] =  new Array();

semanticFieldLabel['Théâtre'] = "<b>Théâtre</b>";
semanticFieldColors['Théâtre'] = "#00CC00";
semanticFieldInstance['Théâtre'] =  new Array();
semanticFieldStatistics['Théâtre'] =  0;
semanticFieldAllInstance['Théâtre'] =  new Array();

semanticFieldLabel['Société'] = "<b>Société</b>";
semanticFieldColors['Société'] = "#336666";
semanticFieldInstance['Société'] =  new Array();
semanticFieldStatistics['Société'] =  0;
semanticFieldAllInstance['Société'] =  new Array();

semanticFieldLabel['Morale_Positive'] = "<b>Morale_Positive</b>";
semanticFieldColors['Morale_Positive'] = "#009999";
semanticFieldInstance['Morale_Positive'] =  new Array();
semanticFieldStatistics['Morale_Positive'] =  0;
semanticFieldAllInstance['Morale_Positive'] =  new Array();

semanticFieldLabel['Morale_Négative'] = "<b>Morale_Négative</b>";
semanticFieldColors['Morale_Négative'] = "#CC0066";
semanticFieldInstance['Morale_Négative'] =  new Array();
semanticFieldStatistics['Morale_Négative'] =  0;
semanticFieldAllInstance['Morale_Négative'] =  new Array();

semanticFieldLabel['Religion'] = "<b>Religion</b>";
semanticFieldColors['Religion'] = "#3300FF";
semanticFieldInstance['Religion'] =  new Array();
semanticFieldStatistics['Religion'] =  0;
semanticFieldAllInstance['Religion'] =  new Array();

semanticFieldLabel['Fête'] = "<b>Fête</b>";
semanticFieldColors['Fête'] = "#330099";
semanticFieldInstance['Fête'] =  new Array();
semanticFieldStatistics['Fête'] =  0;
semanticFieldAllInstance['Fête'] =  new Array();

semanticFieldLabel['Guerre'] = "<b>Guerre</b>";
semanticFieldColors['Guerre'] = "#333366";
semanticFieldInstance['Guerre'] =  new Array();
semanticFieldStatistics['Guerre'] =  0;
semanticFieldAllInstance['Guerre'] =  new Array();

semanticFieldLabel['Origine'] = "<b>Origine</b>";
semanticFieldColors['Origine'] = "#663300";
semanticFieldInstance['Origine'] =  new Array();
semanticFieldStatistics['Origine'] =  0;
semanticFieldAllInstance['Origine'] =  new Array();

semanticFieldLabel['Droit'] = "<b>Droit</b>";
semanticFieldColors['Droit'] = "#CC0000";
semanticFieldInstance['Droit'] =  new Array();
semanticFieldStatistics['Droit'] =  0;
semanticFieldAllInstance['Droit'] =  new Array();

semanticFieldLabel['Economie'] = "<b>Economie</b>";
semanticFieldColors['Economie'] = "#000033";
semanticFieldInstance['Economie'] =  new Array();
semanticFieldStatistics['Economie'] =  0;
semanticFieldAllInstance['Economie'] =  new Array();

semanticFieldLabel['Passion'] = "<b>Passion</b>";
semanticFieldColors['Passion'] = "#006666";
semanticFieldInstance['Passion'] =  new Array();
semanticFieldStatistics['Passion'] =  0;
semanticFieldAllInstance['Passion'] =  new Array();

semanticFieldLabel['Qualité_Positive'] = "<b>Qualité_Positive</b>";
semanticFieldColors['Qualité_Positive'] = "#0066CC";
semanticFieldInstance['Qualité_Positive'] =  new Array();
semanticFieldStatistics['Qualité_Positive'] =  0;
semanticFieldAllInstance['Qualité_Positive'] =  new Array();

semanticFieldLabel['Qualité_Négative'] = "<b>Qualité_Négative</b>";
semanticFieldColors['Qualité_Négative'] = "#0066FF";
semanticFieldInstance['Qualité_Négative'] =  new Array();
semanticFieldStatistics['Qualité_Négative'] =  0;
semanticFieldAllInstance['Qualité_Négative'] =  new Array();

semanticFieldLabel['Femme'] = "<b>Femme</b>";
semanticFieldColors['Femme'] = "#0060FF";
semanticFieldInstance['Femme'] =  new Array();
semanticFieldStatistics['Femme'] =  0;
semanticFieldAllInstance['Femme'] =  new Array();

semanticFieldLabel['Lieu_des_spectacles'] = "<b>Lieu_des_spectacles</b>";
semanticFieldColors['Lieu_des_spectacles'] = "#0050FE";
semanticFieldInstance['Lieu_des_spectacles'] =  new Array();
semanticFieldStatistics['Lieu_des_spectacles'] =  0;
semanticFieldAllInstance['Lieu_des_spectacles'] =  new Array();

semanticFieldLabel['Théorie_Dramatique'] = "<b>Théorie_Dramatique</b>";
semanticFieldColors['Théorie_Dramatique'] = "#0030FE";
semanticFieldInstance['Théorie_Dramatique'] =  new Array();
semanticFieldStatistics['Théorie_Dramatique'] =  0;
semanticFieldAllInstance['Théorie_Dramatique'] =  new Array();

semanticFieldLabel['Acteur'] = "<b>Acteur</b>";
semanticFieldColors['Acteur'] = "#009900";
semanticFieldInstance['Acteur'] =  new Array();
semanticFieldStatistics['Acteur'] =  0;
semanticFieldAllInstance['Acteur'] =  new Array();

semanticFieldLabel['Auteur'] = "<b>Auteur</b>";
semanticFieldColors['Auteur'] = "#E6E2AF";
semanticFieldInstance['Auteur'] =  new Array();
semanticFieldStatistics['Auteur'] =  0;
semanticFieldAllInstance['Auteur'] =  new Array();

semanticFieldLabel['Affect'] = "<b>Affect</b>";
semanticFieldColors['Affect'] = "#A7A37E";
semanticFieldInstance['Affect'] =  new Array();
semanticFieldStatistics['Affect'] =  0;
semanticFieldAllInstance['Affect'] =  new Array();

semanticFieldLabel['Musique'] = "<b>Musique</b>";
semanticFieldColors['Musique'] = "#046380";
semanticFieldInstance['Musique'] =  new Array();
semanticFieldStatistics['Musique'] =  0;
semanticFieldAllInstance['Musique'] =  new Array();

semanticFieldLabel['Nationalité'] = "<b>Nationalité</b>";
semanticFieldColors['Nationalité'] = "#002F2F";
semanticFieldInstance['Nationalité'] =  new Array();
semanticFieldStatistics['Nationalité'] =  0;
semanticFieldAllInstance['Nationalité'] =  new Array();

semanticFieldLabel['Spectateur'] = "<b>Spectateur</b>";
semanticFieldColors['Spectateur'] = "#B4AF91";
semanticFieldInstance['Spectateur'] =  new Array();
semanticFieldStatistics['Spectateur'] =  0;
semanticFieldAllInstance['Spectateur'] =  new Array();

semanticFieldLabel['Théorie Dramatique'] = "<b>Théorie Dramatique</b>";
semanticFieldColors['Théorie Dramatique'] = "#787746";
semanticFieldInstance['Théorie Dramatique'] =  new Array();
semanticFieldStatistics['Théorie Dramatique'] =  0;
semanticFieldAllInstance['Théorie Dramatique'] =  new Array();

semanticFieldLabel['Spectacle'] = "<b>Spectacle</b>";
semanticFieldColors['Spectacle'] = "#40411E";
semanticFieldInstance['Spectacle'] =  new Array();
semanticFieldStatistics['Spectacle'] =  0;
semanticFieldAllInstance['Spectacle'] =  new Array();



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
