package Practice;

import com.example.demo.system.util.MD5;
import com.example.demo.system.util.NumberUtil;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Description:测试类
 */
public class Test {
    @org.junit.Test
    public void md5salt()
    {
        String keyword="i love you";
        String md5= DigestUtils.md5Hex(keyword);
        System.out.println("md5加密后："+"\n"+md5);
        String md5salt= MD5.md5PlusSalt(keyword);
        System.out.println("加盐后："+"\n"+md5salt);
        String word= MD5.md5MinusSalt(md5salt);
        System.out.println("解密后："+"\n"+word);
        String c="10";
        String effective = NumberUtil.getTwo(c);
        String a="10.12145464";
        System.out.println(effective);
        String effective1 = NumberUtil.getDecimal(a, 3);
        System.out.println(effective1);
        String twoPlace = NumberUtil.getTwoPlace(a);
        System.out.println(twoPlace);
    }
}
