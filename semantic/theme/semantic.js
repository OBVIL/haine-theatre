Array.prototype.contains = function(obj) {
    var i = this.length;
    while (i--) {
        if (this[i] == obj) {
            return true;
        }
    }
    return false;
}

function createElement(element,attribute,inner){if(typeof(element) === "undefined"){return false;}if(typeof(attribute) === "undefined"){attribute = "";}if(typeof(inner) === "undefined"){inner = "";}var el = document.createElement(element);if(attribute.length > 1 && attribute[0] == "[" && attribute[attribute.length-1] == "]"){var attr = attribute.split("][");attr[0] = attr[0].substr(1);attr[attr.length-1] = attr[attr.length-1].substr(0,attr[attr.length-1].length-1);for(var k = 0, len = attr.length; k < len; k++){var el_attr,el_attr_val="",ind=attr[k].indexOf(":");if(ind > 0){el_attr = attr[k].substr(0,ind);el_attr_val = attr[k].substr(ind+1);}else{el_attr = attr[k].substr(0);}el.setAttribute(el_attr,el_attr_val);}}if(Array.isArray(inner)){for(var k = 0;k < inner.length;k++){if(inner[k].tagName){el.appendChild(inner[k]);}else{el.appendChild(document.createTextNode(inner[k]));}}}else{if(inner.tagName){el.appendChild(inner);}else{el.innerHTML = inner;}}return el;}

function convertArrayToString(array){
var text = "";
var i;

for(i=0; i<array.length;i++){
	if(i < array.length - 1){
		text = text + array[i] +", ";
	}else{
		text = text + array[i];
	}
}
return text;
}

var terms = document.getElementsByClassName("term");
var i = 0 ;
var ctr = 0;
var array = [];
var semanticFieldLabel = {};
var semanticFieldInstance = {};

//init semantic field label
semanticFieldLabel['Autorité'] = "<b>Autorités citées:</b><br/>";
semanticFieldLabel['Théâtre'] = "<b>Champ sémantique du théâtre:</b><br/>";
semanticFieldLabel['Sentiment_Positif'] = "<b>Champ sémantique des sentiments positifs:</b><br/>";
semanticFieldLabel['Sentiment_Négatif'] = "<b>Champ sémantique des sentiments négatifs:</b><br/>";
semanticFieldLabel['Classe_Sociale'] = "<b>Champ sémantique des classe sociales:</b><br/>";
semanticFieldLabel['Morale_Positive'] = "<b>Champ sémantique de la morale positive:</b><br/>";
semanticFieldLabel['Morale_Négative'] = "<b>Champ sémantique de la morale négative:</b><br/>";
semanticFieldLabel['Amour'] = "<b>Champ sémantique de l'amour:</b><br/>";
semanticFieldLabel['Religion'] = "<b>Champ sémantique religieux:</b><br/>";
semanticFieldLabel['Fête'] = "<b>Champ sémantique de la fête et de la musique:</b><br/>";
semanticFieldLabel['Guerre'] = "<b>Champ sémantique du combat:</b><br/>";
semanticFieldLabel['Origine'] = "<b>Champ sémantique de la nationalité:</b><br/>";
semanticFieldLabel['Juridique'] = "<b>Champ sémantique juridique:</b><br/>";
semanticFieldLabel['Argent'] = "<b>Champ sémantique de l'argent:</b><br/>";

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

for(i = 0; i < terms.length; i++)
{
   var element = terms[i];
   var className = element.className.replace("term ","");
   var semanticField = className.split(" ")[0];
   var value = className.split(" ")[1];
   value = value.replace("_"," ");
   //alert("semanticField "+semanticField);
   //alert("value "+value);
   
   var liste = semanticFieldInstance[semanticField];
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
	 var element = createElement("li","",semanticFieldLabel[semanticField]+"["+convertArrayToString(liste)+"]");
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

