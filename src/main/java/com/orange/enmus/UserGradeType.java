package com.orange.enmus;

/**
 * @author: GLGGAG
 * @Date: 2017/12/05/下午 15:42
 * @Description:
 * 用户级别类型枚举
 *
 */
public enum UserGradeType {

    Employee("员工"),

    TeamLeader("组长"),

    DepartmentHeads("部长"),

    ManagerCenter("总管");

    private String name;

    UserGradeType(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
