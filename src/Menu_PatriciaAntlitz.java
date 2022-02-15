package src;

//PART 2
//
//Create second class that will be the Menu for your WordScramble Game object  name it Menu_yourName.java
public class Menu_PatriciaAntlitz {

    public void menuIntro(){
        System.out.println("\n**************************************************");
        System.out.println("                  WORD SCRAMBLER                  ");
        System.out.println("**************************************************");

        System.out.println("\nWelcome to WORD SCRAMBLER");

        System.out.println("\nInstructions:");
        System.out.println("This Game Has 3 Levels For The User to Choose From:");
        System.out.println("Level 1 = Beginner");
        System.out.println("Level 2 = Intermediate");
        System.out.println("Level 3 = Advanced");
        System.out.println("You Have Three Chances To Guess One Word and 10 Rounds To Complete The Game.");
        System.out.println("Not Guessing a Word Terminates The Round and Resets The Points.\n");
        System.out.println("It also allows the user to add a word to one of the levels, or to an example file.");
        System.out.println("Doing so, will terminate the program, as it should be the last or the first step.\n");
    }

    public void printMenu(){

        System.out.println("------------------------------------------------");
        System.out.println("|                    MENU:                     |");
        System.out.println("|----------------------------------------------|");
        System.out.println("|    - Press the Letter 'Q' to Exit The Game   |");
        System.out.println("|    - Press the Letter 'S' to Start           |");
        System.out.println("|    - Press the Letter 'P' to See Points      |");
        System.out.println("|    - Press the Letter 'W' to Add Words       |");
        System.out.println("|    - Press the Letter 'M' to See Menu        |");
        System.out.println("------------------------------------------------\n");

    }
}
