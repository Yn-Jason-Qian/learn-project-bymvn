package algorithm;

/**
 * Created by qianyang on 18-3-29.
 *
 *

 给定两个非空链表来代表两个非负数，位数按照逆序方式存储，它们的每个节点只存储单个数字。将这两数相加会返回一个新的链表。
 你可以假设除了数字 0 之外，这两个数字都不会以零开头。

 示例：

 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
 输出：7 -> 0 -> 8
 原因：342 + 465 = 807

 */
public class AddTwoNumbers {
    static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null || l2 == null) {
            return new ListNode(0);
        }
        ListNode head = null;
        ListNode current = null;
        int carryon = 0;
        while (l1 != null && l2 != null) {
            int number = l1.val + l2.val + carryon;
            if (number >= 10) {
                number = number - 10;
                carryon = 1;
            } else {
                carryon = 0;
            }
            if (current == null) {
                current = new ListNode(number);
                head = current;
            } else {
                current.next = new ListNode(number);
                current = current.next;
            }
            l1 = l1.next;
            l2 = l2.next;
        }
        while (l1 != null) {
            int number = l1.val + carryon;
            if (number >= 10) {
                carryon = 1;
                number = number - 10;
            } else {
                carryon = 0;
            }
            current.next = new ListNode(number);
            current = current.next;
            l1 = l1.next;
        }
        while (l2 != null) {
            int number = l2.val + carryon;
            if (number >= 10) {
                carryon = 1;
                number = number - 10;
            } else {
                carryon = 0;
            }
            current.next = new ListNode(number);
            current = current.next;
            l2 = l2.next;
        }
        if (carryon == 1) {
            current.next = new ListNode(1);
        }
        return head;
    }

    public static void main(String[] args) {

    }
}
