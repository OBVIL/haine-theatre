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

function colorText(currentSemanticField,currentValue){
	
	//delete all color
	var terms = document.getElementsByClassName("term");
	var i = 0;
	for(i = 0; i < terms.length; i++)
	{
		var element = terms[i];
		var className = element.className.replace("term ","");
		var semanticField = className.split(" ")[0].trim();
		var value = className.split(" ")[1];
		value = value.replace("_"," ");
		value = value.trim();
		if(semanticField == currentSemanticField && value == currentValue){
			element.setAttribute("style","background-color:lightgrey; color:white");
		}else{
			element.removeAttribute("style");
		}
		
	}
   
   //
	
}

function colorTextSemanticField(currentSemanticField){
	//delete all color
	var terms = document.getElementsByClassName("term");
	var i = 0;
	for(i = 0; i < terms.length; i++)
	{
		var element = terms[i];
		var className = element.className.replace("term ","");
		var semanticField = className.split(" ")[0].trim();
		
		if(semanticField == currentSemanticField){
			element.setAttribute("style","background-color:lightgrey; color:white");
		}else{
			element.removeAttribute("style");
		}
		
	}
}

var terms = document.getElementsByClassName("term");
var i = 0 ;
var ctr = 0;
var array = [];
var semanticFieldLabel = {};
var semanticFieldInstance = {};
var semanticFieldStatistics = {};

//init semantic field label
semanticFieldLabel['Autorité'] = "<b>Autorités citées</b>";
semanticFieldLabel['Théâtre'] = "<b>Champ sémantique du théâtre</b>";
semanticFieldLabel['Sentiment_Positif'] = "<b>Champ sémantique des sentiments positifs</b>";
semanticFieldLabel['Sentiment_Négatif'] = "<b>Champ sémantique des sentiments négatifs</b>";
semanticFieldLabel['Classe_Sociale'] = "<b>Champ sémantique des classe sociales</b>";
semanticFieldLabel['Morale_Positive'] = "<b>Champ sémantique de la morale positive</b>";
semanticFieldLabel['Morale_Négative'] = "<b>Champ sémantique de la morale négative</b>";
semanticFieldLabel['Amour'] = "<b>Champ sémantique de l'amour</b>";
semanticFieldLabel['Religion'] = "<b>Champ sémantique religieux</b>";
semanticFieldLabel['Fête'] = "<b>Champ sémantique de la fête et de la musique</b>";
semanticFieldLabel['Guerre'] = "<b>Champ sémantique du combat</b>";
semanticFieldLabel['Origine'] = "<b>Champ sémantique de la nationalité</b>";
semanticFieldLabel['Juridique'] = "<b>Champ sémantique juridique</b>";
semanticFieldLabel['Argent'] = "<b>Champ sémantique de l'argent</b>";

//init semantic field instance
semanticFieldInstance['Autorité'] =  new Array();
semanticFieldInstance['Théâtre'] = new Array() ;
semanticFieldInstance['Sentiment_Positif'] = new Array();
semanticFieldInstance['Sentiment_Négatif'] = new Array();
semanticFieldInstance['Classe_Sociale'] = new Array();
semanticFieldInstance['Morale_Positive'] = new Array();
semanticFieldInstance['Morale_Négative'] = new Array();
semanticFieldInstance['Amour'] = new Array();
semanticFieldInstance['Religion'] = new Array();
semanticFieldInstance['Fête'] = new Array();
semanticFieldInstance['Guerre'] = new Array();
semanticFieldInstance['Origine'] = new Array();
semanticFieldInstance['Juridique'] = new Array();
semanticFieldInstance['Argent'] = new Array();

//init semantic field statistics
semanticFieldStatistics['Autorité'] =  0;
semanticFieldStatistics['Théâtre'] = 0 ;
semanticFieldStatistics['Sentiment_Positif'] = 0;
semanticFieldStatistics['Sentiment_Négatif'] = 0;
semanticFieldStatistics['Classe_Sociale'] = 0;
semanticFieldStatistics['Morale_Positive'] = 0;
semanticFieldStatistics['Morale_Négative'] = 0;
semanticFieldStatistics['Amour'] = 0;
semanticFieldStatistics['Religion'] = 0;
semanticFieldStatistics['Fête'] = 0;
semanticFieldStatistics['Guerre'] = 0;
semanticFieldStatistics['Origine'] = 0;
semanticFieldStatistics['Juridique'] = 0;
semanticFieldStatistics['Argent'] = 0;

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
   var count = semanticFieldStatistics[semanticField];
   semanticFieldStatistics[semanticField] = count + 1;
   
   //alert (liste.length);
   if(liste.contains(value) == false){
		liste[liste.length] = value;
		semanticFieldInstance[semanticField] = liste;
   }
	
  /* if(array.length==0 || array.contains(term)== false){
	array[ctr] = term;
	//alert("array[ctr] "+array[ctr]);
	ctr = ctr+1;
   }*/
      
}

//creating semantic word list
i = 0;
for(semanticField in semanticFieldLabel){
	var liste = semanticFieldInstance[semanticField];
	if(liste.length > 0){
	 var element = createElement("li","","<a href=\"#\" onclick=\"javascript:colorTextSemanticField(\'"+semanticField+"\'); return false;\">"+semanticFieldLabel[semanticField]+"</a>"+"<b>["+semanticFieldStatistics[semanticField]+" occurrences] :</b><br/>"+"["+convertArrayToString(liste, semanticField)+"]");
	 
	 array[i] = element;
	 i = i+1;
	}
	
}

var motCles = createElement("ul","",array);
var motClesListe = createElement("li","[class:  less]",["Annotations associées:",motCles]);
var treeClass = createElement("ul","[class:tree treejs]",motClesListe);
var divTags = createElement("div","[id:tags]",treeClass);
//alert(divTags.id);
var article = document.getElementById("article");
//alert(article.innerHTML);
article.insertBefore(divTags,article.childNodes[0]);
