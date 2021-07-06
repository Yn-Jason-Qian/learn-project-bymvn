package net.stealthcat.leecode;

public class ReverseLinkedList {


    public static void main(String[] args) {
        ListNode node1 = new ListNode(1),
                node2 = new ListNode(2),
                node3 = new ListNode(3),
                node4 = new ListNode(4),
                node5 = new ListNode(5);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;

        System.out.println(node1);
//        ListNode head = reverse(node1);
        ListNode head = reverse2(node1);
        System.out.println(head);
    }

    private static ListNode reverse(ListNode head) {
        ListNode prev = null;
        while (head != null) {
            ListNode next = head.next;
            head.next = prev;
            prev = head;
            head = next;
        }
        return prev;
    }

    private static ListNode reverse2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode prev = reverse2(head.next);
        head.next.next = head;
        head.next = null;
        return prev;
    }
}

class ListNode {
    int val;
    ListNode next;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(val + "").append(",");
        ListNode next = this.next;
        while (next != null) {
            builder.append(next.val).append(",");
            next = next.next;
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

    ListNode(int x) {
        val = x;
    }


}
