import java.util.ArrayList;

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