public class Healer extends Magic {
    private final String subClass = "Healer";

    public Healer(String name) {
        super(name);
        this.HP = (int)(this.HP * 1.2);
        this.actualHP = this.HP;
        this.DEF = (int)(this.DEF * 0.7);
        this.LUCK = (int)(this.LUCK * 3);
    }

    // Partie de récupération des informations des personnages

    public String getSubClass() {
        return this.subClass;
    }

    // Action en combat du personnage

    public void actionCombat(Enemies enemies, int pos) {
        return;
    }

    // Méthode pour les montées de niveau

    public void levelUP() {
        this.neededEXP = this.neededEXP + 50;

        System.out.println(this.Name + " has leveled up!");
        int bhp = (int)(Math.random() * 5);
        this.HP = this.HP + bhp;
        this.actualHP = this.actualHP + bhp;
        System.out.println("HP  - " + this.HP + " (+" + bhp + ")");

        int batk = (int)(Math.random() * 2);
        this.ATK = this.ATK + batk;
        System.out.println("ATK - " + this.ATK + " (+" + batk + ")");

        int bdef = (int)(Math.random() * 3);
        this.DEF = this.DEF + bdef;
        System.out.println("DEF - " + this.DEF + " (+" + bdef + ")");

        int bspd = (int)(Math.random() * 3);
        this.SPEED = this.SPEED + bspd;
        System.out.println("SPD - " + this.SPEED + " (+" + bspd + ")");

    }

    // Partie d'affichage des personnages

    public String toString() {
        return "Name - " + this.Name + "\tLEVEL - " + this.LEVEL + "\nClass - " + this.MainClass + "\tSpecialized Class - " + this.subClass + "\nHP  - " + this.actualHP + "/" + this.HP +"\tATK - " + this.ATK + "\tSPD - " + this.SPEED + "\nDEF - " + this.DEF + " \tLCK - " + this.LUCK + " \t(" + this.EXP + "/" + this.neededEXP + ")\n";
    }
}