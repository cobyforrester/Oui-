package com.ouiplusplus.start;
import com.ouiplusplus.error.Error;
import com.ouiplusplus.error.StackOverflow;
import com.ouiplusplus.helper.Pair;
import com.ouiplusplus.lexer.Run;


public class ServerStart {
    public static String process(String input) {
        try{
            Run run = new Run();
            input = input.substring(Helper.setLanguage(input));
            Pair<String, Error> pair = run.generateOutput("main.ouipp", input);
            Error error = pair.getP2();
            if(error != null) {
                return error.toString();
            } else {
                return pair.getP1();
            }
        } catch (StackOverflowError soe) {
            Error err = new StackOverflow();
            return err.toString();
        } catch(Exception e) {
            return e.toString();
        }

    }
}
