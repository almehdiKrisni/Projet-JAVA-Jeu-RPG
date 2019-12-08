import java.util.Scanner;

public class Mage extends Magic {
    private final String subClass = "Mage";

    public Mage(String name) {
        super(name);
        this.ATK = (int)(this.ATK * 1.5);
        this.SPEED = (int)(this.SPEED * 0.8);
        this.DEF = (int)(this.DEF * 0.8);
    }

    // Partie de récupération des informations des personnages

    public String getSubClass() {
        return this.subClass;
    }

    // Partie d'affichage des personnages

    public String toString() {
        return "Name - " + this.Name + "\tLEVEL - " + this.LEVEL + "\nClass - " + this.MainClass + "\tSpecialized Class - " + this.subClass + "\nHP  - " + this.actualHP + "/" + this.HP +"\tATK - " + this.ATK + "\tSPD - " + this.SPEED + "\nDEF - " + this.DEF + " \tLCK - " + this.LUCK + " \t(" + this.EXP + "/" + this.neededEXP + ")\n";
    }

    // Action en combat du personnage

    public void actionCombat(Enemies enemies){
        System.out.println("______________________________________________________________________________\n");
        System.out.println(this.getName() + " is waiting for orders ...\n(1 - Attack)\t(2 - Use Item)\n");

        Scanner scan = new Scanner(System.in);
        // On choisit l'option de combat
        int choice = scan.nextInt(); System.out.println();

        // Dans le cas ou on attaque
        if (choice == 1) {
            System.out.println("Enemies in sight ...\n" + enemies);
            int pos = scan.nextInt(); System.out.println();
            
            Mob target = enemies.getMobInPos(pos);
            int damage = 0;
            if (this.SPEED - target.getSPEED() < 5) {
                if ((int)(Math.random() * 100) > this.LUCK) {
                    damage = this.ATK;
                    System.out.println(this.Name + " has inflicted " + damage + " damages to " + target.toString() + ".");
                    target.setActualHP(Math.max(0, target.getActualHP() - damage));
                    if (target.getActualHP() == 0) {
                        System.out.println("\n" + target.getName() + " has fainted.\n");
                        this.earnExp(target);
                        enemies.deleteMob(target);
                    }
                }
                else {
                    damage = this.ATK * 3;
                    System.out.println("CRITICAL HIT !!! " + this.Name + " has inflicted " + damage + " damages to " + target.toString() + ".");
                    target.setActualHP(Math.max(0, target.getActualHP() - damage));
                    if (target.getActualHP() == 0) {
                        System.out.println(target.toString() + " has fainted.\n");
                        this.earnExp(target);
                        enemies.deleteMob(target);
                    }
                }
            }
            else {
                int n = 0;
                while ((target.getActualHP() != 0) && (n < 2)) {
                    if ((int)(Math.random() * 100) > this.LUCK) {
                        damage = this.ATK;
                        System.out.println(this.Name + " has inflicted " + damage + " damages to " + target.toString() + ".");
                        target.setActualHP(Math.max(0, target.getActualHP() - damage));
                        if (target.getActualHP() == 0) {
                            System.out.println(target.getName() + " has fainted.\n");
                            this.earnExp(target);
                            enemies.deleteMob(target);
                        }
                    }
                    else {
                        damage = this.ATK * 3;
                        System.out.println("CRITICAL HIT !!! " + this.Name + " has inflicted " + damage + " damages to " + target.toString() + ".");
                        target.setActualHP(Math.max(0, target.getActualHP() - damage));
                        if (target.getActualHP() == 0) {
                            System.out.println(target.toString() + " has fainted.\n");
                            this.earnExp(target);
                            enemies.deleteMob(target);
                        }
                    }
                    if (n == 0) System.out.println(this.Name + " attacks again!");
                    n++;
                }
            }
        }
    }

    // Méthode pour les montées de niveau

    public void levelUP() {
        this.neededEXP = this.neededEXP + 50;
        
        System.out.println(this.Name + " has leveled up!");
        int bhp = (int)(Math.random() * 3);
        this.HP = this.HP + bhp;
        this.actualHP = this.actualHP + bhp;
        System.out.println("HP  - " + this.HP + "\t(+" + bhp + ")");

        int batk = (int)(Math.random() * 3);
        this.ATK = this.ATK + batk;
        System.out.println("ATK - " + this.ATK + "\t(+" + batk + ")");

        int bdef = (int)(Math.random() * 2);
        this.DEF = this.DEF + bdef;
        System.out.println("DEF - " + this.DEF + "\t(+" + bdef + ")");

        int bspd = (int)(Math.random() * 3);
        this.SPEED = this.SPEED + bspd;
        System.out.println("SPD - " + this.SPEED + "\t(+" + bspd + ")");

        System.out.println();
    }

    public String levelUpForWindow() {
        this.EXP = 0;
        this.neededEXP = (int)(this.neededEXP * 1.5);

        String text = this.Name + " has leveled up!\n";
        int bhp = (int)(Math.random() * 3);
        this.HP = this.HP + bhp;
        this.actualHP = this.actualHP + bhp;
        text = text + "HP  - " + this.HP + "\t(+" + bhp + ")\n";

        int batk = (int)(Math.random() * 3);
        this.ATK = this.ATK + batk;
        text = text + "ATK - " + this.ATK + "\t(+" + batk + ")\n";

        int bdef = (int)(Math.random() * 2);
        this.DEF = this.DEF + bdef;
        text = text + "DEF - " + this.DEF + "\t(+" + bdef + ")\n";

        int bspd = (int)(Math.random() * 3);
        this.SPEED = this.SPEED + bspd;
        text = text + "SPD - " + this.SPEED + "\t(+" + bspd + ")\n";

        return text;
    }
}