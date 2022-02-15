package src;
/* LAB 7 - Word Scramble Game - OVERALL GOAL:  Create a game where the player can guess scrambled words.

You may NOT use the solution in the Recursion chapter in Zybooks.  If you are wondering how to scramble the letters
look at Zybooks chapter 6.14 on String access modifications.

(future additions: Reading in multiple words from a Vector,  adding a menu, adding a player object)

Name: Patricia Antlitz
Date: 10/24/2021
Class: CIS-160 Computer Science I - NECC Fall 2021
Professor: Kristen Sparrow
 */

/**
 * This program will take words from a given text file
 * The words will be scrambled by the program
 * and the user will be allowed to guess what the word is based on the letters in the word
 * There will be three chances to guess to earn a point
 * The game is won when the majority of the words were guessed correctly
 * * @author Patricia N. Antlitz
 *  * @version 8.0
 */
//import all necessary functions
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
//
// Create a class WordScramble that has the  methods:
public class WordScramble_PatriciaAntlitz {
    // GLOBAL VARIABLES
    //scan
    Scanner scnr = new Scanner(System.in);
    //to get a file
    FileInputStream fileByteStream = null;
    //starting scanner
    Scanner inputFile = null;
    //to print into a file
    PrintWriter outputWord = null;
    //arrayList used
    ArrayList <String> fileWords;
    //variable for comparison
    String originalWord = "";
    String singleWord = "";
    //user's variable
    String userEntry;
    //accumulated points for the game
    int userPoints;
    //used to end the game
    boolean endGame = false;
    //will hold the username entered by the user
    String userName;

    //saves user input to a text file using FileInputStream objects, this method won't be used in the final guessing
    public void writeWordsToFile(String fileName){
        //will hold the number of words the user wants to enter
        int wordsNum;
        //tells the user to add words
        System.out.println("\nYou can help us improve this game by suggesting your own words.\n");
        System.out.println("How many words would you like to enter?");
        //get number if inputs
        wordsNum = scnr.nextInt();
        //assign the size of the array
        fileWords = new ArrayList<>(wordsNum);
        //ask the user to enter the words
        System.out.println("Enter examples of words that can be used in this game:\n");
        //as long as the user selected to enter 1 or more words
        if(wordsNum > 0) {
            //add each word to the arraylist
            for (int i = 0; i < wordsNum; i++) {
                fileWords.add(scnr.next());
            }
        }
        /* FileWriter and BufferedWriter will allow the words to be appended to a document
         * instead of replacing the text
        */
        try {
            FileWriter fileOutput = new FileWriter(fileName, true);
            BufferedWriter bufferWords = new BufferedWriter(fileOutput);
            outputWord = new PrintWriter(bufferWords);

            System.out.println("\nYou Entered:\n");
            //prints the words entered by the user
            for(int i = 0; i < fileWords.size(); i++){
                String newWord = fileWords.get(i);
                System.out.print("- " + newWord + " - ");
                //prints them on file
                outputWord.println(newWord);
            }
            System.out.println("\n");
            //closes function
            outputWord.close();
        }//error handling
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /* reads a word from a text file using FileInputStream objects (Zybooks 5.5.4)
     * Should take in at least 5 four letters words and store them in a Vector or ArrayList (10.8 zybooks)
    */
    public void readWordFromFile(String filePath){
        //set the array
        fileWords = new ArrayList<>();
        //gets file, passing the parameter since there are three files to choose from
        try {
            fileByteStream = new FileInputStream(filePath);
            inputFile = new Scanner(fileByteStream);
            //splits the words
            while (inputFile.hasNext()) {
                String[] str = inputFile.next().split("d*[.@:=#-]");
                //add them to array, now each word is separate
                for(int i = 0; i < str.length; i++){
                    if(!str[i].isEmpty()){
                        fileWords.add(str[i]);
                    }
                }
            }
            //closing file input
            fileByteStream.close();
        } //error handling
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }

    //will get a random word from the file
    public String returnSingleWord() {
        //as long as the array is not empty
        if(fileWords != null && !fileWords.isEmpty()) {
            //get random number as index
            int randomIndex = (int) (Math.random() * fileWords.size());
            //get the word on that index
            singleWord = fileWords.get(randomIndex);
        }
        //returns that one word
        return singleWord;
    }

    //shifting each character
    public String shuffleChars(String singleWord, int firstIdx, int nextIdx){
        //uses the returned word to separate the characters
        char[] lettersArr = singleWord.toCharArray();
        //basic comparison and switching algorithm
        char temp = lettersArr[firstIdx];
        lettersArr[firstIdx] = lettersArr[nextIdx];
        lettersArr[nextIdx] = temp;
        return new String(lettersArr);
    }

    /* scrambles the word read from the file - SCRAMBLES EACH WORD FROM THE VECTOR or Array List
     * print word with shifted letters
    */
    public void scrambleWord() {
        //these two variables will store the same word in order to compare them later
        singleWord = returnSingleWord();
        originalWord = singleWord;
        int wordSize;
        //gets the size of the word
        wordSize = singleWord.length();
        //loops the each letter
        for(int i = 0; i < wordSize; i++){
            //shuffles
            int firstIdx = ThreadLocalRandom.current().nextInt(0, wordSize);
            int nextIdx = ThreadLocalRandom.current().nextInt(0, wordSize);
            //new word, shuffled
            singleWord = shuffleChars(singleWord, firstIdx, nextIdx);
            /*When the user select the first file (level 1) the word are mainly simple and short,
            * that can cause the program to shift the words to the same original location, meaning the
            * user will see what word it is. This if statement will make sure that does not happen.
            * If the word happens to be the same, the loop will run again until the word is different*/
            if(singleWord.equals(originalWord)){
                //run again
                i = 0;
            }
            else { //otherwise end the loop
                break;
            }
        }
    }

    //method will compare the user's entry with the original word, three chances
    public void compareWords(){
        //loop variables
        int i = 0;
        int j = 0;
        //initiates game's points
        userPoints = 0;

        //first loop, 10 rounds
        while(i >= 0 && i < 10){

            if(endGame){
                break;
            }
            //inner loop will allow the user to guess three times
            while(j < 3) {
                //prints the scrambles word generated by the previous method
                System.out.println(singleWord);
                System.out.print("Type Your Guess:\n");
                //gets user's word
                userEntry = scnr.nextLine();

                //correct answer, if user's entry is the same as the original word (before scrambled)
                if (userEntry.equals(originalWord)) {
                    System.out.println("Correct!");
                    userPoints++;
                    System.out.println("Accumulated Points: " + userPoints + "\n");
                    //will select a new word by running the previous method again
                    System.out.println("New Word:");
                    scrambleWord();
                    //this is the end of this round
                    break;
                }
                else {
                    //if the entry does not match the user will be asked to try again three times
                    System.out.println("Incorrect Entry. Play again!\n");
                }
                //continue until 3 is reached
                j++;
            }
            //continues until loop breaks
            i++;
        }

        if(!endGame) {
            System.out.println("The word was: " + originalWord + "\n");
        }
    }

    /* asks the user to guess the word - checks original word against the scramble word
     * things to consider: how many guesses, points
     * Save the file as WordScramble_yourName.java
    */
    public void playGame(){
        //variable for looping
        int gameLevels = 0;
        //user entry
        char pickALevel;
        //allows the user to pick three levels
        System.out.println("Pick you level (1, 2, or 3): ");
        System.out.println("To see the menu select 'M'");
        pickALevel = scnr.nextLine().charAt(0);
        //this condition doesnt really mean anything
        while(gameLevels <= 0){

            if (pickALevel == 'm' || pickALevel == 'M') {
                //calls the menu method
                endGame = true;
                selectGame();

            }
            //if user selects m
            else if(pickALevel == '1'){
                //level "beginner"
                //calls the file to read the words
                readWordFromFile("level1.txt");
            }//2, intermediate
            else if (pickALevel == '2'){
                readWordFromFile("listIntermediate.txt");
            }//3 advanced
            else if(pickALevel == '3'){
                readWordFromFile("listAdvance.txt");
            }
            else {
                //in case the user types something else
                System.out.println("Incorrect Entry. Please select 1, 2 or 3 to pick a level");
                System.out.println("To see the menu select 'M'");
                pickALevel = scnr.nextLine().charAt(0);
                continue;
            }
            gameLevels++;
        }
        //scramble the words
        scrambleWord();
        compareWords();
    }

    //this method will ask the user to type their name, to be used by the inherited class
    public void getNameUser(){

        System.out.println("Enter Player's Name:");
        userName = scnr.nextLine();
        System.out.println("Hello, " + userName + "!");

    }

    //running menu and game
    public void selectGame(){
        //user choice's variables
        char userChoice;
        int addWord;
        //variable used for looping
        int i = 0;

        // OBJECTS
        //inherited - Part 3 Super class object, to be initiated inside option 'p'
        Player_PatriciaAntlitz getName = new Player_PatriciaAntlitz(userName, userPoints);

        // MENUS
        Menu_PatriciaAntlitz printTheMenu = new Menu_PatriciaAntlitz();
        printTheMenu.printMenu();
        //add word menu, used and initiated inside of option 'w'
        WriteFileMenu_PatriciaAntlitz menuForWriting = new WriteFileMenu_PatriciaAntlitz();

        //asks the user to enter one of the menu choices
        System.out.println("Enter your choice:");
        userChoice = scnr.nextLine().charAt(0);

        //runs until it reaches a break statement
        while(i >= 0){
            //if the user chooses 0 the program breaks;
            if(userChoice == 'q' || userChoice == 'Q'){
                System.out.println("Ending Program. Goodbye!\n");
                break;
            }
            else if(userChoice == 's' || userChoice == 'S'){ //starts the game
                endGame = false;
                playGame();
//                System.out.println("Points: " + userPoints + "\n");
                //breaks if endGame is true
                if(endGame) {
                    break;
                }

            }
            else if(userChoice == 'p' || userChoice =='P') { //print the total points
                getName.userScore(userPoints);
                System.out.println("\nEnter Next Menu Choice:");
                userChoice = scnr.nextLine().charAt(0);
            }
            else if(userChoice == 'w' || userChoice == 'W'){ //add words to selected file
                //allows for the user to pick which file they will be adding to
                int j = 0;
                /* this while loop will allow the user to run the menu
                 * It also check if the user's entry is an int and not a char, since the previous scan used a char
                 */
                while(j >= 0) {
                    menuForWriting.writeIntoFile();
                    //return boolean to determine whether or not the entry is an int
                    while (!scnr.hasNextInt()) {
                        //loop will never end until the user types a number
                        System.out.println("Enter a number");
                        scnr.nextLine();
                    }
                    //gets the number
                    addWord = scnr.nextInt();
                    //breaks the program
                    if (addWord == 4) {
                        System.out.println("Ending Program. Goodbye!\n");
                        break;
                    }
                    //files to choose from
                    if (addWord == 1) {
                        //runs the method to write into one of the files
                        writeWordsToFile("level1.txt");
                    } else if (addWord == 2) {
                        writeWordsToFile("listIntermediate.txt");
                    } else if (addWord == 3) {
                        writeWordsToFile("listAdvance.txt");
                    } else if (addWord == 0) {
                        writeWordsToFile("playerFile.txt");
                    } else {
                        System.out.println("Error, Select Again:");
                    }
                    j++;
                }
                break;
            }
            else if(userChoice == 'm' || userChoice == 'M'){ //gets the menu
                //gets the menu
                printTheMenu.printMenu();
                System.out.println("Enter your choice:");
                userChoice = scnr.nextLine().charAt(0);
            }
            else {
                //error handling
                System.out.println("INCORRECT ENTRY");
                System.out.println("Enter your choice:");
                userChoice = scnr.nextLine().charAt(0);
            }
            i++;
        }
    }

    public static void main(String[] args){
        //running the methods
        WordScramble_PatriciaAntlitz scrambleGame = new WordScramble_PatriciaAntlitz();
        Menu_PatriciaAntlitz printMenu = new Menu_PatriciaAntlitz();

        printMenu.menuIntro();
        scrambleGame.getNameUser();
        scrambleGame.selectGame();
    }
}
// end of program.
