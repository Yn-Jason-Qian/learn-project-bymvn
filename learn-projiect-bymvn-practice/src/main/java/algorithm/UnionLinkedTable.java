package algorithm;

/**
 * Created by qianyang on 18-3-7.
 */
public class UnionLinkedTable {

    public static Node union(Node first, Node second) {
        if (first == null) {
            return second;
        }
        if (second == null) {
            return first;
        }
        Node point1 = first;
        Node point2 = second;
        Node resultHead = null;
        Node resultCur = null;
        while (point1 != null && point2 != null) {
            if (point1.value < point2.value) {
                if (resultHead == null) {
                    resultHead = point1;
                    resultCur = resultHead;
                } else {
                    resultCur.next = point1;
                    resultCur = point1;
                }
                point1 = point1.next;
            } else {
                if (resultHead == null) {
                    resultHead = point2;
                    resultCur = resultHead;
                } else {
                    resultCur.next = point2;
                    resultCur = point2;
                }
                point2 = point2.next;
            }
        }
        while (point1.next != null) {

        }
        return resultHead;
    }


}
