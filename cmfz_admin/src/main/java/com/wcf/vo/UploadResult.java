package com.wcf.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UploadResult implements Serializable {
    //error 0 表示没有错误 非0表示有错误
    //data 已上传的图片路径
    private Integer errno;
    private ArrayList<String> data;
}
