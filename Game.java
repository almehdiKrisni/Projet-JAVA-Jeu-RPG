import java.util.Scanner;

public class Game {
    public static void main(String[] args) {
        System.out.println("\t\t\t\t\t\t\tTHE BIZARRE ADVENTURE\n");
        Party p1 = new Party();

        Scanner scan = new Scanner(System.in);

        // Choix du mode de Jeu

        int gameMode = scan.nextInt(); System.out.println();

        if ( gameMode == 1) {
            return;
        }

        // Création de l'équipe

        
        System.out.println("You... you've finally opened your eyes. For a moment, I thought you were dead.\nI'm sorry to disturb your rest, but it seems like we are surrounded by monsters.\nThere are other people around us and we need to create the best team possible if we want a chance at survival.\nHow many people do you think we can add in our team ? You better choose wisely, otherwise we're done for.\n\nI'd say we can take 3 people at most in our team, looking at the provisions I've managed to gather.\n");
        int howmany = scan.nextInt(); System.out.println();

        int repetitionsStart = 0;
        while ((howmany > 3) && (repetitionsStart < 3)) {
            repetitionsStart++;
            System.out.println("Let me repeat myself. I said we can add 3 people AT MOST.\n");
            howmany = scan.nextInt(); System.out.println();
        }
        if (repetitionsStart == 3) {
            System.out.println("Looks like you don't listen to my orders... Oh god, the monsters are coming!\n\n\n\t\t\t\t\t\t\t\tGAME OVER");
            return;
        }

        if (howmany == 0) {
            System.out.println("Alone, huh.\nI'm not one to judge, don't worry...\n");
        }
        else if (howmany == 1) {
            System.out.println("What a cute couple the two of you make.\nI'm quite jealous of you...\n");
        }
        else if (howmany == 2) {
            System.out.println("'Jamais deux sans trois' like the French would say.\n");
        }
        else {
            System.out.println("Look at Mr.Popular over there with all his friends.\nYou'll soon understand that power isn't only about numbers...\n");
        }

        for (int i = 0; i < howmany + 1; i++) {
            if (i == 0) { System.out.println("So, tell me about yourself...\n"); }
            if (i != 0) { System.out.println("Tell me about your friend...\n"); }
            p1.addMember();
        }







        // Premier combat pour se familiariser avec le jeu

        System.out.println("Good... Now, let's test your abilities in battle.\nI'll assist you during your battles. Here, I'll let you handle these monstres.\n");

        Enemies e1 = new Enemies(p1.getAverageLevel(), (int)(Math.random() * 4 + 1));
        Fight f1 = new Fight(p1, e1);

        try { Thread.sleep(1500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

        f1.actionFight();






        // Après la fin du combat

        System.out.println("Good, good... Looks like we might have a chance after all. I'm impressed by the way you and your team handled this fight.\nBut don't get ahead of yourself, the monsters you've faced were quite weak compared to the ones near my hometown.\n");
        System.out.println("Excuse me, Mr.Invincible. Next time, why don't you handle these monsters all by yourself. (1)\nDo you have any potions? (2)\n");

        int choice1 = scan.nextInt(); System.out.println();

        if (choice1 == 1) {
            System.out.println("Look, I was kidding. I'm not even strong enough to take on a Slime.\nHere, take these but make sure to never mock me again.\n");
            HealingItem h1 = new HealingItem("Potion", 1, 20);
            for (int a1 = 0; a1 < 5; a1++) p1.addItem(h1);
            System.out.println();
            System.out.println("I've also found this Iron Sword. Should we give it to someone?\n");
            Weapon w = new Weapon(4, 2, "Iron Sword", "A basic Iron Sword.");
            p1.equipWeapon(w);
        }
        else if (choice1 == 2) {
            System.out.println("Here, take these but make sure to stay alive during the next fights... I have plans for you.\n");
            HealingItem h1 = new HealingItem("Potion", 1, 20);
            for (int a1 = 0; a1 < 3; a1++) p1.addItem(h1);
        }
        
    }
}