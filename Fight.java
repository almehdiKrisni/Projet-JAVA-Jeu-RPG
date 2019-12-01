import java.util.Scanner;

public class Fight {
    private int Turn;
    private Party allies;
    private Enemies enemies;

    public Fight(Party allies, Enemies enemies){
        this.Turn = 1;
        this.allies = allies;
        this.enemies = enemies;
    }

    // Accesseurs aux éléments d'un objet de la classe

    public int getTurn() { return this.Turn; }

    public Party getParty() { return this.allies; } 
    
    public Enemies getEnemies() { return this.enemies; }

    // Méthode servant à simuler les combats

    public void actionFight() {
        System.out.println("Turn" + this.Turn + "\n");
        System.out.println(this.allies + "\n" + this.enemies);
        while (!this.enemies.haveBeenDefeated()) {
            for (Character c : this.allies.getTeam()) {
                if (c.getIsDead() == false) {
                    System.out.println(c.getName() + " is waiting for orders ...\n");

                    Scanner scan = new Scanner(System.in);
                    int pos = scan.nextInt();

                    c.actionCombat(this.enemies, pos);

                    if (this.enemies.haveBeenDefeated()) {
                        double r = Math.random();
                        if (r < 0.33) System.out.println("The enemies have been defeated! Let's move on...\n");
                        else if (r < 0.67) System.out.println("Victory! To the next figth...\n");
                        else System.out.println("That settles it! It's crazy how strong we are...\n");
                        return;
                    }
                }
            }
        }
    }
}