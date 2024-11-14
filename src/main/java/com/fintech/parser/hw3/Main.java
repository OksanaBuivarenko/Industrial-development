package com.fintech.parser.hw3;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        CustomLinkedListImpl<String> list = new CustomLinkedListImpl<>();

        list.addEnd("Word-1");
        list.addEnd("Word-2");
        list.addEnd("Word-3");
        list.addEnd("Word-4");
        list.addStart("Word-0");

        System.out.println("List start element - " + list.getStart());
        System.out.println("List end element - " + list.getEnd());

        list.removeStart();
        list.removeEnd();

        System.out.println(list.toString());

        System.out.println(list.contains("Word-0"));
        System.out.println(list.contains("Word-1"));

        System.out.println("List size before add newList - " + list.getSize());
        List<String> newList = List.of("Word-5", "Word-6", "Word-7");
        list.addAll(newList);
        System.out.println("List size after add newList - " + list.getSize());
        System.out.println(list.toString());

        System.out.println("List size before add newLinkedList - " + list.getSize());
        CustomLinkedListImpl<String> newLinkedList = new CustomLinkedListImpl<>();
        newLinkedList.addEnd("Word-8");
        newLinkedList.addEnd("Word-9");
        newLinkedList.addEnd("Word-10");
        list.addAll(newLinkedList);
        System.out.println("List size after add newLinkedList - " + list.getSize());

        System.out.println(list.toString());

        List<String> l1 = List.of("S1", "S2");
        List<String> l2 = List.of("S3", "S4");
        List<String> l3 = List.of("S5", "S6", "S7");
        List<List<String>> lists = List.of(l1, l2, l3);
        CustomLinkedListImpl<String> reduceList = lists.stream().reduce(
                new CustomLinkedListImpl<>(),
                (acc, el) -> {
                    acc.addAll(el);
                    return acc;
                },
                (acc, el) -> {
                    acc.addAll(el);
                    return acc;
                }
        );
        System.out.println(reduceList.toString());

        CustomLinkedListImpl<String> reduceList1 = l1.stream().reduce(
                new CustomLinkedListImpl<>(),
                (acc, el) -> {
                    acc.addEnd(el);
                    return acc;
                },
                (acc, el) -> {
                    acc.addAll(el);
                    return acc;
                }
        );
        System.out.println(reduceList1.toString());


        System.out.println();
        System.out.println("CustomLinkedList iterator forEachRemaining: ");
        CustomIterator<String> iterator = list.iterator();
        iterator.forEachRemaining(System.out::println);
    }
}