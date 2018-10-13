package com.wcf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Log {
    private String id;
    private String user;
    private Date time;
    private String resource;
    private String action;
    private String message;
    private String result;
}
