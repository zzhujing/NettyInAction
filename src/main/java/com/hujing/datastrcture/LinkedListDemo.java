package com.hujing.datastrcture;


public class LinkedListDemo {
    public static void main(String[] args) {
        //如何判断一个字符串是否是回文串
        //1. 快慢指针获取中节点
        //2. 循环对比，若都相同则为回文串

        SingleLinkedList linkedList = new SingleLinkedList();
        linkedList.add("1");
        linkedList.add("2");
        linkedList.add("3");
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

            if (head == null || head.next == null) return false;

            //快慢指针获取中间节点
            Node slow = head;
            Node quick = head;

            if (head.next.next == null && head.data.equals(head.next.data)) return true;
            while (quick.next != null && quick.next.next != null) {
                slow = slow.next;
                quick = quick.next.next;
            }

            Node left = null;
            if (quick.next == null) {
                //slow为中间节点
                left = flip(slow);
            } else {
                //slow,quick为中间两节点
                left = flip(slow.next);
            }
            Node next = slow.next;
            while (left != null) {
                if (left.data.equals(next.data)) {
                    left = left.next;
                    continue;
                }
                return false;
            }
            return true;
        }

        public Node flip(Node source) {
            if (source == head) return head;
            Node cur = head;
            Node next = null;
            Node pre = null;
            while (cur != source) {
                next = cur.next;
                cur.next = pre;
                pre = cur;
                cur = next;
            }
            return pre;
        }

        public Node flip() {
            Node cur = head;
            Node next = null;
            Node pre = null;
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
