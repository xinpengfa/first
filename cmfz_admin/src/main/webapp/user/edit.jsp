<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<form id="userEditForm" class="easyui-form" method="post">

    id: <input readonly="readonly" type="text" name="id"  class="easyui-textbox" data-options="required:true"/><br>
    fileName: <input type="text" name="img_path"  class="easyui-textbox" data-options="required:true"/><br>
    description: <input type="text" name="description"  class="easyui-textbox" data-options="required:true"/><br>
    status: <input type="text" name="status"  class="easyui-textbox" data-options="required:true"/><br>
    createDate: <input type="text" name="createDate"  class="easyui-datebox" data-options="required:true"/><br>

</form>
<script>
    $(function () {
        $("#userEditForm").form('load',"${pageContext.request.contextPath}/picture/findOne?id=${param.id}");
    })
</script>