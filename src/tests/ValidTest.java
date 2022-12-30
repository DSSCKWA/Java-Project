package src.tests;

import src.validator.Validator;

public class ValidTest {
    public static void main(String[] args) {
        if(Validator.validMail("k.kierepka01@gmail.com")) {
            System.out.println("OK");
        }
        else {
            System.out.println("Err1");
        }

        if(Validator.validMail("k.kierepka01gmail.com")) {
            System.out.println("Err2");
        }
        else {
            System.out.println("OK");
        }

        if(Validator.validPhone("695542293")) {
            System.out.println("OK");
        }
        else {
            System.out.println("Err3");
        }

        if(Validator.validPhone("k.kierepka01gmail.com")) {
            System.out.println("Err4");
        }
        else {
            System.out.println("OK");
        }

        if(Validator.validPhone("6955422934")) {
            System.out.println("Err4");
        }
        else {
            System.out.println("OK");
        }

        if(Validator.validPhone("095542293")) {
            System.out.println("Err5");
        }
        else {
            System.out.println("OK");
        }
    }
}
