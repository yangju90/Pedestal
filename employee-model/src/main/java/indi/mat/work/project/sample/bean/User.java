package indi.mat.work.project.sample.bean;

public class User {

    private Integer id;
    private String uname;
    private String pwd;
    private Integer age;

    public User(Integer id, String admin, String s, int i) {
        this.id = id;
        this.uname = admin;
        this.pwd = s;
        this.age = i;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}