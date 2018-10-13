<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>wangEditor 菜单和编辑器区域分离</title>
    <style type="text/css">
        .toolbar {
            border: 1px solid #ccc;
        }
        .text {
            border: 1px solid #ccc;
            height: 400px;
        }
    </style>
</head>
<body>


<div style="background-color: #e5cf9a;margin-top: 0px;margin-bottom: 0px" >
    <form id="articleForm" method="post">
        <br>
        <span style="margin-left: 30px">文章标题 : </span><input class="easyui-validatebox" type="text" name="title" data-options="required:true" />
        <br>
        <br>
        <br>
        <span style="margin-left: 30px">文章作者 : </span><input id="guruCombobox" name="guru.id" value="">
        <br>
        <br>
        <br>
        <span style="margin-left: 30px">文章状态 : </span><input class="easyui-switchbutton" data-options="value:'上架',onText:'上架',offText:'下架'" name="status" >
    </form>
        <br>
        <br>
        <br>
        <span style="margin-left: 30px">文章内容 : </span>
        <br>
        <br>
        <div style="margin-left: 30px;margin-right: 30px;background-color: white">

                <div id="div1" class="toolbar" >
                </div>
                <div style="padding: 5px 0; background-color: white">-------------------------------------------</div>
                <div id="div2" class="text" style="min-height: 500px;background-color: white" > <!--可使用`` min-height 实现编辑区域自动增加高度-->
                    <p>请输入内容</p>
                </div>
        </div>
        <br>
        <span style="margin-left: 30px"/><div id="resetButton" class="easyui-linkbutton" onclick="resetArticle()" data-options="iconCls:'icon-2013040601125064_easyicon_net_16'">重置内容</div>
        &nbsp;&nbsp;
        <div id="createArticleButton" class="easyui-linkbutton" onclick="saveArticle()" data-options="iconCls:'icon-comment_add'">创建文章</div>


</div>

<%--<form id="ff" method="post">
    <div>
        <label for="name">Name:</label>
        <input class="easyui-validatebox" type="text" name="name" data-options="required:true" />
    </div>
    <div>
        <label for="email">Email:</label>
        <input class="easyui-validatebox" type="text" name="email" data-options="validType:'email'" />
    </div>
    ...
</form>--%>



<script type="text/javascript" src="${pageContext.request.contextPath}/js/wangEditor.min.js"></script>
<script type="text/javascript">
    // 创建Editor
    var E = window.wangEditor
    var editor = new E('#div1', '#div2')  // 两个参数也可以传入 elem 对象，class 选择器

    editor .customConfig.uploadImgServer = '${pageContext.request.contextPath}/article/upload'  // 上传图片到服务器
    // 自定义 fileName
    editor.customConfig.uploadFileName = 'keyName';
    // 将图片大小限制为 3M
    editor.customConfig.uploadImgMaxSize = 3 * 1024 * 1024;
    // 限制一次最多上传 5 张图片 ; 默认上传10000张, 即不限制
    editor.customConfig.uploadImgMaxLength = 5;
    // 创建一个编辑器  --  WangEditor
    editor.create()

    // 页面加载完成, 读取上师的相关数据
    $(function () {
        $('#guruCombobox').combobox({
            url:'${pageContext.request.contextPath}/guru/queryAll',
            //值属性,相当于option标签的value属性
            valueField:'id',
            //相当于option标签的标签体内容
            textField:'name'
        });
    });

    // 保存文章的方法
    function saveArticle() {
        // 获取编辑区域中的html内容
        var text = editor.txt.html();
        console.log(text);
        $("#articleForm").form('submit', {
            url: '${pageContext.request.contextPath}/article/add',
            onSubmit: function (param) { // 提交表单时，提交额外的请求参数：
                // 后台接受数据时, controller对应的参数是 : text(或接受的对象中有text属性)
                param.text = text;
            },
            success: function (result) {
                // 1. 关闭指定的选项卡 close
                $("#tabs").tabs('close', '创建文章');
                // 2. 注意: result是一个json格式的字符串, 需要转为js对象
                var jsObject = $.parseJSON(result);
                console.log(jsObject);
                // 3.展示信息
                $.messager.show({title: '提示', msg: jsObject.message});
            }
        });
    }
    // 重置文章内容, 清空form表单及 wangEditor
    function resetArticle() {
        alert("testReset()");
        $("#articleForm").form('reset');
        editor.txt.clear();

    }
</script>
</body>
</html>









