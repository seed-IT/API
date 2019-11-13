

let tabData = {
    "jsonarray": [
        {"datetime": "29-10-2019 | 12:10", "temperature": 21.9, "humidity": 45.20},
        {"datetime": "29-10-2019 | 12:11", "temperature": 22.9, "humidity": 45.07},
        {"datetime": "29-10-2019 | 12:12", "temperature": 23.9, "humidity": 46.05},
        {"datetime": "29-10-2019 | 12:13", "temperature": 22.9, "humidity": 46.21},
        {"datetime": "29-10-2019 | 12:14", "temperature":  9.9, "humidity": 45.74},
        {"datetime": "29-10-2019 | 12:15", "temperature": 21.9, "humidity": 45.98},
        {"datetime": "29-10-2019 | 12:16", "temperature": 22.9, "humidity": 46.45},
        {"datetime": "29-10-2019 | 12:17", "temperature": 23.9, "humidity": 46.32},
        {"datetime": "29-10-2019 | 12:18", "temperature": 24.9, "humidity": 45.62},
        {"datetime": "29-10-2019 | 12:19", "temperature": 24.9, "humidity": 45.85},
        {"datetime": "29-10-2019 | 12:20", "temperature": 24.9, "humidity": 46.77},
        {"datetime": "29-10-2019 | 12:21", "temperature": 23.9, "humidity": 46.14},
        {"datetime": "29-10-2019 | 12:22", "temperature": 23.9, "humidity": 45.20},
        {"datetime": "29-10-2019 | 12:23", "temperature": 23.9, "humidity": 45.22},
        {"datetime": "29-10-2019 | 12:24", "temperature": 22.9, "humidity": 46.30},
        {"datetime": "29-10-2019 | 12:25", "temperature": 22.9, "humidity": 46.40}]
};

//tentative de changer de couleur lorsque la température dépasse un certain seuil
/*var myColors=[];
$.each(tabData, function( index,item ) {
    if(item<10){
        myColors[index].temperature="red";
    }else{
        myColors[index].temperature="green";
    }
});*/

//fonction permettant d'aller chercher nos données grâce à la librairie fetch()
/*
function getData(){
    const reponse = await fetch("adresse");
    const data = await response.text(); ??
}
*/


//variables a utiliser dans la config :
                        //LABELS
var labels = tabData.jsonarray.map(function(e) {
    return e.datetime;
});
//DONNEES TEMPERATURE
var dataT = tabData.jsonarray.map(function(e) {
    return e.temperature;
});
//DONNEES HUMIDITE
var dataH = tabData.jsonarray.map(function(e) {
    return e.humidity;
});

                        //CHART DATA
var chartData = {
    labels: labels,
    datasets: [{
        type: "line",
        label: 'Température',
        id: "y-axis-1",
        fill: false,
        borderColor : 'rgb(54, 179, 16)',   //vert
        //pointColor: "rgba(60, 199, 18)",
        backgroundColor: "rgba(255, 255, 255)",
        //backgroundColor: myColors,
        data: dataT,
        //borderWidth: 1
    }, {
        type: "line",
        label: 'Humidité',
        id: "y-axis-2",
        fill: false,
        borderColor : 'rgb(18, 112, 199)',   //bleu
        backgroundColor: "rgba(255, 255, 255)",
        data: dataH,
        //borderWidth: 1
    }]
};

                        //ChartJS config globale

var ctx = document.getElementById('myChart').getContext('2d');
    //configuration du graphe (options... pour la doc voir site : chartjs.org/docs/latest/
var config =
{
    type: 'line',
    data: chartData,
    options: {
        title: {
            display: true,
            fontSize: 20,
            text: "Graphique du PiMium"
        },
        tooltips: {
            mode: 'label'   //affiche les deux données au passage de la souris sur le point
        },
        responsive: true,
        scales: {
            yAxes: [{
                position: "left",
                id: "y-axis-1",
                fontStyle: "italic",
                ticks: {
                    beginAtZero: true
                }
            }, {
                position: "right",
                id: "y-axis-2",
                ticks: {
                    beginAtZero: true
                }
            }]
        }
    }
};
var myChart = new Chart(ctx, config);


//IF STATEMENTS

/*if(chartData.datasets[0].data < 10){
    chartData.datasets[0].backgroundColor = 'rgb(232, 21, 28)';
    chartData.update();
}else{
    chartData.datasets[0].backgroundColor = 'rgb(60, 199, 18)';
    chartData.update();
};*/


/*//Function to update the chart on changing the checkbox status
var updateChart = function(url, name, checked) {

    var index = -1;
    for (var i = 0; i < data.length; i++)
        if (data[i].name === name) {
            index = i;
            break;
        }

    if (checked) {
        if (index === -1) {
            $.getJSON(url, function(result) {
                data.push({
                    dataPoints: result,
                    name: name,
                    visible: true
                });
                chart.render();
            });
        } else
            data[index].visible = true;
    }
    else
        data[index].visible = false;
    chart.render();
};*/