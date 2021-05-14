// Set new default font family and font color to mimic Bootstrap's default styling
Chart.defaults.global.defaultFontFamily =
  '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = "#292b2c";
var nf = Intl.NumberFormat();
var monthYearArr = new Array();
var offProfits = new Array();
var onlProfits = new Array();
var nameBrands = [];
var brandProfits = [];
for (i = 0; i < barChartProfits.length; i++) {
  var res = barChartProfits[i].split("\t");
  monthYearArr.push("Tháng " + res[0] + "/" + res[1]);
  offProfits.push(res[2]);
  onlProfits.push(res[3]);
}

var sum = BigInt(0);
for (i = 0; i < pieChartProfits.length - 1; i++) {
  console.log(pieChartProfits[i]);
  var res = pieChartProfits[i].split("\t");
  console.log(res[4]);
  sum = sum + BigInt(res[4]);
  nameBrands.push(res[1]);
  brandProfits.push(res[4]);
}
nameBrands.push("Các hãng khác");
brandProfits.push(
  (sum - BigInt(pieChartProfits[pieChartProfits.length - 1])).toString()
);

$("#line-bar-chart-form").submit(function (e) {
  e.preventDefault();
  var chartType = $(this).find("[name='chart-type']")[0].value;
  var type = $(this).find("[name='type']")[0].value;
  var fromDate = $(this).find("[name='from-date']")[0].value;
  var toDate = $(this).find("[name='to-date']")[0].value;
  console.log(chartType);
  console.log(type);
  console.log(fromDate);
  console.log(toDate);
  $.ajax({
    type: "POST",
    url: "/admin/line-bar-chart",
    data: { type: type, fromdate: fromDate, todate: toDate },
    success: function (response) {
      alert(response);
    },
  });
});

// Bar Chart Example
var ctx = document.getElementById("myBarChart");
var myLineChart = new Chart(ctx, {
  type: "bar",
  data: {
    labels: monthYearArr,
    datasets: [
      {
        label: "Thanh toán trực tiếp",
        backgroundColor: "#25c2a0",
        borderColor: "#25c2a0",
        data: offProfits,
      },
      {
        label: "Thanh toán trực tuyến",
        backgroundColor: "#ff1a68",
        borderColor: "#ff1a68",
        data: onlProfits,
      },
    ],
  },

  options: {
    layout: {
      padding: {
        left: 0,
        right: 50,
        top: 0,
        bottom: 0,
      },
    },
    scales: {
      xAxes: [
        {
          maxBarThickness: 100,
          // time: {
          //     unit: 'month'
          // },
          gridLines: {
            offsetGridLines: true,
            display: false,
          },
          offset: true,
          ticks: {
            beginAtZero: true,
            // maxTicksLimit: 6
          },
        },
      ],
      yAxes: [
        {
          ticks: {
            beginAtZero: true,
            callback: function (value) {
              return nf.format(value);
            },
            // min: 0,
            // max: 15000,
            // maxTicksLimit: 5
          },
          gridLines: {
            offsetGridLines: true,
            display: true,
          },
        },
      ],
    },
    legend: {
      display: true,
      position: "top",
    },
    tooltips: {
      callbacks: {
        label: function (tooltipItem, chart) {
          var datasetLabel =
            chart.datasets[tooltipItem.datasetIndex].label || "";
          return (
            datasetLabel + ": " + number_format(tooltipItem.yLabel, 0) + "đ"
          );
        },
      },
    },
  },
});
var ctx = document.getElementById("myPieChart");
var myPieChart = new Chart(ctx, {
  type: "pie",
  data: {
    labels: nameBrands,
    datasets: [
      {
        data: brandProfits,
        // backgroundColor: ["#007bff", "#dc3545", "#ffc107", "#28a745"],
        backgroundColor: ["#007bff", "#dc3545"],
      },
    ],
  },
  options: {
    tooltips: {
      callbacks: {
        label: function (tooltipItem, chart) {
          var datasetLabel =
            chart.datasets[tooltipItem.datasetIndex].label || "";
          return datasetLabel + ": " + number_format(tooltipItem, 0) + "đ";
        },
      },
    },
  },
});
function number_format(number, decimals, dec_point, thousands_sep) {
  // *     example: number_format(1234.56, 2, ',', ' ');
  // *     return: '1 234,56'
  number = (number + "").replace(",", "").replace(" ", "");
  var n = !isFinite(+number) ? 0 : +number,
    prec = !isFinite(+decimals) ? 0 : Math.abs(decimals),
    sep = typeof thousands_sep === "undefined" ? "," : thousands_sep,
    dec = typeof dec_point === "undefined" ? "." : dec_point,
    s = "",
    toFixedFix = function (n, prec) {
      var k = Math.pow(10, prec);
      return "" + Math.round(n * k) / k;
    };
  // Fix for IE parseFloat(0.55).toFixed(0) = 0;
  s = (prec ? toFixedFix(n, prec) : "" + Math.round(n)).split(".");
  if (s[0].length > 3) {
    s[0] = s[0].replace(/\B(?=(?:\d{3})+(?!\d))/g, sep);
  }
  if ((s[1] || "").length < prec) {
    s[1] = s[1] || "";
    s[1] += new Array(prec - s[1].length + 1).join("0");
  }
  return s.join(dec);
}
