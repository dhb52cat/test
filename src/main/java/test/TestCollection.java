package test;

import org.junit.Test;

import java.util.*;


public class TestCollection {
    @Test
    public void testLinkedList(){
        LinkedList list= new LinkedList();
//        Vector synchronize
        HashSet<TestCollection> set=new HashSet();
        TestCollection t1=new TestCollection();
        TestCollection t2=new TestCollection();
        System.out.println(t1.hashCode());
        System.out.println(t2.hashCode());
        System.out.println(t1==t2);
        set.add(t1);
        set.add(t2);
        Iterator it=set.iterator();
        while (it.hasNext()){
            Object o=it.next();
            System.out.println(o);
        }


    }

    @Test
    public void testHashMap(){
        HashMap<Integer,Integer> hashMap=new HashMap();
        hashMap.put(1,1);
        System.out.println(1<<4);
    }
    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public boolean equals(Object obj) {
        return true;
    }
}
