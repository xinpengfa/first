<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<script type="text/javascript">

    $("#cc").combotree({
        animate: true,
        lines: true,
        multiple: true,
        data: [{
            text: '自定义导出设置',
            // state: 'closed',
            children: [{
                text: '编号',
                iconCls: "icon-add",
                id: "Id"
            }, {
                text: '姓名',
                iconCls: "icon-add",
                id: "Name"
            }, {
                text: '昵称',
                iconCls: "icon-add",
                id: "NikeName",
            }, {
                text: '性别',
                iconCls: "icon-add",
                id: "Sex"
            }, {
                text: '手机',
                iconCls: "icon-add",
                id: "Phone"
            }, {
                text: '城市',
                iconCls: "icon-add",
                id: "City"
            },{
                text: '签名',
                iconCls: "icon-add",
                id: "Sign"
            },{
                text: '注册时间',
                iconCls: "icon-add",
                id: "CreateDate"
            },{
                text: '状态',
                iconCls: "icon-add",
                id: "Status"
            }]
        }],
        checkbox: true,
    });
	$("#custom_btn").linkbutton({
		onClick:function () {
            var titles = $("#cc").combotree("getText");
            var columns = $("#cc").combotree("getValues");
            console.log(titles + "|" + columns);
            $("#ff").form("submit", {
                url: "${pageContext.request.contextPath}/user/customExport",
                onSubmit: function (param) { // 提交表单时，提交额外的请求参数：
                    param.titles = titles;
                    param.columns = columns;
                }
            });
        }
	})
</script>
<form id="ff" method="post">
	<div style="text-align: center;margin-top: 30px">
		自定义导出设置：<input id="cc" name="dept" value="" data-options="required:true">
	</div>
</form>
<div style="text-align: center;margin-top: 30px">
	<a id="custom_btn" class="easyui-linkbutton" data-options="iconCls:'icon-cdr'">导出</a>
</div>


