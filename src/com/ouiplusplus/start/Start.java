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
        int count = removeLeadingComments(input);
        while (count < input.length()) {
            if (input.charAt(count) != '\n'
                    && input.charAt(count) != '\t'
                    && input.charAt(count) != ' ') {
                if (input.length() - count >= 7
                        && input.startsWith("lang:eng", count)) {
                    Language.language = Languages.ENGLISH;
                    count += 8;
                    int tmp = count;
                    while (tmp < input.length()) {
                        if (input.charAt(tmp) == '#') {
                            while(tmp < input.length() && input.charAt(tmp) != '\n') tmp++;
                        }
                        if (tmp >= input.length()) return count;

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
                        && input.startsWith("lang:fr", count)) {
                    Language.language = Languages.FRENCH;
                    count += 7;
                    int tmp = count;
                    while (tmp < input.length()) {
                        if (input.charAt(tmp) == '#') {
                            while(tmp < input.length() && input.charAt(tmp) != '\n') tmp++;
                        }
                        if (tmp >= input.length()) return count;

                        if (input.charAt(tmp) == '\n') {
                            return count;
                        } else if (input.charAt(tmp) != ' '
                                && input.charAt(tmp) != '\t') {
                            return 0;
                        }
                        tmp++;
                    }
                    return count;
                } else {
                    return 0;
                }
            }
            count++;
        }
        return 0;
    }
    public static int removeLeadingComments(String input) {
        int count = 0;
        while(count < input.length() && "\n\t# ".contains(Character.toString(input.charAt(count)))) {
            if (input.charAt(count) == '#') {
                if(input.length() > count + 2 && input.charAt(count + 1) == '#'
                        && input.charAt(count + 2) == '#') {
                    count += 3;
                    while (count < input.length()) {
                        if (count + 2 < input.length() && input.charAt(count) == '#'
                                && input.charAt(count + 1) == '#'
                                && input.charAt(count + 2) == '#') {
                            count+=2;
                            break;
                        }
                        count++;
                    }
                } else {
                    while (count < input.length() && input.charAt(count) != '\n') {
                        count++;
                    }
                }
            }
            count++;
        }
        return count;
    }
}
