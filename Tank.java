public class Tank extends Physical {
    private final String subClass = "Tank";

    public Tank(String name) {
        super(name);
        this.HP = (int)(this.HP * 1.6);
        this.actualHP = this.HP;
        this.DEF = (int)(this.DEF * 2.1);
        this.ATK = (int)(this.ATK * 1.2);
        this.SPEED = (int)(this.SPEED * 0.4);
    }

    // Partie de récupération des informations des personnages

    public String getSubClass() { return this.subClass; }

    // Partie de l'affichage des personnages

    public String toString() {
        String text = "Name - " + this.Name + "       LEVEL - " + this.LEVEL + "\nClass - " + this.MainClass + "       Specialized Class - " + this.subClass + "\nHP  - " + this.actualHP + "/" + this.HP +"       ATK - " + this.getATK() + "       SPD - " + this.getSPEED() + "\nDEF - " + this.getDEF() + "       LCK - " + this.LUCK + "       (" + this.EXP + "/" + this.neededEXP + ")\n";
        if (this.getWeapon() != null) {
            return text + "Equiped Weapon - " + this.getWeapon().getName() +"\n";
        }
        else {
            return text + "Equiped Weapon - None\n";
        }
    }

    // Méthode pour les montées de niveau

    public void levelUP() {
        this.neededEXP = this.neededEXP + 50;
        
        System.out.println(this.Name + " has leveled up!");
        int bhp = (int)(Math.random() * 6);
        this.HP = this.HP + bhp;
        this.actualHP = this.actualHP + bhp;
        System.out.println("HP  - " + this.HP + "\t(+" + bhp + ")");

        int batk = (int)(Math.random() * 2);
        this.ATK = this.ATK + batk;
        System.out.println("ATK - " + this.ATK + "\t(+" + batk + ")");

        int bdef = (int)(Math.random() * 3);
        this.DEF = this.DEF + bdef;
        System.out.println("DEF - " + this.DEF + "\t(+" + bdef + ")");

        int bspd = (int)(Math.random() * 2);
        this.SPEED = this.SPEED + bspd;
        System.out.println("SPD - " + this.SPEED + "\t(+" + bspd + ")");

        System.out.println();
    }

    public String levelUpForWindow() {
        this.EXP = 0;
        this.neededEXP = (int)(this.neededEXP * 1.5);
        this.LEVEL++;

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