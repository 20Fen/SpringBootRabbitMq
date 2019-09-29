package Practice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import static org.apache.naming.SelectorContext.prefix;

/**
 * Description: 多数据插入
 *
 * @date 2019年09月29日 9:43
 * Version 1.0
 */
public class RMysqlTest {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        final String driver = "com.mysql.jdbc.Driver";
        final String url = "jdbc:mysql://172.21.11.44:3306/sys?characterEncoding=UTF-8";
        final String user = "root";
        final String password = "root";
//加载驱动
        Class.forName(driver);
//获取连接
        Connection conn = DriverManager.getConnection(url, user, password);
        if (null != conn) {
            System.out.println("连接成功");
            insert(conn);
        } else {
            System.out.println("连接失败");
        }
    }

    private static void insert(Connection conn) throws SQLException {

        StringBuilder builder = new StringBuilder();
//        开始时间
        long start = System.currentTimeMillis();
//        sql 编写
        String sql = "INSERT INTO service_diag_plan_process (pkid,plan_no,operator,operate_content,explain,create_time,plan_status) VALUES ";
//         关闭事务
        conn.setAutoCommit(false);

//        编译sql
        Statement statement = conn.createStatement();
        for (int i = 0; i <= 1; i++) {

            builder = new StringBuilder();
//          拼接sql
            for (int j = 0; j <= 10; j++) {
                builder.append("('" + UUID.randomUUID() + "','" + i * j + "','123'" + ",'456'" + ",'789'" + ",'2016-08-12 14:43:26'" + ",'测试'),");
            }
            String sql1 = sql + builder.substring(0, builder.length());
//            统一添加，统一执行
            statement.addBatch(sql1);
            statement.executeBatch();
//            关闭事务
            conn.commit();
            builder = new StringBuilder();

        }
        statement.close();
        conn.close();

        // 结束时间
        Long end = System.currentTimeMillis();
        System.out.println("数据插入时间:" + (end - start) / 1000 + "s");
    }
}
