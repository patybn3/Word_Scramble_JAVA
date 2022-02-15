package src;
//Create a third class that will represent the Player object it should inherit from the Person class.
//Write 2 classes Player_yourName.java and Person_yourName.java

public class Person_PatriciaAntlitz {
    String userName;
    int allPoints;

    public Person_PatriciaAntlitz(String name){
        this.userName = name;
    }

    public void userScore(int userScore){
        this.allPoints = userScore;

        System.out.println(userName + "'s Points: " + allPoints);
    }

}
