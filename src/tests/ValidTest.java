package src.tests;

import src.validator.Validator;

public class ValidTest {
    public static void main(String[] args) {
        if (Validator.isValidMail("k.kierepka01@gmail.com")) {
            System.out.println("OK");
        } else {
            System.out.println("Err1");
        }

        if (Validator.isValidMail("k.kierepka01gmail.com")) {
            System.out.println("Err2");
        } else {
            System.out.println("OK");
        }

        if (Validator.isValidPhone("695542293")) {
            System.out.println("OK");
        } else {
            System.out.println("Err3");
        }

        if (Validator.isValidPhone("k.kierepka01gmail.com")) {
            System.out.println("Err4");
        } else {
            System.out.println("OK");
        }

        if (Validator.isValidPhone("6955422934")) {
            System.out.println("Err4");
        } else {
            System.out.println("OK");
        }

        if (Validator.isValidPhone("095542293")) {
            System.out.println("Err5");
        } else {
            System.out.println("OK");
        }
    }
}
