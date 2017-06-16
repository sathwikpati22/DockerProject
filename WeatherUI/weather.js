    $(document).ready(function(){


      //Forecast call from Local API
      $("#execute1").click(function() {

            //Date validation
            if ($("#datepicker").val() == ""){
              alert("Please select a Date");
            }
             
            var datapts = [];  
            
            var apiURI = "http://sample-env-1.xfv2hfrwma.us-east-2.elasticbeanstalk.com/forecast/" + $("#datepicker").val();
            $.ajax({
                url: apiURI,
                type: "GET",
                dataType: "json"
            }).then(function(data) {
              var len = 0;
              maxtemp = data[0].TMAX;
              for(var i =0 ; i<5; i++) {
                  tmp = {
                    'label' : data[i].DATE,
                    'y' : [Math.ceil(data[i+1].TMIN),Math.ceil(data[i+1].TMAX)],
                  };
                  datapts.push(tmp);
              }
              var chart = new CanvasJS.Chart("chartContainer",{            
              title:{
                text: "Forecast from Local API",              
              },
              axisY: {
                includeZero: false,
                suffix: "°C",
                gridThickness: 0.2
              },
              toolTip:{
                shared: true,
                content: "{name} </br> <strong>Temperature: </strong> </br> Min: {y[0]}°C, Max: {y[1]}°C",
              },
              data: [
              {
                type: "rangeSplineArea",
                fillOpacity: 0,
                color: "#91AAB1",
                indexLabelFormatter: formatter,
                dataPoints:  datapts
              
              }]
            });
            chart.render();

            function formatter(e) {
              if(e.index === 0 && e.dataPoint.x === 0) {
                return " Low " + e.dataPoint.y[e.index];

              }
              if(e.index == 1 && e.dataPoint.x === 0) {
                return " High " + e.dataPoint.y[e.index];
              }
              else{
                return e.dataPoint.y[e.index];
              }
            } 
          });
               
        });

            //Forecast call from external API(BONUS)
      $("#execute1").click(function() {

        var datapts = [];
        var chart = new CanvasJS.Chart("chartContainer2",{
          title:{
            text: "Forecast from External API(Bonus)",
          },
          axisY: {
            includeZero: false,
            suffix: "°C",
            gridThickness: 0.2
          },
          toolTip:{
            shared: true,
            content: "{name} </br> <strong>Temperature: </strong> </br> Min: {y[0]}°C, Max: {y[1]}°C",
          },
          data: [
            {
              type: "rangeSplineArea",
              fillOpacity: 0,
              color: "#91AAB1",
              indexLabelFormatter: formatter,
              dataPoints:  datapts
            }]
        });

        // var dr = false;
        var date = $("#datepicker").val();
        //Converting the value to yyyy.mm.dd format
        var df = date.substring(0,4) + '.' +date.substring(4,6)+'.'+date.substring(6,8);
        for (var i = 0 ; i < 5; i++) {
          var dat2e = new Date(df);
          //incrementing the date
          dat2e.setDate(dat2e.getDate() + i);
          //converting into unix time
          var dat2 = new Date(dat2e).getTime() / 1000;
          // calling the external api
          var extURI = "https://api.darksky.net/forecast/96f76c6edaa823bab12ee664e6f53187/39.9431,-84.6120,"+dat2+"?exclude=currently,hourly,minutely,flags";
          // var self = this;
          $.ajax({
            url: extURI,
            type: "GET",
            dataType: "jsonp",
          }).success(function(data) {
            //reading the Min temp and Max Temp
            var idata = data.daily.data;
            var a = new Date(idata[0].time*1000);
            var fmDate = a.getFullYear() +""+ (("0" + (a.getMonth() + 1)).slice(-2)) +""+(("0" + (a.getDate())).slice(-2));
            tmp = {
              'label' : fmDate,
              'y'  : [Math.ceil(idata[0].temperatureMin),Math.ceil(idata[0].temperatureMax)],
            };
            datapts.push(tmp);
            if(datapts.length == 5){
              function predicateBy(prop){
               return function(a,b){
                  if( a[prop] > b[prop]){
                      return 1;
                  }else if( a[prop] < b[prop] ){
                      return -1;
                  }
                  return 0;
                 }
              }
              datapts.sort(predicateBy("label")); 
              chart.render();
            }
          });
        }

        function formatter(e) {
          if(e.index === 0 && e.dataPoint.x === 0) {
            return " Low " + e.dataPoint.y[e.index];
          }
          if(e.index == 1 && e.dataPoint.x === 0) {
            return " High " + e.dataPoint.y[e.index];
          }
          else{
            return e.dataPoint.y[e.index];
          }
        }

      });


    });
