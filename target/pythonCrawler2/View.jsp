<%--
  Created by IntelliJ IDEA.
  User: Tefuir
  Date: 2022/5/13
  Time: 11:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>可视化图</title>
    <script src="js/jquery-3.0.0.min.js"></script>
    <script src="js/echarts.js"></script>
    <script src="js/echarts-wordcloud.js"></script>
    <link rel="stylesheet" type="text/css" href="https://www.layuicdn.com/layui/css/layui.css" />
    <style>
        #main{
            width: 100%;
            height: 1500px;
        }
        table.gridtable {
            font-family: verdana,arial,sans-serif;
            font-size:11px;
            color:#333333;
            border-width: 1px;
            border-color: #666666;
            border-collapse: collapse;
        }
        table.gridtable th {
            border-width: 1px;
            padding: 8px;
            border-style: solid;
            border-color: #666666;
            background-color: #dedede;
        }
        table.gridtable td {
            border-width: 1px;
            padding: 8px;
            border-style: solid;
            border-color: #666666;
            background-color: #ffffff;
        }
        input[type=button], input[type=reset] {
            background-color: #66BBFF;
            border: none;
            color: white;
            padding: 16px 32px;
            text-decoration: none;
            margin: 4px 2px;
            cursor: pointer;
        }
        td{width:200px;overflow:hidden}
    </style>
</head>
<body>
<form actoin="" id="shuzi2" method="post">
    <div align="center">
        <h3 style="margin-top: 50px">输入关键词的数量查看词云：</h3>
        <input type="text" name="shuzi" id="shuzi" style="margin-top: 50px;height: 30px;"><br>
<%--
        <input type="button" onclick="cloud()" value="确定" style="margin-top: 30px">
--%>
        <input type="button" onclick="cloud()" onclick="addBut()" value="确定" style="margin-top: 30px">
    </div>
</form>
<div class="layui-row">
    <div class="layui-col-xs6">
        <div class="grid-demo grid-demo-bg1">
            <div id="main" align="center" style="align-items: center;width:100%;height:300%;"></div>
        </div>
    </div>
    <div class="layui-col-xs6">
        <div class="grid-demo">
            <table id="biaoge"
                   style="border-collapse: collapse;
                       border-spacing: 0;
                       width: 600px;
                       height: 600px;
                       margin-left: 100px;">
            </table>
        </div>
    </div>
</div>
<div>

    <script type="text/javascript">

        var str="<table border='1'>" +
            "<tbody>" +
            "<tr>" +
            "<td width='35%'>热词</td>  " +
            "<td width='15%'>数量</td>" +
            "</tr>" +
            "<br>";
        var str1="</tbody>" +
            "</table>";

        var tab=document.getElementById("biaoge");

        function addTable(){
            var num=0;
            var tableHtml="";
            tableHtml +='' + ''
        }

        function cloud(){
            var dt;
            var hzb=new Array(0);
            var zzb=new Array(0);

            $.ajax({
                url : "cloud.action",
                async : true,
                type : "POST",
                data :$('#shuzi2').serialize(),
                dataType : "json",

                success : function(data) {
                    dt = data;
                    var mydata = new Array(0);
                    for (var i = 0; i < dt.length; i++) {
                        var d = {};
                        d["name"] = dt[i].name;
                        d["value"] = dt[i].value;
                        mydata.push(d);
                        hzb.push(dt[i].name);
                        zzb.push(dt[i].value);
                        if (dt[i]!=null) {
                            str = str + "<tr><td>" + dt[i].name + "</td>  " +
                                "<td>" + dt[i].value + "</td></tr><br>";
                        }
                    }
                    if(i==dt.length){
                        str=str+str1;
                    }
                    console.log(str);
                    //alert("1111");
                    tab.innerHTML = str;
                    str="<table border='1'>" +
                        "<tbody>" +
                        "<tr>" +
                        "<td width='35%'>热词</td>  " +
                        "<td width='15%'>数量</td>" +
                        "</tr>" +
                        "<br>";
                    var myChart = echarts3.init(document.getElementById('main'));
                    //设置点击效果
                    myChart.setOption({
                        title: {
                            text: ''
                        },
                        tooltip: {},
                        series: [{
                            type : 'wordCloud',  //类型为字符云
                            shape:'smooth',  //平滑
                            gridSize : 8, //网格尺寸
                            size : ['50%','50%'],
                            //sizeRange : [ 50, 100 ],
                            rotationRange : [-45, 0, 45, 90,60,16], //旋转范围
                            textStyle : {
                                normal : {
                                    fontFamily:'微软雅黑',
                                    color: function() {
                                        return 'rgb(' +
                                            Math.round(Math.random() * 255) +
                                            ', ' + Math.round(Math.random() * 255) +
                                            ', ' + Math.round(Math.random() * 255) + ')'
                                    }
                                },
                                emphasis : {
                                    shadowBlur : 5,  //阴影距离
                                    shadowColor : '#333'  //阴影颜色
                                }
                            },
                            left: 'center',
                            top: 'center',
                            right: 'center',
                            bottom: 'center',
                            width:'100%',
                            height:'100%',
                            data:mydata
                        }]
                    });
                },
                error : function() {
                    alert("请求失败");
                },
            });
        }

        function recitable(){
            var bigdiv=document.getElementById("FontScroll");
            var str="<table align='center'>";
            var j=0;
            for(i=0;i<alldata.length;i++){
                if(j==0)str=str+"<tr>";
                str=str+"<td class='iconfont' id='tabtr"+i+"'>"+(parseInt(i)+parseInt(1))+"."+alldata[i].name+"</td>";
                j++;
                if(j==3){str=str+"</tr>";j=0}

            }
            str=str+"</table>";
            var div=document.createElement("div");
            div.id="tablediv";
            div.innerHTML = str;
            bigdiv.appendChild(div);
        }

        function cleartable(){
            document.getElementById("tablediv").remove();
        }

    </script>
</div>
</body>
</html>
