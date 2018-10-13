<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<form id="userSaveForm" class="easyui-form" method="post" enctype="multipart/form-data">

    轮播图描述: <input type="text" name="description"  class="easyui-textbox" data-options="width:150"/><br>

    轮播图状态: <select name="status" class="easyui-combobox" data-options="panelHeight:50,width:150">
                    <option value="展示">展示</option>
                    <option value="不展示">不展示</option>
                </select><br>
    上传轮播图: <input class="easyui-filebox" name="aa"   data-options="width:150,buttonText:'文件'"/><br>

</form>
