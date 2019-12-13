public class Buff extends Item{
    private int ATKBuff;
    private int DEFBuff;
    private int SPDBuff;
    private int remainingTurns;
    private String name;

    // Les buffs sont appliqués à certains personnages pendant les combats
    // Ils sont affiches lors de l'écran de séléction des actions de combats avec des flèches multicolores
    // Ils sont localisés au niveau des personnages grace à une ArrayList de buff
    // A chaque tour, l'attribut remainingTurns diminue de 1. Lorsqu'il attend 0, le buff est éliminé

    public Buff(int atk, int def, int spd, int turn) {
        if (atk == 0) this.ATKBuff = 0;
        else this.ATKBuff = atk;

        if (def == 0) this.DEFBuff = 0;
        else this.DEFBuff = def;

        if (spd == 0) this.SPDBuff = 0;
        else this.SPDBuff = spd;

        this.remainingTurns = 1 + turn;

        if (this.ATKBuff != 0) this.name = "Buster Potion (Level " + (this.remainingTurns - 1) + ")";
        else if (this.DEFBuff != 0) this.name = "Shield Potion (Level " + (this.remainingTurns - 1) + ")";
        else this.name = "Speed Potion (Level " + (this.remainingTurns - 1) + ")";
    }

    // Méthode de get

    public int getATKBuff() { return this.ATKBuff; }

    public int getDEFBuff() { return this.DEFBuff; }

    public int getSPDBuff() { return this.SPDBuff; }

    public int getRemainingTurns() { return this.remainingTurns; }

    public String getName() { return this.name; }

    // Méthode de retour des descriptions

    public String getDesc() {
        if (this.ATKBuff != 0) return "Boosts the ATK Stat of a Character by " + this.ATKBuff + ". Lasts " + (this.remainingTurns - 1) + " turns.";
        else if (this.DEFBuff != 0) return "Boosts the DEF Stat of a Character by " + this.DEFBuff + ". Lasts " + (this.remainingTurns - 1) + " turns.";
        else return "Boosts the SPD Stat of a Character by " + this.SPDBuff + ". Lasts " + (this.remainingTurns - 1) + " turns.";
    }

    public String info() {
        return "Item name - " + this.name + "\n" + this.getDesc() + "\n";
    }

    // Méthode d'affichage des buffs

    public String toString() {
        if (this.ATKBuff != 0) return "Buster Potion (Level " + (this.remainingTurns - 1) + ")";
        else if (this.DEFBuff != 0) return "Shield Potion (Level " + (this.remainingTurns - 1) + ")";
        else return "Speed Potion (Level " + (this.remainingTurns - 1) + ")";
    }

    // Méthode de vérification des buffs

    public Boolean boostsATK() { return (this.ATKBuff != 0); }

    public Boolean boostsDEF() { return (this.DEFBuff != 0); }

    public Boolean boostsSPD() { return (this.SPDBuff != 0); }

    // Méthode de set

    public void reduceTurns() { this.remainingTurns--; }

    // Remplissage

    public int getHPRecovery() { return 0; }
}