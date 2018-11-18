package com.lixin.rxjava2demo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LiXin
 * @date 2018/11/16 15:59
 * @description User
 * @file RxJava2Demo
 */
public class User {

    String name;
    String password;
    List<String> mList;

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password == null ? "" : password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getList() {
        if (mList == null) {
            return new ArrayList<>();
        }
        return mList;
    }

    public void setList(List<String> list) {
        mList = list;
    }
}
