package com.example.xtian.cecelectionvotingsystem.getSetterItem;

/**
 * Created by Xtian on 11/19/2015.
 */
public class item_sqlite {

    String id;
    String studentNo;
    String studentId;
    String studentName;
    String studentCourse;
    String studentPass;
    String studentFlag;




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public item_sqlite(String id, String studentName, String studentPass,String studentFlag) {
        this.id = id;
        this.studentName = studentName;
        this.studentPass =  studentPass;
        this.studentFlag = studentFlag;
    }


    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentCourse() {
        return studentCourse;
    }

    public void setStudentCourse(String studentCourse) {
        this.studentCourse = studentCourse;
    }

    public String getStudentPass() {
        return studentPass;
    }

    public void setStudentPass(String studentPass) {
        this.studentPass = studentPass;
    }

    public String getStudentFlag() {
        return studentFlag;
    }

    public void setStudentFlag(String studentFlag) {
        this.studentFlag = studentFlag;
    }

    public item_sqlite(String studentNo, String studentID, String studentName, String studentCourse, String studentPass, String studentFlag) {
        this.studentNo = studentNo;
        this.studentId = studentID;
        this.studentName = studentName;
        this.studentCourse = studentCourse;
        this.studentPass = studentPass;
        this.studentFlag = studentFlag;

    }

}
