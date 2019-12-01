public class Mob {
    // Les statistiques classiques de tout les monstres du jeu.
    private int HP;
    private int actualHP;
    private int ATK;
    private int DEF;
    private int SPEED;
    private int LEVEL;
    private String Name;
    private int expDrop;
    private int teamSPace;

    // Les statistiques de base des monstres.
    private static final int BASE_HP = 30;
    private static final int BASE_ATK = 6;
    private static final int BASE_DEF = 4;
    private static final int BASE_SPEED = 5;
    private static final int BASE_expDrop = 20;

    // Le créateur random de monstre.

    public Mob(int lvl) {
        int teamLevel = lvl - 1;
        double rand = Math.random();
        if (rand < 0.4) {
            this.Name = "Goblin";
            this.HP = (int)(BASE_HP * 2/3) + 3 * teamLevel;
            this.ATK = (int)(BASE_ATK * 2/3) + 1 * teamLevel;
            this.DEF = (int)(BASE_DEF * 1/2) + 1 * teamLevel;
            this.SPEED = (int)(BASE_SPEED * 7/5) + 2 * teamLevel;
            this.expDrop = BASE_expDrop;
            this.teamSPace = 1;
        }
        else if (rand < 0.6) {
            this.Name = "Giant";
            this.HP = (int)(BASE_HP * 2) + 10 * teamLevel;
            this.ATK = (int)(BASE_ATK * 1) + 1 * teamLevel;
            this.DEF = (int)(BASE_DEF * 2) + 3 * teamLevel;
            this.SPEED = (int)(BASE_SPEED * 1/5) + (int)(0.5 * teamLevel);
            this.expDrop = (int)(BASE_expDrop * 1.5);
            this.teamSPace = 2;
        }
        else if (rand < 0.9) {
            this.Name = "Slime";
            this.HP = (int)(BASE_HP) + 5 * teamLevel;
            this.ATK = (int)(BASE_ATK) + 2 * teamLevel;
            this.DEF = (int)(BASE_DEF) + 2 * teamLevel;
            this.SPEED = (int)(BASE_SPEED) + (int)(1.75 * teamLevel);
            this.expDrop = (int)(BASE_expDrop * 1.5);
            this.teamSPace = 2;
        }
        else if (rand < 0.95) {
            this.Name = "Dragon";
            this.HP = (int)(BASE_HP * 10/3) + 15 * teamLevel;
            this.ATK = (int)(BASE_ATK * 4) + 3 * teamLevel;
            this.DEF = (int)(BASE_DEF * 2.5) + 2 * teamLevel;
            this.SPEED = (int)(BASE_SPEED) + (int)(1.4 * teamLevel);
            this.expDrop = (int)(BASE_expDrop * 5) + 50 * teamLevel;
            this.teamSPace = 4;
        }
        else {
            this.Name = "Silver Slime";
            this.HP = 1;
            this.ATK = 1;
            this.DEF = 10000;
            this.SPEED = 1;
            this.expDrop = 1000;
            this.teamSPace = 1;
        }
        this.LEVEL = lvl;
        this.actualHP = this.HP;
    }

    // Les accesseurs aux statistiques des monstres

    public String getName() { return this.Name; }

    public int getHP() { return this.HP; }

    public int getActualHP() { return this.actualHP; }

    public int getATK() { return this.ATK; }

    public int getDEF() { return this.DEF; }

    public int getSPEED() { return this.SPEED; }

    public int getExpDrop() { return this.expDrop; }

    public int getLEVEL() { return this.LEVEL; }

    public int getTeamSpace() { return this.teamSPace; }

    // Les attributeurs de valeurs aux statistiques des monstres (uniquement les HP, pour des raisons triviales)

    public void setActualHP(int hp) { this.actualHP = hp; }

    // La méthode toString des monstres

    public String toString() { return this.Name + " (Level " + this.LEVEL + ")"; }
}