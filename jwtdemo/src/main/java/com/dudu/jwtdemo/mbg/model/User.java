package com.dudu.jwtdemo.mbg.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
public class User implements Serializable {
    @ApiModelProperty(value = "序号")
    private Integer id;

    @ApiModelProperty(value = "账号")
    private String name;

    @ApiModelProperty(value = "密码")
    private String password;

    private static final long serialVersionUID = 1L;

}