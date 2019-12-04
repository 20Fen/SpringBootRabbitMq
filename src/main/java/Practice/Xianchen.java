package Practice;

import org.apache.catalina.core.ApplicationPart;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.MultipartConfigElement;
import javax.servlet.annotation.MultipartConfig;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Description:
 *
 * @date 2019年09月29日 9:20
 * Version 1.0
 */
public class Xianchen {

    public static void main(String[] args) throws InterruptedException {

//            double a=123164.14564135;
////            保存小数点后两位
//           System.out.println(String.format("%.2f",a));
////           去掉最后面两位
//           System.out.println(String.format("%02f",a));
////            前面补0，正整数 int类型
//           System.out.println(String.format("%05d",12));

//            System.out.println(String.format("%02f",a));
//        Thread thread1=new T1("123");
//        thread1.start();
//
//        Thread thread2=new T2("465");
//        thread2.start();
//        thread2.join();
//
//        Thread thread3=new T3("789");
//        thread3.start();
        String a ="D:\\file\\新建文本文档.txt";
        File f =new File(a);
        FileInputStream in= null;
        BufferedInputStream buffIn= null;
        FileOutputStream out= null;
        try {
            in= new FileInputStream(a.trim());
            buffIn=new BufferedInputStream(in);
            String[] split = a.split("\\\\");
            Path path = Paths.get("D:\\"+ File.separator+split[2]);
            byte[] bytes = a.getBytes();
            Files.write(path,bytes);
            out=new FileOutputStream("D:\\新建.txt");
            int i = buffIn.available();
            byte buffer[] = new byte[i];
            int len = 0;
            // 循环将输入流中的内容读取到缓冲区当中
            while ((len = in.read(buffer)) > 0) {
                // 输出缓冲区的内容到浏览器，实现文件下载
                out.write(buffer, 0, len);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.exit(0);
    }

}

//构造方法传值
class Test1 extends Thread {

    public static void main(String[] args) {
        Thread thread1=new Test1("123");
        thread1.start();
    }
    private String name;

    public Test1(String name) {
        this.name = name;
    }

    @Override
    public void run() {

        System.out.println("456"+name);

    }
}

//变量传值
class Test2 implements Runnable {

    public static void main(String[] args) {
        Test2 test2 =new Test2();
        test2.setName("789");
        Thread thread1 =new Thread(test2);
        thread1.start();

    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println("456"+name);
    }
}

class T1 extends Thread {

    private String name;

    public T1(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("1"+name);
        }

    }
}

class T2 extends Thread {

    private String name;

    public T2(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("2"+name);
        }

    }
}

class T3 extends Thread {

    private String name;

    public T3(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("3"+name);
        }
    }


}
