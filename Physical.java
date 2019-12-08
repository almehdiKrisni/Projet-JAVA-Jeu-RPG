import java.util.Scanner;

public abstract class Physical extends Character {
    protected final String MainClass = "Weapon User";
    protected Weapon weapon;

    public Physical(String name) {
        super(name);
        this.weapon = null;
    }

    // Partie de récupération des informations des personnages

    public String getMainClass() { return this.MainClass; }

    public Weapon getWeapon() {
        return this.weapon;
    }

    // Attribuer des armes aux personnages

    public void giveWeapon(Weapon weapon) {
        if (this.weapon != null) {
            System.out.println("This character already holds a weapon.\n");
            return;
        }
        this.weapon = weapon;
        System.out.println("This character now holds " + this.weapon.getName() +".\n");
    }

    public Weapon takeWeapon() {
        if (this.weapon == null) {
            System.out.println(this.getName() + " doesn't hold a weapon.\n");
            return null;
        }
        else {
            Weapon ret = this.getWeapon();
            this.giveWeapon(null);
            return ret;
        }
    }
    
    // Action en combat du personnage

    public void actionCombat(Enemies enemies){
        System.out.println("______________________________________________________________________________\n");
        System.out.println(this.getName() + " is waiting for orders ...\n(1 - Attack)\t(2 - Use Item)\n");

        Scanner scan = new Scanner(System.in);
        // On choisit l'option de combat
        int choice = scan.nextInt(); System.out.println();

        // Dans le cas ou on attaque
        if (choice == 1 ) {
            System.out.println("Enemies in sight ...\n" + enemies);
            int pos = scan.nextInt(); System.out.println();

            while ((pos > enemies.getEnemies().size()) || (pos < 1)) {
                System.out.println("Hey, I'll give you an advice... Choose a number between the options next to the enemies name.\n" + enemies.toString());
                pos = scan.nextInt(); System.out.println();;
            }
            
            Mob target = enemies.getMobInPos(pos);
            int damage = 0;
            if (this.SPEED - target.getSPEED() < 5) {
                if ((int)(Math.random() * 100) > this.LUCK) {
                    damage = Math.max(this.ATK - target.getDEF(), 0);
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
                        damage = Math.max(this.ATK - target.getDEF(), 0);
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
                            System.out.println(target.toString() + "has fainted.\n");
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

    // Partie d'affichage des informations des personnages

    public abstract String toString();

    // Méthode de montée de niveau

    public abstract void levelUP();

    public abstract String levelUpForWindow();

}