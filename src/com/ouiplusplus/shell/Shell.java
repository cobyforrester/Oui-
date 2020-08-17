package com.ouiplusplus.shell;
import com.ouiplusplus.lexer.*;
import com.ouiplusplus.helper.Pair;
import com.ouiplusplus.error.Error;

import java.util.Scanner;

public class Shell {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.print("Oui++>> ");
            String input = scanner.nextLine();
            Run run = new Run();
            Pair<String, Error> pair = run.runToString("Main.fr", input);
            Error error = pair.getP2();
            if(error != null) {
                System.out.println(error);
            } else {
                System.out.println(pair.getP1());
            }
        }
    }
}
