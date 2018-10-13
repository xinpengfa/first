<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" pageEncoding="UTF-8" %>

    <table id="tt_picture"></table>
    <script type="text/javascript">
        $(function(){


            $('#tt_picture').datagrid({

                onLoadSuccess: aa,
                toolbar:'#tb_picture',
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
                url:'${pageContext.request.contextPath}/picture/findAll',

                columns:[[
                    {field:'id',title:'标识编号',width:100,sortable:true,align:'center'},
                    {field:'img_path',title:'文件名',width:100,sortable:true,align:'center'},
                    {field:'description',title:'描述信息',width:100,align:'center',sortable:true},
                    {field:'status',title:'轮播图状态',width:100,align:'center',sortable:true,
                        formatter:function(value,row,index){
                            return "<h1>"+row.status+"</h1>"
                        }
                    },
                    {field:'createDate',title:'轮播图创建时间',width:100,sortable:true,align:'center'},
                    {field:'xiu',title:'操作',width:60,align:'center',
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
                        '<td rowspan=2 style="border:0"><img src="192.168.29.133' + rowData.img_path + '" style="height:50px;"></td>' +
                        '<td style="border:0">' +
                        '<p>Attribute: ' + rowData.description + '</p>' +
                        '<p>Status: ' + rowData.status + '</p>' +
                        '</td>' +
                        '<td>'+'<a id="btn222" href="${pageContext.request.contextPath}/picture/download?id='+rowData.id+'">下载</a>'+'</td>'+
                        '</tr></table>';
                }
            });

        });







        //用来打开保存用户的对话信息
        function openUserSaveDialog() {
            $("#userSaveDialog_picture").dialog({
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
                            $("#userSaveDialog_picture").dialog('close', true);
                        }
                    }
                ]
            });
        }

        //保存用户信息
        function saveUserInfo() {
            $("#userSaveForm_picture").form('submit', {
                url: "${pageContext.request.contextPath}/picture/add",
                success: function (result) {
                    //关闭保存对话框
                    $("#userSaveDialog_picture").dialog('close', true);
                    //1.转为js对象
                    var jsObject = $.parseJSON(result);

                    //2.展示信息
                    $.messager.show({title: '提示', msg: jsObject.message});
                    if (jsObject.success) {
                        $("#userSaveDialog_picture").dialog('close', true);
                        //2.刷新datagrid
                        $("#tt_picture").datagrid('reload');
                    }
                }
            })
        }




        //打开编辑用户信息的对话框
        function openUserEditDialog(row){
            //直接根据页面中的行修改
            $("#userEditDialog").dialog({
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
            $("#userEditForm_picture").form('submit', {
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

<div id="tb_picture">
    <a href="#" class="easyui-linkbutton" data-options="onClick:openUserSaveDialog,iconCls:'icon-add',plain:true">添加</a>
</div>
<div id="userSaveDialog_picture" data-options="width:600,height:400,title:'保存用户信息'"></div>
<div id="userEditDialog_picture" data-options="width:600,height:400,title:'修改用户信息'"></div>





