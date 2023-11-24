package test;

import java.util.Arrays;
import org.junit.*;

public class Test1 {
//    @Test
    @Test
    public void test1(){
        String s=new String("ac");
        s.intern();
        String d="ac";
        System.out.println(d==s);
    }

    @Test
    public void test41() {
        String a = "asdb" + "b";
        String d = "asdb";//c==d
        String c = new String("asd") + "b";

        c.intern();
        
       // String d = "asdb";//c==d
        System.out.println(c == d);


    }
    @Test
    public void arrage(){
        int[] arr=new int[1032767];
        for(int i=0;i<1032767;i++){
            arr[i]=i;
        }
        int  rs=arrange(23222,1026096,arr);
        System.out.println(rs);
    }


    public int arrange (int n, int x, int[] a) {
        // write code here
        Arrays.sort(a);
        int sum=0;
        int rs=0;
        for(int m=a.length-1;m>-1;m--){
            sum=sum+a[m];
            if(sum/(a.length-m)<x){
                return rs;
            }else{
                rs=a.length-m;
            }
        }
        return rs;
    }
}
