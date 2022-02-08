package com.company;

import java.lang.*;
import java.util.*;
import java.util.stream.*;

public class AnagramTest {

    public static List<List<String>> groupAnagrams(List<String> words) {
        List<List<String>> result = new LinkedList<>();
        List<String> wordsCopy = new LinkedList<>(words);

        words.forEach(item -> {
            wordsCopy.forEach(itm -> {
                if (isAnagram(item, itm)) {

                    List<String> bucketItem = result.stream()
                            .filter(f -> f.stream().anyMatch(ff -> isAnagram(ff, item)))
                            .findFirst().orElse(null);

                    if (bucketItem == null) {
                        List<String> bucket = new LinkedList<>();
                        bucket.add(item);
                        result.add(bucket);
                    } else {
                        int bucketIndex = result.indexOf(bucketItem);
                        List<String> bucket = result.get(bucketIndex);
                        if (!bucket.contains(item))
                            bucket.add(item);
                    }

                }

            });
        });

        System.out.println(result);
        return result;
    }

    private static String normalizeSolution(List<List<String>> solution) {
        return solution.stream()
                .map(l ->
                        l.stream()
                                .sorted()
                                .collect(Collectors.joining(",", "[", "]")))
                .sorted()
                .collect(Collectors.joining(",", "[", "]"));

    }


    private static boolean isAnagram(String str1, String str2) {
        if (Objects.isNull(str1) || Objects.isNull(str2))
            return false;
        if (str1.length() != str2.length())
            return false;

        char[] chars1 = str1.toCharArray();
        char[] chars2 = str2.toCharArray();

        List<Character> list1 = arrayToList(chars1);
        List<Character> list2 = arrayToList(chars2);

        return list1.stream()
                .allMatch((item) -> list2.stream()
                        .anyMatch(itm ->
                                String.valueOf(item).equalsIgnoreCase(String.valueOf(itm))
                        )
                );
    }

    private static List<Character> arrayToList(char[] characters) {
        List<Character> result = new ArrayList<>(characters.length);
        for (char ch : characters)
            result.add(ch);
        return result;
    }

    private static void testEquals(String name, Object actual, Object expected) {
        System.out.println("Test " + name + (expected.equals(actual) ? "PASSED" : "FAILED"));
    }

    public static void main(String args[]) {

        boolean isAnagram = isAnagram("bca", "abc");
        System.out.print(isAnagram);

        testEquals("Empty list test: ", normalizeSolution(groupAnagrams(Arrays.asList())), "[]");

        testEquals("Lowercase: ", normalizeSolution(groupAnagrams(Arrays.asList("tn", "cab", "vwa", "bca", "nt", "abc"))), "[[abc,bca,cab],[nt,tn],[vwa]]");

        testEquals("Mixed-case: ", normalizeSolution(groupAnagrams(Arrays.asList("Tn", "cab", "vwa", "BCA", "nt", "abc"))), "[[BCA,abc,cab],[Tn,nt],[vwa]]");

    }
}
