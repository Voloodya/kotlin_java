package com.kotlin.java;

import com.kotlin.java.kotlin.InfixKt;
import com.kotlin.java.kotlin.extend.JoinKt;
import com.kotlin.java.kotlin.extend.StringPropertyKt;

import java.util.List;

import static com.kotlin.java.kotlin.FunctionKt.greetUser;
import static com.kotlin.java.kotlin.strings.JoinFunctions.joinToString;


public class KotlinJavaApplication {

    public static void main(String[] args) {

        greetUser();

        List<Integer> list = List.of(1, 2, 3, 4, 5);

        System.out.println(joinToString(list));

        System.out.println(joinToString(list, "/", "("));

        System.out.println(JoinKt.joinToString(list, ",", "{"));

        List<String> listString = List.of("a", "b", "c", "d", "f");

        System.out.println(JoinKt.joinToStr(listString, ",", "{", "}"));


        System.out.println(StringPropertyKt.getLastChar("Kotlin?!"));

        var infixFunResult = InfixKt.myTo(1, "one");

        System.out.println(infixFunResult);
    }
}
