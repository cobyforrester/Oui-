package com.ouiplusplus.shell;
//import com.ouiplusplus.lexer.*; for later
import java.util.Scanner;

public class Shell {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.print("Oui++> ");
            String input = scanner.nextLine();
            System.out.println(input);
        }
    }
}
