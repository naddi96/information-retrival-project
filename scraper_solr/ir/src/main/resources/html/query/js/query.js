

	var forEach = function (collection, callback, scope) {
		if (Object.prototype.toString.call(collection) === '[object Object]') {
			for (var prop in collection) {
				if (Object.prototype.hasOwnProperty.call(collection, prop)) {
					callback.call(scope, collection[prop], prop, collection);
				}
			}
		} else {
			for (var i = 0, len = collection.length; i < len; i++) {
				callback.call(scope, collection[i], i, collection);
			}
		}
	};


var text = [];

function cerca(){

    var mat=document.getElementById("materia");
    var ann=document.getElementById("anno");
    var tip=document.getElementById("tipologia");
    var pro=document.getElementById("professore");
    var tes=document.getElementById("testo");

	//console.log(mat.value,ann.value,tip.value,pro.value,tes.value);

	var query= "http://localhost:8983/solr/corso_informatica/select?"

    if(pro.value != ""){
    	query = query + "fq=professore:" + pro.value + "&";
    }
    if(mat.value != ""){
    	query = query + "fq=materia:" + mat.value + "&";
    }
    if(ann.value != ""){
    	query = query + "fq=anno:" + ann.value + "&";
    }
    if(tip.value != ""){
    	query = query + "fq=tipologia:" + tip.value + "&";
    }
    if(tes.value != ""){
    	query = query + "fq=testo:" + tes.value + "&";
    }
    query = query + "q=*%3A*&rows=12&start=0";

    //console.log(query);

    var x1;

    var materia,anno,professore,tipologia,link,testo;



    var doc = document.getElementsByClassName("art");

    for (i = doc.length-1; i >=0; i--) {
    	doc[i].remove();

	}


    // http://localhost:8983/solr/bigboxstore/select?fq=anno%3A2006&fq=professore%3Aguala&fq=testo%3Aristrutturarich&q=*%3A*&rows=100&start=0
	axios.get(query, {
	headers: {
	  'Access-Control-Allow-Origin': '*',
	}})
  	.then((response) => {

  			//console.log(Math.ceil(response.data["response"]["numFound"]/10));


	    	forEach(response.data["response"]["docs"], function(value,index){

	    		text[index] = value["testo"];

	    		x1 = document.createElement("article");
	    		x1.setAttribute("class", "art");
	    		x1.setAttribute("id", index);
				materia = document.createElement("div");
				materia.setAttribute("class", "info");
				materia.innerHTML = value["materia"];
				anno = document.createElement("div");
				anno.setAttribute("class", "info");
				anno.innerHTML = value["anno"];
				professore = document.createElement("div");
				professore.setAttribute("class", "info");
				professore.innerHTML = value["professore"];
				tipologia = document.createElement("div");
				tipologia.setAttribute("class", "info");
				tipologia.innerHTML = value["tipologia"];
				link = document.createElement("div");
				link.setAttribute("class", "info2");

				link.innerHTML = "<marquee type='button' style='overflow: hidden; width:100%;' class='btn btn-info btn-lg' data-toggle='modal' data-target='#myModal' onclick='mostraTesto("+index+")'>"+value["link_pagina"]+"</marquee>";

				x1.appendChild(materia);
				x1.appendChild(anno);
				x1.appendChild(tipologia);
				x1.appendChild(professore);
				x1.appendChild(link);

				document.getElementById("cards").appendChild(x1);

	    	});

    	});

   axios.get("/query?professore=rossi", {
	headers: {
	  'Access-Control-Allow-Origin': '*',
	}})
  	.then((response) => {
			console.log("seconda chiamata ");
  			console.log(response.data);
	});





    }



	function mostraTesto(i){
		document.getElementsByClassName("modal-body")[0].innerHTML = text[i];

		link = document.getElementsByTagName("marquee")[i].innerHTML;

		document.getElementById("linkPag").setAttribute("href", link);
	}

