<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>持名法州主页</title>
<link rel="stylesheet" type="text/css" href="../themes/default/easyui.css">   
<link rel="stylesheet" type="text/css" href="../themes/IconExtension.css">   
<script type="text/javascript" src="../js/jquery.min.js"></script>   
<script type="text/javascript" src="../js/jquery.easyui.min.js"></script>  
<script type="text/javascript" src="../js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/datagrid-detailview.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/wangEditor.min.js"></script>
<script type="text/javascript">
    $(function () {

        //发送ajax请求查询菜单数据
        $.post("${pageContext.request.contextPath}/menu/findAll",function (result) {
            console.log(result);

            //遍历菜单数据
            $.each(result,function (idx,menu) {
                //遍历二级孩子菜单
                var content = "<div style='text-align: center;'>";
                $.each(menu.children,function(idx,child){
                    console.log(child);
                    var json = JSON.stringify(child);
                    content += "<div onclick='addTabs("+json+")' style='border:1px #95B8E7 solid;width:90%;margin:5px 0px 5px 0px;' class='easyui-linkbutton' " +
                        "data-options=\"plain:true,iconCls:'"+ child.icon +"'\">"+child.name+"</div><br/>";
                });
                content +="</div>"

                //渲染一级菜单
                $("#aa").accordion('add',{
                    title:menu.name,
                    iconCls:menu.icon,
                    content:content,
                });
            });
        },"JSON");

    });
    function addTabs(child){
        if(!$("#tabs").tabs('exists',child.name)){
            $("#tabs").tabs('add',{
                title:child.name,
                iconCls:child.icon,
                closable:true,
                href:"${pageContext.request.contextPath}"+child.href,
                fit:true,

            });
        }else{
            $("#tabs").tabs('select',child.name);
        }
    }

</script>

</head>
<body class="easyui-layout">   
    <div data-options="region:'north',split:true" style="height:60px;background-color:  #5C160C">
    	<div style="font-size: 24px;color: #FAF7F7;font-family: 楷体;font-weight: 900;width: 500px;float:left;padding-left: 20px;padding-top: 10px" >持名法州后台管理系统</div>
    	<div style="font-size: 16px;color: #FAF7F7;font-family: 楷体;width: 300px;float:right;padding-top:15px">欢迎您:xxxxx &nbsp;<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">修改密码</a>&nbsp;&nbsp;<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-01'">退出系统</a></div>
    </div>   
    <div data-options="region:'south',split:true" style="height: 40px;background: #5C160C">
    	<div style="text-align: center;font-size:15px; color: #FAF7F7;font-family: 楷体" >&copy;百知教育 gaozhy@zparkhr.com.cn</div>
    </div>   
       
    <div data-options="region:'west',title:'导航菜单',split:true" style="width:220px;">
    	<div id="aa" class="easyui-accordion" data-options="fit:true">
    		
		</div>  
    </div>   
    <div data-options="region:'center'" style="background-color: #3d3d3d">
    	<div id="tabs" class="easyui-tabs" data-options="fit:true,narrow:true,pill:true" >
		    <div title="主页" data-options="iconCls:'icon-neighbourhood',"  style="background-image:url(image/shouye.jpg);background-repeat: no-repeat;background-size:100% 100%;"></div>
		</div>  
    </div>

</body> 
</html>