package org.example.stack;

public class Node{
    Number value;
    Node next;

    public Node(Number value) {
        this.value = value;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
