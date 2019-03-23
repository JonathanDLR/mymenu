package com.ocr.anthony;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.APPEND;

public class Order {
    Scanner sc = new Scanner(System.in);
    String orderSummary = "";

    /**
     * Display all available menus in the restaurant.
     */
    public void displayAvailableMenu() {
        System.out.println("Choix menu");
        System.out.println("1 - Poulet");
        System.out.println("2 - Boeuf");
        System.out.println("3 - Végétarien");
        System.out.println("Quel menu voulez vous?");

    }
    /**
     * Display a selected menu.
     * @param nbMenu The selected menu.
     */
    public void displaySelectedMenu(int nbMenu) {
        switch (nbMenu) {
            case 1:
                System.out.println("Vous avez choisi comme menu: poulet");
                break;
            case 2:
                System.out.println("Vous avez choisi comme menu: boeuf");
                break;
            case 3:
                System.out.println("Vous avez choisi comme menu: végétarien");
                break;
            default:
                System.out.println("Vous n'avez pas choisi de menu parmi les choix proposés");
                break;
        }
    }

    /**
     * Run asking process for a menu
     */
    public String runMenu() {
        int nbMenu = askMenu();
        int nbSide = -1;
        int nbDrink = -1;
        switch (nbMenu) {
           case 1:
                nbSide = askSide(true);
                nbDrink= askDrink();
                break;
           case 2:
                nbSide = askSide(true);
                break;
           case 3:
                nbSide = askSide(false);
                nbDrink = askDrink();
                break;
        }
        return nbMenu + "," + nbSide + "," + nbDrink + "%n";
    }

    /**
     * Display a selected side depending on all sides enable or not.
     * All sides = vegetables, frites and rice
     * No all sides = rice or not
     * @param nbSide The selected Side
     * @param allSidesEnable  enable display for all side or not
     */
    public void displaySelectedSide(int nbSide, boolean allSidesEnable) {
        if (allSidesEnable) {
            switch (nbSide) {
                case 1:
                    System.out.println("Vous avez choisi comme accompagnement: légumes frais");
                    break;
                case 2:
                    System.out.println("Vous avez choisi comme accompagnement: frites");
                    break;
                case 3:
                    System.out.println("Vous avez choisi comme accompagnement: riz");
                    break;
                default:
                    System.out.println("Vous n'avez pas choisi d'accompagnement parmi les choix proposés");
                    break;
            }
        } else {
                switch (nbSide) {
                    case 1:
                        System.out.println("Vous avez choisi comme accompagnement: riz");
                        break;
                    case 2:
                        System.out.println("Vous avez choisi comme accompagnement: pas de riz");
                        break;
                    default:
                        System.out.println("Vous n'avez pas choisi d'accompagnement parmi les choix proposés");
                        break;
                }
        }
    }

    /**
     * Display a selected drink
     * @param nbDrink The Drink Selected
     */
    public void displaySelectedDrink(int nbDrink) {
        switch (nbDrink) {
            case 1:
                System.out.println("Vous avez choisi comme boisson: Eau");
                break;
            case 2:
                System.out.println("Vous avez choisi comme boisson: Eau Gazeuse");
                break;
            case 3:
                System.out.println("Vous avez choisi comme boisson: Soda");
                break;
            default:
                System.out.println("Vous n'avez pas choisi de boisson parmi les choix proposés");
                break;

        }
    }

    /**
     * Display all available side depending on all sides enable or not.
     * All sides = vegetables, frites and rice
     * No all sides = rice or not
     * @param allSideEnable enable display for all side or not
     */
    public void displayAvailableSide(boolean allSideEnable) {
        System.out.println("Choix accompagnement");
        if (allSideEnable) {
            System.out.println("1 - légumes frais");
            System.out.println("2 - frites");
            System.out.println("3 - riz");
        } else {
            System.out.println("1 - riz");
            System.out.println("2 - pas de riz");
        }
        System.out.println("Que souhaitez vous comme accompagnement?");
    }

    /**
     * Display all availables drinks in the restaurant
     */
    public void displayAvailableDrink() {
        System.out.println("Choix boisson");
        System.out.println("1 - Eau");
        System.out.println("2 - Eau Gazeuse");
        System.out.println("3 - Soda");
        System.out.println("Que souhaitez vous comme boisson?");
    }

    /**
     * Run asking process for several menus
     */
    public void runMenus() {
        Path orderPath = Paths.get("order.csv");
        System.out.println("Combien voulez vous de menu?");
        int menuQuantity = -1;
        boolean responseIsGood;
        do {
            try {
                menuQuantity = sc.nextInt();
                responseIsGood = true;
            } catch (InputMismatchException e) {
                sc.next();
                System.out.println("Vous devez saisir un nombre, correspondant au nombre de menu souhaité");
                responseIsGood = false;
            }
        } while (!responseIsGood);
        orderSummary = "Résumé de votre commande: %n";
        for (int i = 0; i < menuQuantity; i++) {
            orderSummary += "Menu " + (i + 1) + ":%n";
            String orderLine = runMenu();
            try {
                Files.write(orderPath, String.format(orderLine).getBytes(), APPEND);
            } catch (IOException e) {
                System.out.println("Erreur survenu, Merci de rééessayer plus tard");
                return;
            }
        }
        System.out.println("");
        System.out.println(String.format(orderSummary));
    }

    /**
     * Display question about a category in the standard input, get and display response
     * @param category the category
     * @param responses available response
     */
    public int askSomething(String category, String[] responses) {
        System.out.println("Choix " + category);
        for (int i = 1; i <= responses.length; i++) {
            System.out.println(i + "-" + responses[i - 1]);
        }
        System.out.println("Que souhaitez vous comme " + category + "?");
        int nbResponse = 0;
        boolean responseIsGood;
        do {
            try {
                nbResponse = sc.nextInt();
                responseIsGood = (nbResponse >= 1 && nbResponse <= responses.length);
            } catch (InputMismatchException e) {
                sc.next();
                responseIsGood = false;
            }
            if (responseIsGood) {
                String choice = "Vous avez choisi comme " + category + ": " + responses[nbResponse - 1];

                orderSummary += choice + "%n";
                System.out.println(choice);
            } else {
                boolean isVowel = "aeiouy".contains(Character.toString(category.charAt(0)));
                if (isVowel) {
                    System.out.println("Vous n'avez pas choisi d'" + category + " parmi les choix proposés");
                } else {
                    System.out.println("Vous n'avez pas choisi de " + category + " parmi les choix proposés");
                }
            }
        } while (!responseIsGood);
        return nbResponse;
    }


    /**
     * Display question about menu in standard input, get response and display it
     */
    public int askMenu() {
        String[] menus = {"poulet", "boeuf", "végétarien"};
        int nbMenu = askSomething("menu", menus);
        return nbMenu;
    }

    /**
     * Display question about side in standard input, get response and display it
     * @param allSideEnable display all available side
     */
    public int askSide(boolean allSideEnable) {
        if (allSideEnable) {
            String[] responseAllSides = {"légumes frais", "frites", "riz"};
            return askSomething("accompagnement", responseAllSides);
        } else {
            String[] responseOnlyRice = {"riz", "pas de riz"};
            return askSomething("accompagnement", responseOnlyRice);
        }
    }

    /**
     * Display question about drink in standard input, get response and display it
     */
    public int askDrink() {
        String[] drinks = {"Eau", "Eau Gazeuse", "Soda"};
        return askSomething("boisson", drinks);
    }
}