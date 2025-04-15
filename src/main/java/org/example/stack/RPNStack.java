package org.example.stack;

import static java.lang.Long.valueOf;

public class RPNStack {
    Node top;
    int size;

    public RPNStack() {
        this.top = null;
        this.size= 0;
    }

    public void push(Number value){
        Number a = 3;
        Node next=top;
        top=new Node(value);
        top.setNext(next);
        this.size+=1;

    }

    public Number pop(){
        if (this.size==0){
            throw new Error();
        }
        Number number=top.value;
        this.top=top.next;
        size--;
        return number;
    }

    public int length(){
        return this.size;
    }
}
