package com.ouiplusplus.start;
import com.ouiplusplus.helper.Pair;
import com.ouiplusplus.lexer.Run;
import com.ouiplusplus.error.Error;
import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;

public class Start {
    public static void main(String[] args) throws FileNotFoundException {
        Run run = new Run();
        String fname = "/Users/cobyforrester/Desktop/Professional/Projects/JavaProjects/out/production/OuiPlusPlus/com/ouiplusplus/start/main.ouipp";
        File file = new File(fname);
        Scanner sf = new Scanner(file);
        sf.useDelimiter("\\Z"); // gets whole file as one string
        String input = sf.next();
        Pair<String, Error> pair = run.generateOutput("main.ouipp", input);
        Error error = pair.getP2();
        if(error != null) {
            System.out.println(error);
        } else {
            System.out.println(pair.getP1());
        }
    }
}
