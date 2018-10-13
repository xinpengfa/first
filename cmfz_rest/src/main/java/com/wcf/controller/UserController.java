package com.wcf.controller;

import com.wcf.entity.User;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Api(value="user controller",tags = "用户控制器类")
@RestController  // @Controller + @ResponseBody
@RequestMapping("/user")
public class UserController {

    /**
     * 查所有用户信息
     *
     * Http: Get   restful uri
     *
     */
    @ApiOperation(value = "查询所有用户信息")
    @RequestMapping(value="/all",method = RequestMethod.GET)  //@GetMapping
    public List<User> findAll(){
        List<User> users = Arrays.asList(new User());
        return users;
    }

    @ApiOperation("根据id查用户详情信息")
    // @GetMapping("/{id}") //  /user/1
    // public User findOne(@PathVariable(name="id") Integer id){  // 将uri中id占位的结果赋值给id形参
    @GetMapping("/findOne") //  /user/findOne?user_id=1
    public User findOne(@RequestParam(name="user_id") Integer id){  // 通过requestParam注解指定前台请求参数和后台形参的映射关系
        System.out.println("id："+id);
        return new User();
    }

    /**
     * 新增用户信息
     */
    @ApiOperation("新增用户信息")
    @PostMapping("/add")
    public String addNewUser(@RequestBody
                             @ApiParam(value = "用户对象",required = true) User user ){ // 直接将请求体（json）解析为User对象 json--->user
        System.out.println("user："+user);
        return "OK";
    }


    /**
     * 根据用户的姓名和年龄查询用户信息
     */
//    @ApiIgnore // 忽略生成api文档
    @ApiOperation("根据姓名和年龄查询用户信息")
//    @ApiImplicitParam(name="name",value="用户姓名",required = true,dataType = "string")
    @ApiImplicitParams(value={
        @ApiImplicitParam(name="name",value = "用户姓名",required = true,paramType = "query"),
        @ApiImplicitParam(name="age",value="用户年龄",required = false,paramType = "query",dataType = "int")
    })
    //@ApiResponse(code = 404,message = "资源没有找到")
    @ApiResponses({
            @ApiResponse(code = 404,message = "资源没有找到"),
            @ApiResponse(code = 400,message = "类型转换异常"),
            @ApiResponse(code = 500,message = "服务器内容错误"),
            @ApiResponse(code = 200,message = "处理正常")
    })
    @GetMapping("/findByNameAndAge")
    public List<User> findByNameAndAge( @RequestParam(required = true) String name,@RequestParam(defaultValue = "100") Integer age){
        System.out.println(name + " | "+age);
        List<User> users = Arrays.asList(new User());
        return users;
    }


    @ApiOperation("根据用户的编号删除用户信息")
    @DeleteMapping("/delete")
    public void removeUserById(Integer id){
        System.out.println(id);
    }

    @ApiOperation("根据用户信息修改为新的信息")
    @PutMapping("/modify")
    public void modify(@RequestBody User user){
        System.out.println(user);
    }
}
