package com.example.xtian.cecelectionvotingsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * Created by Xtian on 7/21/2015.
 */
public class DBconnect {

    public static String id;

    public static void main(String[] args) {
        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(Config.url, Config.user, Config.pass);

            PreparedStatement stInsert = con.prepareStatement("UPDATE student_ID SET vote_Number=vote_Number+? WHERE student_ID=? ");
            stInsert.setInt(1,1);
            stInsert.setInt(2,1);
            stInsert.executeUpdate();

        }
        catch (Exception e){
            e.printStackTrace();

        }
//    }
//        String connect(){
//            String connect_database = id;
//            return connect_database;
//        }

//        float num1 = 200;
//        int num2 = 1;
//        float total_num2_percentage;
//        int percentage;
//
//        total_num2_percentage = num2 *100;
//
//        percentage = (int) (total_num2_percentage/num1);
//
//
//
//        String  count;
//        count = "null";
//        //if ()
//
//        System.out.println("Percage is "+ percentage);
    }

    }

