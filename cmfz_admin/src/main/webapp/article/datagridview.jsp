<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" pageEncoding="UTF-8" %>

    <table id="tt"></table>
    <script type="text/javascript">
        $(function(){
            $('#tt').datagrid({
                onLoadSuccess: aa,
                toolbar:'#tb',
                pagination:true,//分页工具栏
                pagePosition:'bottom',
                pageNumber:1,
                pageSize:3,//注意这个数字要求必须是pageList中子元素
                pageList:[3,6,10,20,50],
                fit:true,
                title:'DataGrid - DetailView',
                width:500,
                height:250,
                remoteSort:false,
                singleSelect:true,
                nowrap:false,
                fitColumns:true,
                url:'${pageContext.request.contextPath}/user/findByPage',

                columns:[[
                    {field:'id',title:'id',width:60,sortable:true,align:'center'},
                    {field:'name',title:'用户名',width:60,sortable:true,align:'center'},
                    {field:'nikeName',title:'昵称',width:60,align:'center',sortable:true},
                    {field:'sex',title:'性别',width:60,align:'center',sortable:true},
                    {field:'phone',title:'手机',width:60,align:'center',sortable:true},
                    {field:'sign',title:'签名',width:60,align:'center',sortable:true},
                    {field:'createDate',title:'创建时间',width:60,align:'center',sortable:true},
                    {field:'status',title:'用户状态',width:60,align:'center',sortable:true,
                        formatter:function(value,row,index){
                            return "<h1>"+row.status+"</h1>"
                        }
                    },
                    {field:'caozuo',title:'操作',width:60,align:'center',
                        formatter:function(value,row,index){
                            //json---->js对象 eval $.parseJSON
                            //js对象---json字符串   JSON.stringify(js对象)
                            var message = JSON.stringify(row);
                            //console.log(message);
                            return "<a href='javascript:;' class='edit' onclick='openUserEditDialog("+message+")' data-options=\"iconCls:'icon-edit',plain:true\">修改</a>";
                        }
                    }
                ]],
                view: detailview,
                detailFormatter: function(rowIndex, rowData){
                    return '<table><tr>' +
                        '<td rowspan=2 style="border:0"><img src="${pageContext.request.contextPath}/picture/' + rowData.img_path + '" style="height:50px;"></td>' +
                        '<td style="border:0">' +
                        '<p>Attribute: ' + rowData.description + '</p>' +
                        '<p>Status: ' + rowData.status + '</p>' +
                        '</td>' +
                        '<td>'+'<a id="btn222" href="${pageContext.request.contextPath}/picture/download?id='+rowData.id+'">下载</a>'+'</td>'+
                        '</tr></table>';
                }
            });



        });

        function opencustomExportUserDialog(){
            $("#customExportUserDialog").dialog({

                href: '${pageContext.request.contextPath}/user/customexport.jsp',
                buttons: [
                    {
                        iconCls: 'icon-save',
                        text: '保存',
                        handler: saveUserInfo,
                    },
                    {

                        iconCls: 'icon-cancel',
                        text: '取消',
                        handler: function () {
                            $("#userSaveDialog").dialog('close', true);
                        }
                    }
                ]
            });
        }





        //用来打开保存用户的对话信息
        function openUserSaveDialog() {
            $("#userSaveDialog").dialog({
                href: '${pageContext.request.contextPath}/main/save.jsp',
                buttons: [
                    {
                        iconCls: 'icon-save',
                        text: '保存',
                        handler: saveUserInfo,
                    },
                    {
                        iconCls: 'icon-cancel',
                        text: '取消',
                        handler: function () {
                            $("#userSaveDialog").dialog('close', true);
                        }
                    }
                ]
            });
        }

        //保存用户信息
        function saveUserInfo() {
            $("#userSaveForm").form('submit', {
                url: "${pageContext.request.contextPath}/picture/add",
                success: function (result) {
                    //关闭保存对话框
                    $("#userSaveDialog").dialog('close', true);
                    //1.转为js对象
                    var jsObject = $.parseJSON(result);

                    //2.展示信息
                    $.messager.show({title: '提示', msg: jsObject.message});
                    if (jsObject.success) {
                        $("#userSaveDialog").dialog('close', true);
                        //2.刷新datagrid
                        $("#tt").datagrid('reload');
                    }
                }
            })
        }




        //打开编辑用户信息的对话框

        function openExportUserDialog(row){
            //直接根据页面中的行修改
            $("#ExportUserDialog").dialog({
                buttons: [{
                    text: '保存',
                    iconCls: 'icon-save',
                    handler: saveEditUserInfo,
                }, {
                    text: '关闭',
                    iconCls: 'icon-cancel',
                    handler: function () {
                        $("#userEditDialog").dialog('close', true);
                    }
                }],
                onLoad:function () {
                    $("#userEditForm").form('load',row);
                }
            });
            $("#userEditDialog").dialog({
                href:'${pageContext.request.contextPath}/main/edit.jsp?id='+row.id
            });

        }

        //保存编辑的用户信息
        function saveEditUserInfo() {
            $("#userEditForm").form('submit', {
                url: '${pageContext.request.contextPath}/picture/update',
                success: function (result) {//form表单获取的结果为json格式字符串  使用时需要转为js对象
                    //1.转为js对象 $.parseJSON(result);
                    console.log(result);
                    var parseJSON = $.parseJSON(result);
                    //2.提示消息
                    $.messager.show({title: "提示", msg: parseJSON.message, width: 300, height: 200});
                    //3.关闭对话框
                    $("#userEditDialog").dialog('close', true);
                    //4.刷新datagrid
                    if (parseJSON.success) {
                        $("#tt").datagrid('reload');
                    }
                }
            });
        }

        //用来做数据渲染
        function aa(data) {
            $('#btn222').linkbutton({
                iconCls: 'icon-add'
            });
            $(".del").linkbutton();
            $(".edit").linkbutton();
            $(".save").linkbutton();

        }


    </script>

<div id="tb">
    <a href="#" class="easyui-linkbutton" data-options="onClick:openUserSaveDialog,iconCls:'icon-add',plain:true">添加</a>
    <a href="#" class="easyui-linkbutton" data-options="onClick:openUserSaveDialog,iconCls:'icon-add',plain:true">导入</a>
    <a href="#" class="easyui-linkbutton" data-options="onClick:openExportUserDialog,iconCls:'icon-add',plain:true">全部导出</a>
    <a href="#" class="easyui-linkbutton" data-options="onClick:opencustomExportUserDialog,iconCls:'icon-add',plain:true">自选导出</a>
</div>
<div id="userSaveDialog" data-options="width:600,height:400,title:'保存用户信息'"></div>
<div id="ExportUserDialog" data-options="width:600,height:400,title:'修改用户信息'"></div>
<div id="customExportUserDialog" data-options="width:600,height:400,title:'自选导出'">

</div>
<div style="text-align: center;margin-top: 30px">
    <a id="e_btn" class="easyui-linkbutton" data-options="iconCls:'icon-cdr'">导出</a>
</div>






