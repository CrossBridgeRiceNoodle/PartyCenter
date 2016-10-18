package com.crossbridgericenoodle.partycenter.model;

/**
 * 用户信息类
 */

public class User {
    public static final int SEX_MAN = 1;
    public static final int SEX_WOMAN = 0;
    public static final int TYPE_AUDIENCE = 2;
    public static final int TYPE_HOST = 3;

    public int ID;
    public int type;
    public String name;
    public String email;
    public int sex;


}
