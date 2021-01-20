package algorithm;

/**
 * Created by qianyang on 18-3-7.
 */
public class ReverseLinkedTable {


    public static Node reverse(Node head) {
        if (head == null) {
            return null;
        }
        Node pre = null;
        Node cur = head;
        Node next;
        while (cur != null) {
            next = cur.next;
            cur.next = pre;

            pre = cur;
            cur = next;
        }
        return pre;
    }

    public static void main(String[] args) {
        Node head = new Node(1, null);
        Node cur = head;
        for (int i = 2; i < 4; i++) {
            cur.next = new Node(i, null);
            cur = cur.next;
        }
        Util.print(head);
        System.out.println("reverse");

        Util.print(reverse(head));

    }

}
