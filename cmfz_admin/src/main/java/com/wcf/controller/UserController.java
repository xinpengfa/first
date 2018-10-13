package com.wcf.controller;

import com.wcf.entity.User;
import com.wcf.service.UserService;
import com.wcf.vo.Result;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@Controller
@RequestMapping("user")
public class UserController {
    @Resource
    private UserService userService;




    @RequestMapping("findByPage")
    @ResponseBody
    public Map<String, Object> findByPage(Integer page, Integer rows){
        List<User> users = userService.queryByPage(page, rows);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("rows",users);
        map.put("total",userService.findCounts());
        return map;
    }

    @RequestMapping("add")
    @ResponseBody
    public Result add(User user){
        Result result = new Result();
        try {
            userService.add(user);
            result.setSuccess(true);
            result.setMessage("添加成功");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("添加失败");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("exportUser")
    public void exportUser(HttpServletResponse response) throws IOException {
        //创建 Excel 工作薄对象
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建工作表
        HSSFSheet sheet = workbook.createSheet("用户信息");
        //创建标题行
        HSSFRow row = sheet.createRow(0);

        String[] title = {"编号","姓名","昵称","性别","手机","城市","签名","注册时间","状态"};

        //创建单元格对象
        HSSFCell cell = null;
        for (int i = 0; i < title.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
        }

        //处理日期格式
        HSSFCellStyle cellStyle = workbook.createCellStyle(); //样式对象
        HSSFDataFormat dataFormat = workbook.createDataFormat(); //日期格式
        cellStyle.setDataFormat(dataFormat.getFormat("yyyy 年 MM 月 dd 日 ")); //设置日期格式

        Long counts = userService.findCounts();
        List<User> users = userService.queryAll();
        //处理数据行
        for (int i = 1; i <=counts; i++) {
            row = sheet.createRow(i);
            User user = users.get(i-1);
            row.createCell(0).setCellValue(user.getId());
            row.createCell(1).setCellValue(user.getName());
            row.createCell(2).setCellValue(user.getNikeName());
            row.createCell(3).setCellValue(user.getSex());
            row.createCell(4).setCellValue(user.getPhone());
            row.createCell(5).setCellValue(user.getCity());
            row.createCell(6).setCellValue(user.getSign());
            HSSFCell cell1 = row.createCell(7);
            cell1.setCellStyle(cellStyle);
            cell1.setCellValue(user.getCreateDate());
            row.createCell(8).setCellValue(user.getStatus());

        }
        response.setHeader("content-disposition", "attachment;filename=user.xls");
        response.setContentType("application/vnd.ms-excel");
        ((HSSFWorkbook) workbook).write(response.getOutputStream());
        /*try {
            workbook.write(new File("C:\\Users\\wcf65\\Desktop\\用户.xls"));
            workbook.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/

        }

        @RequestMapping("importUser")
        public void importUser() throws IOException {
            try {
                User user = new User();
                // 获取本地 Excel 文件输入流，并创建工作薄对象
                HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream("C:\\Users\\wcf65\\Desktop\\用户.xls"));
                // 获取工作表
                HSSFSheet sheet = workbook.getSheet("用户信息");
                // 声明行对象
                HSSFRow row = null;

                //注意：获取数据 需排除标题行 从数据行开始读取
                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    // 获取当前工作表中的数据行信息 数据行索引从 1 开始
                    row = sheet.getRow(i);
                    String stringCellValue = row.getCell(0).getStringCellValue();
                    String stringCellValue1 = row.getCell(1).getStringCellValue();
                    String stringCellValue2 = row.getCell(2).getStringCellValue();
                    String stringCellValue3 = row.getCell(3).getStringCellValue();
                    String stringCellValue4 = row.getCell(4).getStringCellValue();
                    String stringCellValue5 = row.getCell(5).getStringCellValue();
                    String stringCellValue6 = row.getCell(6).getStringCellValue();
                    Date dateCellValue = row.getCell(7).getDateCellValue();
                    String stringCellValue7 = row.getCell(8).getStringCellValue();
                    user.setId(stringCellValue);
                    user.setName(stringCellValue1);
                    user.setNikeName(stringCellValue2);
                    user.setSex(stringCellValue3);
                    user.setPhone(stringCellValue4);
                    user.setCity(stringCellValue5);
                    user.setSign(stringCellValue6);
                    user.setCreateDate(dateCellValue);
                    user.setStatus(stringCellValue7);
                    userService.add(user);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    @RequestMapping("customExport")
    public void customExportUser(String titles, String columns, HttpServletResponse response) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException {
        // 根据字段列表查询用户集合
        List<User> users = userService.queryByColumns(columns);
        for (User user : users) {
            System.out.println(user);
        }
        Workbook workbook = new HSSFWorkbook();

        //处理日期格式
        CellStyle cellStyle = workbook.createCellStyle();//样式对象
        DataFormat dataFormat = workbook.createDataFormat();//日期格式
        cellStyle.setDataFormat(dataFormat.getFormat("yyyy 年 MM 月 dd 日 ")); //设置日期格式
        Sheet sheet = workbook.createSheet("用户信息");
        Row row = sheet.createRow(0);

        // 将用户信息导出到Excel表格中
        // Excel=标题行+数据行
        String[] title = titles.split(",");
        for (int i = 0; i < title.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(title[i]);
        }

        // [id,name,age,birthday]
        // 可以 get+Cname = get方法名 注意get后的首字母大写,可以前台直接设置成大写的传到后台
        //即"get" +a.substring(0, 1).toUpperCase() + s.substring(1, cName.length());
        String[] column = columns.split(",");
        for (int i = 1; i <= users.size(); i++) {
            row = sheet.createRow(i);
            User user = users.get(i - 1);
            Class<? extends User> userClass = user.getClass();
            for (int j = 0; j < column.length; j++) {
                Cell cell = row.createCell(j);
                String s = column[j];
                String methodName = "get"+s;
                //null 因为没有方法参数所以为null
                Method method = userClass.getMethod(methodName, null);
                Object o = method.invoke(user, null);
                if(o == null){
                    continue;
                }
                if(o instanceof Date){
                    cell.setCellStyle(cellStyle);
                    cell.setCellValue((Date) o);
                } else {
                    cell.setCellValue(o.toString());
                }
            }


        }
        response.setHeader("content-disposition", "attachment;filename=user.xls");
        response.setContentType("application/vnd.ms-excel");
        ((HSSFWorkbook) workbook).write(response.getOutputStream());
    }



}
