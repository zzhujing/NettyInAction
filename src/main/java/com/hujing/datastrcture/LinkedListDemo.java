package com.hujing.datastrcture;


public class LinkedListDemo {
    public static void main(String[] args) {
        //如何判断一个字符串是否是回文串
        //1. 快慢指针获取中节点
        //2. 循环对比，若都相同则为回文串

        SingleLinkedList linkedList = new SingleLinkedList();
        linkedList.add("2");
        linkedList.add("2");
        linkedList.add("2");
        linkedList.add("2");
        linkedList.add("1");
        linkedList.foreach();
        System.out.println(linkedList.head);
        System.out.println(linkedList.last);
        System.out.println("======flip=====");
        System.out.println(linkedList.checkBackToText());
    }


    static class SingleLinkedList {
        private Node head;
        private Node last;
        private int size;

        public void add(String data) {
            Node newNode = new Node(data, null);
            if (head == null) {
                last = newNode;
                head = newNode;
            } else {
                //添加节点到末尾
                last.next = newNode;
                last = newNode;
            }
            size++;
        }

        public void foreach() {
            if (head == null) return;
            Node cur = head;
            while (cur.next != null) {
                System.out.print(cur.data + " ,");
                cur = cur.next;
            }
            System.out.println(cur.data);
        }

        public boolean checkBackToText() {
            //1. 快慢指针获取中节点
            if (head==null) throw new IllegalArgumentException("非法参数异常");
            if (head.next==null) return true;
            Node slow = head;
            Node quick = head;
            while (quick.next != null && quick.next.next != null) {
                slow = slow.next;
                quick = quick.next.next;
            }
            Node left = head;

            //翻转后半节点部分，这里不需要区分奇数偶数，因为奇数的时候left和right中间多一个节点不需要判断所以翻转slow.next即可，偶数的时候直接翻转slow.next即可
            Node right = flip(slow.next);
            //2. 翻转后半部分节点
            //3. 遍历比较，这里left和right节点肯定是一样多的。所以直接用right来判断
            while (right!=null) {
                if (left.data.equals(right.data)) {
                    left = left.next;
                    right = right.next;
                }else {
                    return false;
                }
            }
            return true;
        }

        public Node flip(Node source) {
            if (source == null) throw new IllegalArgumentException("error argument");
            if (source.next==null) return source;
            Node cur = source;
            Node pre = null;
            Node next = null;
            while (cur != null) {
                next = cur.next;
                cur.next = pre;
                pre = cur;
                cur = next;
            }
            return pre;
        }


        private static class Node {
            private String data;
            private Node next;

            public Node(String data, Node next) {
                this.data = data;
                this.next = next;
            }

            @Override
            public String toString() {
                return "Node{" +
                        "data='" + data + '\'' +
                        ", next=" + next +
                        '}';
            }

            public String getData() {
                return data;
            }

            public void setData(String data) {
                this.data = data;
            }

            public Node getNext() {
                return next;
            }

            public void setNext(Node next) {
                this.next = next;
            }
        }
    }
}
