package io;

import java.util.Scanner;
import java.util.regex.Pattern;

public class InputData {
    public static String scanString(String str) {
        String name = "";
        Scanner scanName = new Scanner(System.in);
        System.out.print(str);
        if (scanName.hasNextLine()) {
            name = scanName.nextLine();
        }
        return name;
    }

    public static String scanBirthday() {
        String birthday = "";
        Scanner scanBirthday = new Scanner(System.in);
        String pattern = "((((19|20)\\d{2})-(0(1|[3-9])|1[012])-(0[1-9]|[12]\\d|30))|(((19|20)\\d{2})-(0[13578]|1[02])-31)|(((19|20)\\d{2})-02-(0[1-9]|1\\d|2[0-8]))|((((19|20)([13579][26]|[2468][048]|0[48]))|(2000))-02-29))$";
        do {
            System.out.print("Enter birthday(yyyy-mm-dd):");
            if (scanBirthday.hasNextLine()) {
                birthday = scanBirthday.nextLine();
            }
        } while (!Pattern.matches(pattern, birthday));
        return birthday;
    }

    public static int scanInt(String str) {
        String n = "";
        System.out.println(str);
        n = isInteger(n);
        return Integer.parseInt(n);

    }

    public static String scanTime(String str){
        String time = "";
        System.out.println(str);
        time = isTime(time);
        return time;
    }

    public static int scanHour(String str){
        String n = "";
        System.out.println(str);
        n = isHour(n);
        return Integer.parseInt(n);
    }

    public static int scanMinute(String str){
        String n = "";
        System.out.println(str);
        n = isMinute(n);
        return Integer.parseInt(n);
    }
    public static String scanYN(String str){
        String n = "";
        System.out.println(str);
        n = isYN(n);
        return n;
    }

    public static String isTime(String str){
        String result;
        Pattern pattern = Pattern.compile("^([0-1][0-9]|2[0-3]):([0-5][0-9])$");
        if (!pattern.matcher(str).matches()) {
            System.out.print("Please Enter time (hh:mm):");
            Scanner sc = new Scanner(System.in);
            result = isTime(sc.next());
        } else {
            result = str;
        }
        return result;
    }

    public static String isHour(String str){
        String result;
        Pattern pattern = Pattern.compile("^([0-1]?[0-9]|2[0-3])$");
        if (!pattern.matcher(str).matches()) {
            System.out.print("Please Enter between 0-24:");
            Scanner sc = new Scanner(System.in);
            result = isHour(sc.next());
        } else {
            result = str;
        }
        return result;
    }

    public static String isMinute(String str){
        String result;
        Pattern pattern = Pattern.compile("^([0-5]?[0-9])$");
        if (!pattern.matcher(str).matches()) {
            System.out.print("Please Enter between 0-59:");
            Scanner sc = new Scanner(System.in);
            result = isMinute(sc.next());
        } else {
            result = str;
        }
        return result;
    }
    public static String isInteger(String str) {
        String result;
        Pattern pattern = Pattern.compile("^[1-9][\\d]*$");
        if (!pattern.matcher(str).matches()) {
            System.out.print("Please Enter a number positive:");
            Scanner sc = new Scanner(System.in);
            result = isInteger(sc.next());
        } else {
            result = str;
        }
        return result;
    }
    public static  String isYN(String str){String result;
        Pattern pattern = Pattern.compile("^[ynYN]$");
        if (!pattern.matcher(str).matches()) {
            System.out.print("(y/n):");
            Scanner sc = new Scanner(System.in);
            result = isYN(sc.next());
        } else {
            result = str;
        }
        return result;}
}
