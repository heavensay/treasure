package com.helix.demo.db.mysql;

import org.junit.Test;

import java.sql.*;
import java.util.UUID;

/**
 * @author lijianyu
 * @date 2023/12/16 15:09
 */
public class TestMysql {

    @Test
    public void batchInsert() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection conn = DriverManager.getConnection("jdbc:mysql://172.25.11.51:3306/dev_hdj?useUnicode=true&characterEncoding=utf8&useSSL=false",
                "root", "abcABC123!");

        String sql = "insert into hdj_register(bid, uid,register_time,register_flag,token) VALUES (?,?,?,?,?)";
        Statement stat = conn.prepareStatement(sql);
        long start = System.currentTimeMillis();//开始计时【单位：毫秒】
        PreparedStatement ps = null;
        int rownum = 20000;
        try {
            Long bidStart = 1050000L;
            Long uidStart = 3050000L;
//            Long bidStart = 1000000L;
//            Long uidStart = 3000000L;
            ps = conn.prepareStatement(sql);
            for (int i = 1; i <= rownum; i++) {
                ps.setLong(1, bidStart + Integer.toUnsignedLong(i));//填充sql语句种得占位符
                ps.setLong(2, uidStart + Integer.toUnsignedLong(i));
                ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                ps.setInt(4, i % 20 == 0 ? 0 : 1);
                ps.setString(5, UUID.randomUUID().toString());
                ps.addBatch();//执行sql语句
                if (i % 1000 == 0) {
                    ps.executeBatch();
                    ps.clearBatch();
                }
            }
            System.out.println("bid:" + bidStart + rownum);
            System.out.println("uidStart:" + uidStart + rownum);
            //为防止有sql语句漏提交【如i结束时%500！=0的情况】，需再次提交sql语句
            ps.executeBatch();//将容器中的sql语句提交
            ps.clearBatch();//清空容器
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ps.close();
            conn.close();
        }
        //打印耗时【单位：毫秒】
        System.out.println("百万条数据插入用时：" + (System.currentTimeMillis() - start) + "【单位：毫秒】");

    }

}
