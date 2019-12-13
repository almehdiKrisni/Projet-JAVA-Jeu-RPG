public abstract class Magic extends Character {
    protected final String MainClass = "Magic User";

    public Magic(String name) {
        super(name);
    }

    // Partie de récupération des informations des personnages

    public String getMainClass() { return this.MainClass; }

    // Action en combat du personnage

    public abstract void actionCombat(Enemies enemies);

    // Méthode pour recevoir l'attaque d'un personnage

    public int getATK() { 
        int buffA = 0;
        for (Buff buff : this.buffList) {
            if (buff.boostsATK()) buffA = buffA + buff.getATKBuff();
        }
        return buffA + this.ATK;
    }

    public int getSPEED() { return this.SPEED; }

    // Partie d'affichage des informations des personnages

    public abstract String toString();

    public abstract String levelUpForWindow();

}
