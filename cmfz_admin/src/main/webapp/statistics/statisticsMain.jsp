<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; UTF-8"%>
<!-- 引入 echarts.js -->
<script type="text/javascript" src="${pageContext.request.contextPath }/js/echarts.min.js"></script>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('statisticsMain'));

    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '持名法州App活跃用户'
        },
        tooltip: {},
        legend: {
            data:['用户数量']
        },
        xAxis: {
            data: []
        },
        yAxis: {},
        series: [{
            name: '数量',
            type: 'bar',
            data: []
        }]
    };

    myChart.setOption(option);

    //异步加载统计信息
    $.post("${pageContext.request.contextPath }/statistics/activeUser",function(data){
        console.log(data);
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption({
            xAxis: {
                data: data.x
            },
            series: [{
                // 根据名字对应到相应的系列
                name: '活跃用户',
                data: data.y
            }]
        });
    },"json");
</script>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="statisticsMain" style="width: 600px;height: 400px;margin-left: 25%;margin-top: 5%"></div>

<%--
myChart.setOption({
     series : [
         {
             name: '访问来源',
             type: 'pie',
             radius: '55%',
             data:[
                 {value:235, name:'视频广告'},
                 {value:274, name:'联盟广告'},
                 {value:310, name:'邮件营销'},
                 {value:335, name:'直接访问'},
                 {value:600, name:'搜索引擎'}
             ]
         }
     ]
})
--%>
