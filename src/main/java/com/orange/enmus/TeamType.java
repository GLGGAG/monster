package com.orange.enmus;

/**
 * @author: GLGGAG
 * @Date: 2017/12/07/上午 10:22
 * @Description:
 * 团队枚举类
 */
public enum TeamType {

    development(0,"研发部门"),

    MobileTeam(1,"移动端项目组"),

    TestTeam(2,"测试项目组"),

    JavaTeam(3,"JAVA项目组"),

    PhpTeam(4,"PHP项目组"),

    DemandTeam(5,"需求分析项目组");

    private int  teamNo;

    private String teamName;

    TeamType(int teamNo,String teamName){
        this.teamNo=teamNo;
        this.teamName=teamName;
    }

    public int getTeamNo() {
        return teamNo;
    }

    public void setTeamNo(int teamNo) {
        this.teamNo = teamNo;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public static String getTeamName(int teamNo){
        TeamType[] values = TeamType.values();
        for (TeamType type : values){
            if (type.getTeamNo() == teamNo){
                return type.getTeamName();
            }
        }
        return  null;
    }

}
