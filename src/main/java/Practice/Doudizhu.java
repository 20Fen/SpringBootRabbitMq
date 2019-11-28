package Practice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Description:Y
 */
public class Doudizhu {

    public static void main(String[] args) {

        List<String> color = new ArrayList();
        List<String> num = new ArrayList();
        List<String> zongpai = new ArrayList();
        //牌色
        color.add("♠");
        color.add("♥");
        color.add("♦");
        color.add("♣");
        //数字
        for (int j = 1; j <= 10; j++) {
            num.add(j+"");
        }

        num.add("J");
        num.add("Q");
        num.add("K");
        //总张数
        String pai=null;
        for (String o : color) {
            for (String o1 : num) {
                pai= o + o1;
                zongpai.add(pai);
            }
        }
        zongpai.add("大");
        zongpai.add("小");
//打乱顺序
        Collections.shuffle(zongpai);

        List<String> play1=new ArrayList();
        List<String> play2=new ArrayList();
        List<String> play3=new ArrayList();
        List<String> dipai=new ArrayList();

        for (int i = 0; i < zongpai.size(); i++) {
            String paimian = zongpai.get(i);
            if(i >= 51){
                dipai.add(paimian);
            }else {
                if(i % 3 == 0){
                    play1.add(paimian);
                }else if(i % 3 == 1){
                    play2.add(paimian);
                }else {
                    play3.add(paimian);
                }
            }
        }
        System.out.println("令狐冲："+play1);
        System.out.println("田伯光："+play2);
        System.out.println("绿竹翁："+play3);
        System.out.println("底牌："+dipai);

    }
}
