package org.queryex;

import org.queryex.checker.Processor;

public class Main {
    public static void main(String[] args) {
        System.out.println(Processor.getInstance().extract(new StringBuffer(),4));
        ;
    }
}