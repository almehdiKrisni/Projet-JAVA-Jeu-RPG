import java.util.ArrayList;
import java.util.Scanner;

public class Party {
    private ArrayList<Character> Team;
    private Inventory inventory;

    // Je limite l'équipe à 4 membres

    private static final int maxTeamSize = 4;

    // Constructeur de la classe

    public Party() {
        this.inventory = new Inventory();
        this.Team = new ArrayList<Character>();
    }

    // Méthodes de la classe (ajout de personnages ou d'objets)

    public void addMember() {
        if (this.Team.size() == maxTeamSize) {
            System.out.println("The team is already full (only 4 members are accepted).\n");
            return;
        }
        
        Scanner scanner = new Scanner(System.in);
        ArrayList<Integer> liste_choix = new ArrayList<Integer>();
        for (int v = 1; v <= 4; v++) { liste_choix.add(v); }

        for (int i = 0; i < 4; i++) {
            System.out.println("Enter your name :");
            String name = scanner.next();

            System.out.println("\nChoose your class :\n1 - Sweeper (High base Speed and Attack)\n2 - Tank (High base HP and Defense)\n3 - Mage (Ignore the enemy's defense, low base HP and DEF)\n4 - Healer (Heals an ally or inflict low damage to enemies)\n");
            int v = scanner.nextInt();

            while ( liste_choix.contains(v) == false) {
                System.out.println("\nError while choosing your class (wrong value chosen)\nChoose your class :\n1 - Sweeper (High base Speed and Attack)\n2 - Tank (High base HP and Defense)\n3 - Mage (Ignore the enemy's defense, low base HP and DEF)\n4 - Healer (Heals an ally or inflict low damage to enemies)\n");
                v = scanner.nextInt();
            }

            if ( v == 1) { Sweeper c = new Sweeper(name); this.Team.add(c); }
            else if ( v == 2 ) { Tank c = new Tank(name); this.Team.add(c); }
            else if ( v == 3 ) { Mage c = new Mage(name); this.Team.add(c); }
            else { Healer c = new Healer(name); this.Team.add(c); }
        }

        scanner.close();
    }

    public void addMember(Character c) {
        if (this.Team.size() == maxTeamSize) System.out.println("The team is already full (only 4 members are accepted).\n");
        else {
            this.Team.add(c);
            System.out.println(c.getName() + " has joined the team !\n");
        }
    }

    public void addItem(Item i) {
        this.inventory.addItem(i);
    }

    // Accesseurs aux éléments d'un objet de la class Party

    public ArrayList<Character> getTeam() { return this.Team; }

    public Inventory getInventory() { return this.inventory; }

    // Accès au niveau moyen de l'équipe

    public int getAverageLevel() {
        int l = 0;
        for (Character c : this.Team) {
            l = l + c.getLEVEL();
        }
        return (int)(l / 4);
    }

    // Méthode toString de la classe

    public String toString() {
        String text = "";
        for (Character c : this.Team) {
            text = text + c.toString() + "\n";
        }
        return text + this.inventory.toString();
    }
}