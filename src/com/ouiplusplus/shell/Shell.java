package com.ouiplusplus.shell;
import com.ouiplusplus.run.Run;
import com.ouiplusplus.helper.Pair;
import com.ouiplusplus.error.Error;
import com.ouiplusplus.lexer.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Shell {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.print("Oui++> ");
            String input = scanner.nextLine();
            Run run = new Run();
            Pair<ArrayList<Token>, Error> pair = run.run(input);
            Error error = pair.getP2();
            if(error != null) {
                System.out.println(error);
            } else {
                System.out.println(pair.getP1());
            }

        }
    }
}
