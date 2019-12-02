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
            System.out.println("Le personnage tient déjà une arme.\n");
            return;
        }
        this.weapon = weapon;
        System.out.println("Le personnage tient désormais " + this.weapon.getName() +".\n");
    }

    public Weapon takeWeapon() {
        if (this.weapon == null) {
            System.out.println(this.getName() + "ne possede pas d'équipement.\n");
            return null;
        }
        else {
            Weapon ret = this.getWeapon();
            this.giveWeapon(null);
            return ret;
        }
    }

    // Action en combat du personnage

    public void actionCombat(Enemies enemies, int pos){
        Mob target = enemies.getMobInPos(pos);
        int damage = 0;
        if (this.SPEED - target.getSPEED() >= 5) {
            if ((int)(Math.random() * 100) > this.LUCK) {
                damage = Math.max(this.ATK - target.getDEF(), 0);
                System.out.println(this.Name + " has inflicted " + damage + " damages to " + target.toString() + ".\n");
                target.setActualHP(Math.max(0, target.getActualHP() - damage));
                if (target.getActualHP() == 0) {
                    System.out.println(target.getName() + " has fainted.\n");
                    this.earnExp(target);
                    enemies.deleteMob(target);
                }
            }
            else {
                damage = this.ATK * 3;
                System.out.println("CRITICAL HIT !!! " + this.Name + " has inflicted " + damage + " damages to " + target.toString() + ".\n");
                target.setActualHP(Math.max(0, target.getActualHP() - damage));
                if (target.getActualHP() == 0) {
                    System.out.println(target.toString() + "has fainted.\n");
                    this.earnExp(target);
                    enemies.deleteMob(target);
                }
            }
        }
        else {
            for (int n = 0; n < 2; n++) {
                if ((int)(Math.random() * 100) > this.LUCK) {
                    damage = Math.max(this.ATK - target.getDEF(), 0);
                    System.out.println(this.Name + " has inflicted " + damage + " damages to " + target.toString() + ".\n");
                    target.setActualHP(Math.max(0, target.getActualHP() - damage));
                    if (target.getActualHP() == 0) {
                        System.out.println(target.getName() + " has fainted.\n");
                        this.earnExp(target);
                        enemies.deleteMob(target);
                    }
                }
                else {
                    damage = this.ATK * 3;
                    System.out.println("CRITICAL HIT !!! " + this.Name + " has inflicted " + damage + " damages to " + target.toString() + ".\n");
                    target.setActualHP(Math.max(0, target.getActualHP() - damage));
                    if (target.getActualHP() == 0) {
                        System.out.println(target.toString() + "has fainted.\n");
                        this.earnExp(target);
                        enemies.deleteMob(target);
                    }
                }
                if (n == 0) System.out.println(this.Name + " attacks again!");
            }
        }
    }

    // Partie d'affichage des informations des personnages

    public abstract String toString();

    // Méthode de montée de niveau

    public abstract void levelUP();

}