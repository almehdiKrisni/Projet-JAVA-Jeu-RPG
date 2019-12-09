import javax.swing.*;

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
    private JLabel imageN;

    // Les statistiques de base des monstres.
    private static final int BASE_HP = 30;
    private static final int BASE_ATK = 10;
    private static final int BASE_DEF = 4;
    private static final int BASE_SPEED = 5;
    private static final int BASE_expDrop = 20;

    // Le créateur random de monstre.

    public Mob(int lvl) {
        int teamLevel = Math.min(lvl - 1, 0);
        double rand = Math.random();
        if (rand < 0.4) {
            this.Name = "Goblin";
            this.HP = (int)(BASE_HP * 2/3) + 3 * teamLevel;
            this.ATK = (int)(BASE_ATK * 2/3) + 1 * teamLevel;
            this.DEF = (int)(BASE_DEF * 1/2) + 1 * teamLevel;
            this.SPEED = (int)(BASE_SPEED * 7/5) + 2 * teamLevel;
            this.expDrop = BASE_expDrop;
            this.teamSPace = 1;
            this.imageN = new JLabel(new ImageIcon("Enemy_Sprites/Goblin.png"));
        }
        else if (rand < 0.6) {
            this.Name = "Giant";
            this.HP = (int)(BASE_HP * 2) + 10 * teamLevel;
            this.ATK = (int)(BASE_ATK * 1) + 1 * teamLevel;
            this.DEF = (int)(BASE_DEF * 2) + 3 * teamLevel;
            this.SPEED = (int)(BASE_SPEED * 1/5) + (int)(0.5 * teamLevel);
            this.expDrop = (int)(BASE_expDrop * 1.5);
            this.teamSPace = 2;
            this.imageN = new JLabel(new ImageIcon("Enemy_Sprites/Giant.png"));
        }
        else if (rand < 0.97) {
            this.Name = "Slime";
            this.HP = (int)(BASE_HP) + 5 * teamLevel;
            this.ATK = (int)(BASE_ATK) + 2 * teamLevel;
            this.DEF = (int)(BASE_DEF) + 2 * teamLevel;
            this.SPEED = (int)(BASE_SPEED) + (int)(1.75 * teamLevel);
            this.expDrop = (int)(BASE_expDrop * 1.5);
            this.teamSPace = 1;
            this.imageN = new JLabel(new ImageIcon("Enemy_Sprites/Slime.png"));
        }
        else {
            this.Name = "Silver Slime";
            this.HP = 1;
            this.ATK = 1;
            this.DEF = 9999;
            this.SPEED = 1;
            this.expDrop = 1000;
            this.teamSPace = 1;
            this.imageN = new JLabel(new ImageIcon("Enemy_Sprites/Silver_Slime.png"));
        }
        this.LEVEL = Math.min(lvl, 1);
        this.actualHP = this.HP;
    }

    public Mob(int lvl, String name) {
        int teamLevel = Math.min(lvl - 1, 0);
        if (name == "Dragon") {
            this.Name = "Dragon (Dangerous enemy)";
            this.HP = (int)(BASE_HP * 10) + 30 * teamLevel;
            this.ATK = (int)(BASE_ATK * 4) + 4 * teamLevel;
            this.DEF = (int)(BASE_DEF * 2) + 2 * teamLevel;
            this.SPEED = (int)(BASE_SPEED * 0.7) + (int)(1.4 * teamLevel);
            this.expDrop = (int)(BASE_expDrop * 10) + 50 * teamLevel;
            this.teamSPace = 4;
            this.imageN = new JLabel(new ImageIcon("Enemy_Sprites/Dragon.png"));
        }
        else if (name == "Armored Beast") {
            this.Name = "Armored Beast (Dangerous enemy)";
            this.HP = (int)(BASE_HP * 20) + 50 * teamLevel;
            this.ATK = (int)(BASE_ATK * 2) + 4 * teamLevel;
            this.DEF = (int)(BASE_DEF * 3) + 2 * teamLevel;
            this.SPEED = (int)(BASE_SPEED * 0.2) + (int)(0.5 * teamLevel);
            this.expDrop = (int)(BASE_expDrop * 10) + 50 * teamLevel;
            this.teamSPace = 4;
            this.imageN = new JLabel(new ImageIcon("Enemy_Sprites/Armored_Beast.png"));
        }
        else if (name == "Death") {
            this.Name = "Death (Run)";
            this.HP = 9999;
            this.ATK = 9999;
            this.DEF = 9999;
            this.SPEED = 9999;
            this.expDrop = 1;
            this.teamSPace = 4;
            this.imageN = new JLabel(new ImageIcon("Enemy_Sprites/Death.png"));
        }
        else {
            this.Name = "Shrek (Dangerous enemy)";
            this.HP = (int)(BASE_HP * 5) + 20 * teamLevel;
            this.ATK = (int)(BASE_ATK * 2.3) + 4 * teamLevel;
            this.DEF = (int)(BASE_DEF * 3) + 2 * teamLevel;
            this.SPEED = (int)(BASE_SPEED * 1.2) + (int)(1.1 * teamLevel);
            this.expDrop = (int)(BASE_expDrop * 5) + 50 * teamLevel;
            this.teamSPace = 4;
            this.imageN = new JLabel(new ImageIcon("Enemy_Sprites/Shrek.png"));
        }
        this.LEVEL = Math.min(lvl, 1);
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

    public JLabel getImageN() { return this.imageN; }

    // Les attributeurs de valeurs aux statistiques des monstres (uniquement les HP, pour des raisons triviales)

    public void setActualHP(int hp) { this.actualHP = Math.max(0, hp); }

    // La méthode toString des monstres

    public String toString() { return this.Name + " (Level " + this.LEVEL + ")"; }

    // Action en combat d'un monstre (en version Terminal)

    public void actionCombat(Party p1) {
        System.out.println("______________________________________________________________________________\n");
        System.out.println(this.Name + " is attacking ...");
        int choice = (int)(Math.random() * p1.getTeam().size());
        Character target = p1.getTeam().get(choice);
        System.out.println(this.Name + " attacks " + target.getName() + "!");

        if (this.SPEED - target.getSPEED() < 5) {
            int damage = Math.max(this.ATK - target.getDEF(), 0);
            System.out.println(this.Name + " has inflicted " + damage + " damages to " + target.getName() + "!");
            target.setActualHP(Math.max(0, target.getActualHP() - damage));
            if (target.getActualHP() == 0) {
                System.out.println("\n" + target.getName() + " is down!\n");
                target.setIsDead(true);
            }
        }
        else {
            int n = 0;
            while ((target.actualHP != 0) && (n < 2)) {
                if (n == 1) {
                    choice = (int)(Math.random() * p1.getTeam().size() + 1);
                    target = p1.getTeam().get(choice);
                    System.out.println(this.Name + " attacks " + target.getName() + "!");
                }
                int damage = Math.max(this.ATK - target.getDEF(), 0);
                System.out.println(this.Name + " has inflicted " + damage + " damages to " + target.getName() + "!");
                target.setActualHP(Math.max(0, target.getActualHP() - damage));
                if (target.getActualHP() == 0) {
                    System.out.println("\n" + target.getName() + " is down!\n");
                    target.setIsDead(true);
                }
                if (n == 0) System.out.println(this.Name + " attacks again ...");
                n++;
            }
        }
    }
}