package org.hk;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to player popularity survey!");
        List<FanVote> fanVotes = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Please enter your vote <first_choice> <second_choice> <third_choice>:");
            String line = scanner.nextLine();
            System.out.println("Your vote is: " + line);
            FanVote fanVote = getFanVote(line);
            if(isValidVote(fanVote)) {
                System.out.println("Thank you for voting! Your vote is valid and will be counted!");
                fanVotes.add(fanVote);
            }
            else {
                System.out.println("Your vote is invalid and will not be counted! a valid vote must have 3 unique choices between 1 and 20");
            }
            if(isExistCondition(fanVote)) {
                System.out.println("Exit condition. No more votes!");
                break;
            }
        }
        int winner = getWinner(fanVotes);
        System.out.println("The winner is: " + winner);
    }

    private static FanVote getFanVote(String line) {
        String[] votes = line.split(" ");
        int firstChoice = Integer.parseInt(votes[0]);
        int secondChoice = Integer.parseInt(votes[1]);
        int thirdChoice = Integer.parseInt(votes[2]);
        return new FanVote(firstChoice, secondChoice, thirdChoice);
    }

    private static boolean isExistCondition(FanVote fanVote) {
        return (fanVote.firstChoice() == 0 && fanVote.secondChoice() == 0 && fanVote.thirdChoice() == 0);
    }

    private static boolean isValidVote(FanVote fanVote) {
        return (isAllChoicesUnique(fanVote) && isAllChoicesInRange(fanVote));
    }

    private static boolean isAllChoicesUnique(FanVote fanVote) {
        return (fanVote.firstChoice() != fanVote.secondChoice() && fanVote.firstChoice() != fanVote.thirdChoice() && fanVote.secondChoice() != fanVote.thirdChoice());
    }

    private static boolean isAllChoicesInRange(FanVote fanVote) {
        return (isChoiceInRange(fanVote.firstChoice()) &&
                isChoiceInRange(fanVote.secondChoice()) &&
                isChoiceInRange(fanVote.thirdChoice()));
    }

    private static boolean isChoiceInRange(int choice) {
        return (choice >= 1 && choice <= 20);
    }

    private static int getWinner(List<FanVote> fanVotes) {
        System.out.println("Counting the votes...");
        Map<Integer, Integer> playerScores = new HashMap<>();
        for(int i=1; i<=20; i++) {
            playerScores.put(i, 0);
        }
        for(FanVote fanVote : fanVotes) {
            int firstChoice = fanVote.firstChoice();
            playerScores.put(firstChoice, playerScores.get(firstChoice)+3);

            int secondChoice = fanVote.secondChoice();
            playerScores.put(secondChoice, playerScores.get(secondChoice)+2);

            int thirdChoice = fanVote.thirdChoice();
            playerScores.put(thirdChoice, playerScores.get(thirdChoice)+1);
        }
        int maxPlayerScore = 0;
        int winner = 0;
        for(int i=1; i<=20; i++) {
            int playerScore = playerScores.get(i);
            if(playerScore >= maxPlayerScore) {
                winner = i;
                maxPlayerScore = playerScore;
            }
        }
        System.out.println("The Highest popularity score is: " + maxPlayerScore + " and the player who got it is player number: " + winner);
        return winner;
    }

    private record FanVote(int firstChoice, int secondChoice, int thirdChoice) {
    }
}