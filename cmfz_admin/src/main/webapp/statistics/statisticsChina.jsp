<%@page pageEncoding="UTF-8" contentType="text/html; UTF-8" isELIgnored="false"%>
<!-- 引入 echarts.js -->
<script src="${pageContext.request.contextPath}/js/echarts.min.js"></script>
<script src="${pageContext.request.contextPath}/js/china.js"></script>
<script type="text/javascript">
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('statisticsChina'));
        option = {
            title: {
                text: '持名法州APP用户分布图',
                subtext: '2017年6月15日 最新数据',
                left: 'center'
            },
            tooltip: {
                trigger: 'item'
            },
            // 说明
            legend: {
                orient: 'vertical',
                left: 'left',
                data: ['男', '女']
            },
            visualMap: {
                min: 0,
                max: 2500,
                left: 'left',
                top: 'bottom',
                text: ['高', '低'],           // 文本，默认为数值文本
                calculable: true
            },
            // 工具箱
            toolbox: {
                show: true,
                orient: 'vertical',
                left: 'right',
                top: 'center',
                feature: {
                    dataView: {readOnly: false},
                    restore: {},
                    saveAsImage: {}
                }
            },
            series: [
                {
                    name: '男',
                    type: 'map',
                    mapType: 'china',
                    roam: false,
                    label: {
                        normal: {
                            show: true
                        },
                        emphasis: {
                            show: true
                        }
                    },
                    data: []
                },
                {
                    name: '女',
                    type: 'map',
                    mapType: 'china',
                    label: {
                        normal: {
                            show: true
                        },
                        emphasis: {
                            show: true
                        }
                    },
                    data: []
                }
            ]
        };
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);

    $(function () {
        $.post("${pageContext.request.contextPath}/statistics/distribution1", function (data) {
            console.log(data);
            myChart.setOption({
                series: [{
                    // 根据名字对应到相应的系列
                    name: '男',
                    data: data
                }]
            });
        }, "json");

        $.post("${pageContext.request.contextPath}/statistics/distribution2", function (data) {
            console.log(data);
            myChart.setOption({
                series: [{
                    // 根据名字对应到相应的系列
                    name: '女',
                    data: data
                }]
            });
        }, "json");
    });
</script>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="statisticsChina" style="width: 600px;height: 400px;margin-left: 25%;margin-top: 5%"></div>

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