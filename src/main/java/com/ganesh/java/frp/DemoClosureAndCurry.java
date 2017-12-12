package com.ganesh.java.frp;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DemoClosureAndCurry {

    static private Integer b = 2;

    static private Integer getB() {
        return b;
    }

    static private class Int {
        public int value;

        public Int(int value) {
            this.value = value;
        }
    }

    public static void main(String[] args) {
        List values = Arrays.asList(1, 2, 3, 4, 5);

        SimpleEquationCalculatorWithValueNotChanging(values.stream(), 3)
                .collect(Collectors.toList())
                .forEach(System.out::println);

        Int a = new Int(3);

        SimpleEquationCalculatorWithValueChanging(values.stream(), a)
                .collect(Collectors.toList())
                .forEach(System.out::println);

        SimpleEquationCalculatorWithCurry(values.stream(), 3)
                .collect(Collectors.toList())
                .forEach(System.out::println);
    }

    static private Stream<Integer> SimpleEquationCalculatorWithCurry(Stream stream, Integer a) {
        return stream.map(
                ((Function<Integer, Function<Integer, Function<Integer, Integer>>>) x -> y -> t ->
                {
                    return x + y * t;
                }).apply(b)
                        .apply(a));
    }

    static private Stream<Integer> SimpleEquationCalculatorWithValueChanging(Stream values, Int a) {
        return values.map(new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer t) {
                Integer bx = getB();
                return t * a.value + bx;
            }
        });
    }

    static private Stream<Integer> SimpleEquationCalculatorWithValueNotChanging(Stream values, Integer a) {
        return values.map(new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer t) {
                return t * a + 2;
            }
        });
    }

    // Implement f(x) = x * 3 + 2
    static private Stream<Integer> SimpleEquationCalculator(Stream<Integer> values, Integer a) {
        return values.map(v -> (Integer) v * a + 2);
    }

}

