package indi.mat.work.db;

/**
 * @author Mat
 * @version : Student, v 0.1 2022-03-16 18:53 Yang
 */
public class Student {
    private Integer id;
    private String name;
    private String subject;
    private Integer score;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }


    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
