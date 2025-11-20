package com.wavebreaker.utils;

import java.util.Scanner;

public class Input {
  private static final Scanner scanner = new java.util.Scanner(System.in);

  public static String get(String prompt) {
    System.out.println(prompt);
    return scanner.nextLine();
  }
}
