package net.stealthcat.test;

import com.google.common.collect.Lists;

import java.util.Comparator;
import java.util.List;

/**
 * @author y_qiana
 * @date 2018/8/22
 */
public class FilterAndMaxTest {

    public static void main(String[] args) {
        List<A> list = Lists.newArrayList();
        list.add(new A("first", "a", 1));
        list.add(new A("second", "a", 2));
        list.add(new A("third", "a", 3));
        list.add(new A("fourth", "d", 4));

        System.out.println(list.stream().filter(s -> s.parent.equalsIgnoreCase("a")).max(Comparator.comparing(A::getLevel)).orElse(new A()).getName());
    }

    private static class A {
        String name;
        String parent;
        int level;

        public A() {
        }

        public A(String name, String parent, int level) {
            this.name = name;
            this.parent = parent;
            this.level = level;
        }

        public String getParent() {
            return parent;
        }

        public void setParent(String parent) {
            this.parent = parent;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }
    }

}
