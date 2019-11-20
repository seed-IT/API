const graphLabels = [];
const graphDataT = [];
const graphDataH = [];
const myColors = [];

//PARSE THE JSON RESPONSE
monGraphe();

async function monGraphe() {
    await getData();
    changeColorIf();
    var chartData = {
        labels: graphLabels[0],
        datasets: [{
            type: "line",
            label: 'Temperature',
            id: "y-axis-1",
            fill: false,
            borderColor: 'rgb(54, 179, 16)',   //vert
            backgroundColor: myColors, //"rgba(255, 255, 255)",
            data: graphDataT[0],
            pointRadius: 5, //taille du point
            pointHoverRadius: 4, //lorsqu'on passe la souris dessus
            //borderWidth: 1
        }, {
            type: "line",
            label: 'Humidite',
            id: "y-axis-2",
            fill: false,
            borderColor: 'rgb(18, 112, 199)',   //bleu
            backgroundColor: "rgba(255, 255, 255)",
            data: graphDataH[0],
            pointRadius: 5,
            pointHoverRadius: 4,
            //borderWidth: 1
        }]
    };

    //ChartJS config globale

    var ctx = document.getElementById('myChart').getContext('2d');
    //configuration du graphe (options). Doc : chartjs.org/docs/latest/
    var config =
        {
            type: 'line',
            data: chartData,
            options: {  //possibilite d'ajouter les ° ou % (voir video)
                title: {
                    display: true,
                    fontSize: 20,
                    text: "Graphique du PiMium"
                },
                tooltips: {
                    mode: 'label'   //affiche les deux donnees au passage de la souris sur le point
                },
                responsive: true,
                scales: {
                    yAxes: [{
                        position: "left",
                        id: "y-axis-1",
                        ticks: {
                            beginAtZero: true
                        }
                    /*}, {
                        position: "right",
                        id: "y-axis-2",
                        ticks: {
                            beginAtZero: true
                        }*/
                    }]
                }
            }
        };

    var myChart = new Chart(ctx, config);


}


async function getData() {
    const res = await fetch('http://seed-it.eu:4000/sensor');
    const data = await res.json();

    const labelsX = data.map(function(e) {
        return e.datetime;
    });
    graphLabels.push(labelsX);

    const dataT = data.map(function(e) {
        return e.temperature;
    });
    graphDataT.push(dataT);

    const dataH = data.map(function(e) {
        return e.humidity;
    });
    graphDataH.push(dataH);
//console.log("Labels :" + graphLabels, "Temp :" + graphDataT, "Humidité :" + graphDataH);
}

//change de couleur lorsque la temperature depasse un certain seuil
function changeColorIf() {
    $.each(graphDataT[0], function (index) {
        //myColors.push(graphDataT[0]);
        if (graphDataT[0][index] > 22.4) { // pour eviter la germination des graines 10 = ideal
            myColors[index] = 'rgb(255, 0, 0)';
            //console.log("rouge");
        } else {
            myColors[index] = 'rgb(54, 179, 16)';
            //console.log("green");
        }
    });
}