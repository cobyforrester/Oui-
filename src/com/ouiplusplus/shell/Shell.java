package com.ouiplusplus.shell;
import com.ouiplusplus.helper.Pair;
import com.ouiplusplus.lexer.Run;
import com.ouiplusplus.error.Error;
import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;

public class Shell {
    public static void main(String[] args) throws FileNotFoundException {
        Run run = new Run();
        String fname = "/Users/cobyforrester/Desktop/Professional/Projects/JavaProjects/out/production/OuiPlusPlus/com/ouiplusplus/shell/main.ouipp";
        File file = new File(fname);
        Scanner sf = new Scanner(file);
        sf.useDelimiter("\\Z");
        String input = sf.next();
        Pair<String, Error> pair = run.runToString("main.ouipp", input);
        /*
        Run run = new Run();
        Scanner scanner = new Scanner(System.in);
        while(true) {

            System.out.print("Oui++>> ");
            String input = scanner.nextLine();
            Pair<String, Error> pair = run.runToString("Main.ouipp", input);
            Error error = pair.getP2();
            if(error != null) {
                System.out.println(error);
            } else {
                System.out.println(pair.getP1());
            }
        }
        */
    }
}
