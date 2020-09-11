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
        input = input.substring(setLanguage(input));
        Pair<String, Error> pair = run.generateOutput("main.ouipp", input);
        Error error = pair.getP2();
        if(error != null) {
            System.out.println(error);
        } else {
            System.out.println(pair.getP1());
        }
    }
    public static int setLanguage(String input) {
        int count = 0;
        while (count < input.length()) {
            if (input.charAt(count) != '\n'
                    && input.charAt(count) != '\t'
                    && input.charAt(count) != ' ') {
                if (input.length() - count >= 7
                        && input.substring(count, count + 8).equals("lang:eng")) {
                    Language.language = Languages.ENGLISH;
                    count += 8;
                    int tmp = count;
                    while (tmp < input.length()) {
                        if (input.charAt(tmp) == '\n') {
                            return count;
                        } else if (input.charAt(tmp) != ' '
                                && input.charAt(tmp) != '\t') {
                            return 0;
                        }
                        tmp++;
                    }
                    return count;
                } else if (input.length() - count >= 6
                        && input.substring(count, count + 7).equals("lang:fr")) {
                    Language.language = Languages.FRENCH;
                    count += 7;
                    int tmp = count;
                    while (tmp < input.length()) {
                        if (input.charAt(tmp) == '\n') {
                            return count;
                        } else if (input.charAt(tmp) != ' '
                                && input.charAt(tmp) != '\t') {
                            return 0;
                        }
                        tmp++;
                    }
                    return count;
                }
            }
            count++;
        }
        return 0;
    }
}
