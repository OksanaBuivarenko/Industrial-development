package com.fintech.parser.hw3;

import java.util.Collection;

public class CustomLinkedList<T> {
    private Node<T> start;
    private Node<T> end;
    private int size;

    public CustomLinkedList() {
        start = null;
        end = null;
        size = 0;
    }

    public int getSize() {
        return size;
    }

    public T getStart() {
        if (isEmpty()) {
            throw new RuntimeException("Unable to get element, this list is empty!");
        }
        return start.getData();
    }

    public T getEnd() {
        if (isEmpty()) {
            throw new RuntimeException("Unable to get element, this list is empty!");
        }
        return end.getData();
    }

    private Node<T> getStartNode() {
        return start;
    }

    private Node<T> getEndNode() {
        return end;
    }

    private void addFirstNode(T data) {
        start = new Node<>(data);
        end = start;
    }

    public void addEnd(T data) {
        if (isEmpty()) {
            addFirstNode(data);
        } else {
            Node<T> current = new Node<>(data);
            if (start.getNext() == null) {
                end = current;
                end.setPrevious(start);
                start.setNext(end);
            } else {
                Node<T> prev = end;
                end = current;
                prev.setNext(end);
                end.setPrevious(prev);
            }
        }
        size++;
    }

    public void addStart(T data) {
        if (isEmpty()) {
            addFirstNode(data);
        } else {
            Node<T> current = new Node<>(data);
            if (start.getNext() == null) {
                start = current;
                end.setPrevious(start);
                start.setNext(end);
            } else {
                Node<T> second = start;
                start = current;
                start.setNext(second);
                second.setPrevious(start);
            }
        }
        size++;
    }

    public boolean isEmpty() {
        return start == null && end == null;
    }

    private void removeSingle() {
        start = null;
        end = null;
    }

    public void removeStart() {
        if (!isEmpty()) {
            if (size == 1) {
                removeSingle();
            } else {
                Node<T> current = start;
                start = current.getNext();
                start.setPrevious(null);
            }
            size--;
        }
    }

    public void removeEnd() {
        if (!isEmpty()) {
            if (size == 1) {
                removeSingle();
            } else {
                Node<T> current = end;
                end = current.getPrevious();
                end.setNext(null);
            }
            size--;
        }
    }

    public boolean contains(T data) {
        Node<T> node = start;
        while (node != null) {       //.getNext()
            if (node.getData().equals(data)) {
                return true;
            }
            node = node.getNext();
        }
        return false;
    }

    public void addAll(Collection<T> list) {
        for (T element : list) {
            addEnd(element);
        }
    }

    public void addAll(CustomLinkedList<T> list) {
        if (isEmpty()) {
            start = list.getStartNode();
        } else {
            Node<T> current = end;
            Node<T> next = list.getStartNode();
            current.setNext(next);
            next.setPrevious(current);
        }
        end = list.getEndNode();
        size += list.size;
    }

    public void print() {
        if (!isEmpty()) {
            Node<T> node = start;
            while (node.getNext() != null) {
                System.out.print(node.getData() + " -> ");
                node = node.getNext();
            }
            System.out.println(end.getData());
        }
    }
}