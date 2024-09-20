package com.fintech.parser.hw3;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class Node<T> {
    private T data;
    private Node<T> next;
    private Node<T> previous;

    public Node(T data) {
        this.data = data;
        this.next = null;
        this.previous = null;
    }
}
