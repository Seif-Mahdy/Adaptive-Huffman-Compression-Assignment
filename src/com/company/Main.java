package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        HuffmanCompression obj = new HuffmanCompression();

        StringBuilder message =new StringBuilder();
        System.out.println("Enter the stream to compress ");
        Scanner scanner = new Scanner(System.in);
        String inputStream = scanner.next();
        System.out.println("----------------------------------");
        message=obj.comression(inputStream);

        System.out.println("compressed message  "+message);
        System.out.println("----------------------------------");
        System.out.println("Decompressed message  " +obj.decompression(message));










    }
}
