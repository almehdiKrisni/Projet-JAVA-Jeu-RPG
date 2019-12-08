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

    public Enemies(int lvl, String name) {
        this.teamSize = 4;
        this.enemies = new ArrayList<Mob>();
        this.enemies.add(new Mob(lvl, name));
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
        for (Mob m : this.enemies) {
            text = text + m.getName() + " (Level " + m.getLEVEL() + ") + (HP - " + m.getActualHP() + "/" + m.getHP() + ")\n";
        }
        return text;
    }

    // Retourne la position d'un monstre dans la liste

    public int posMob(Mob m) { 
        for (int i = 0; i < this.enemies.size(); i++) {
            if (this.enemies.get(i).equals(m)) return i;
        }
        return 0;
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

            return this.enemies.get(Integer.parseInt(v.trim()) - 1);
        }

        return this.enemies.get(pos - 1);
    }

    // Méthode pour obtenir la taille des monstres dans l'équipe adverse

    public int getTeamSize() { return this.teamSize; }

    // Méthode servant à savoir si les ennemis ont été vaincus

    public Boolean haveBeenDefeated() {
        if (this.enemies.size() == 0) return true;
        else return false;
    }

    // Obtention de l'expérience moyenne

    public int getAverageExp() {
        int exp = 0;
        for (Mob b : this.enemies) {
            exp = exp + b.getExpDrop();
        }
        return exp / this.enemies.size();
    }

    // Obtention de l'équipe d'ennemis

    public ArrayList<Mob> getEnemies() {
        return this.enemies;
    }
}