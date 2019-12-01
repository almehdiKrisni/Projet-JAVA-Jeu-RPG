import java.util.ArrayList;
import java.util.Scanner;

public class Enemies {
    private int teamSize;
    private ArrayList<Mob> enemies;

    public Enemies(int lvl) {
        this.teamSize = (int)(Math.random() * 4) + 1;
        this.enemies = new ArrayList<Mob>();
        int sn = 0;
        while (sn < this.teamSize) {
            Mob m = new Mob(lvl);
            while (m.getTeamSpace() + sn > teamSize) {
                m = new Mob(lvl);
            }
            this.enemies.add(m);
            sn = sn + m.getTeamSpace();
        }
    }

    public Enemies(int lvl, int taille) {
        this.teamSize = taille;
        this.enemies = new ArrayList<Mob>();
        int sn = 0;
        while (sn < this.teamSize) {
            Mob m = new Mob(lvl);
            while (m.getTeamSpace() + sn > teamSize) {
                m = new Mob(lvl);
            }
            this.enemies.add(m);
            sn = sn + m.getTeamSpace();
        }
    }

    // Affichage de l'équipe adverse

    public String toString() {
        String text = "";
        int n = 1;
        for (Mob m : this.enemies) {
            text = text + m.getName() + " (Level " + m.getLEVEL() + ") {" + n + "}\n";
            n++;
        }
        return text;
    }

    // Supression d'un ennemi de l'équipe adverse (quand il est vaincu)

    public void deleteMob(Mob m) { this.enemies.remove(m); }

    // Accesseurs aux monstres de l'équipe et à sa taille

    public Mob getMobInPos(int pos) {
        if (pos > this.enemies.size() + 1) { 
            ArrayList<String> liste = new ArrayList<String>();
            for (int i = 1; i < this.enemies.size() + 1; i++) {
                liste.add(String.format("%s", i));
            }

            System.out.println("Wrong position, please choose again.\n"); 
            System.out.println(this);
            Scanner scan = new Scanner(System.in);
            String v = scan.next();

            while ( liste.contains(v) != true) {
                System.out.println("Wrong position, please choose again.\n"); 
                System.out.println(this);
                v = scan.next();
            }

            scan.close();

            return this.enemies.get(Integer.parseInt(v.trim()) - 1);
        }

        return this.enemies.get(pos - 1);
    }

    public int getTeamSize() { return this.teamSize; }
}