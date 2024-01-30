/*<![CDATA[*/
var chartData = /*[[${chartData}]]*/ null;

// 차트 생성 및 설정
var ctx = document.getElementById('priceChart').getContext('2d');
var myChart = new Chart(ctx, {
    type: 'line',
    data: {
        labels: chartData.labels,
        datasets: [{
            label: 'Close Price',
            data: chartData.closePrices,
            borderColor: 'rgba(75, 192, 192, 1)',
            borderWidth: 1,
            fill: false
        }]
    },
    options: {
        scales: {
            x: {
                type: 'time',
                time: {
                    unit: 'day'
                }
            },
            y: {
                beginAtZero: false
            }
        }
    }
});
/*]]>*/