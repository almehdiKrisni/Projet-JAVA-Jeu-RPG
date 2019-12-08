import javax.swing.*;

public abstract class Character {
    // Les Statistiques des Personnages

    // Les points de Vie. Quand ils tombent à 0, le personnage est considéré KO (il faut le réanimer à l'aide d'un objet ou dans une aire de repos).
    protected int HP;
    // La statistique d'Attaque. Permet de calculer le nombre de dégats infligés lors des attaques.
    protected int ATK;
    // La statistique de Defense. Permet de calculer le nombre de dégats recus lors des attaques ennemies.
    protected int DEF;
    // La statistique de Vitesse. Permet d'effectuer plusieurs attaques par tour dans certaines conditions et d'autres choses.
    protected int SPEED;
    // La statistique de Chance. Permet d'esquiver les attaques adverses ou de faire des attaques critiques.
    protected int LUCK;
    // Le Niveau du personnage. Lors qu'il augmente, les statistiques sont augmentées.
    protected int LEVEL;
    // L'expérience du personnage. Quand ils atteignent un certain seuil, le personnage monte de niveau.
    protected int EXP;
    // L'expérience nécessaire pour que le personnage augmente de niveau.
    protected int neededEXP;
    // Le nom du personnage.
    protected String Name;
    // L'image du personnage en état neutre.
    protected JLabel imageN;
    

    // Les statistiques minimales que peut avoir un personnage.
    private final static int MIN_HP = 35;
    private final static int MIN_ATK = 10;
    private final static int MIN_DEF = 5;
    private final static int MIN_SPEED = 7;
    private final static int MIN_LEVEL = 1;
    private final static int START_EXP = 0;
    private final static int START_neededEXP = 100;

    // Le nombre de points de vie actuel et status des personnages (permet de gérer les combats)
    protected int actualHP;
    protected boolean isDead;

    public Character(String name) {
        this.HP = (int)(Math.random() * (MIN_HP * 0.3) + MIN_HP);
        this.ATK = (int)(Math.random() * (MIN_ATK * 0.5) + MIN_ATK);
        this.DEF = (int)(Math.random() * (MIN_DEF * 0.3) + MIN_DEF);
        this.SPEED = (int)(Math.random() * (MIN_SPEED * 0.5) + MIN_DEF);
        this.LUCK = (int)(Math.random() * 6);
        this.LEVEL = MIN_LEVEL;
        this.EXP = START_EXP;
        this.neededEXP = START_neededEXP;
        this.Name = name;
        
        this.imageN = null;
        this.isDead = false;
        this.actualHP = this.HP;
    }


    // Partie de récupération des statistiques (utiles lors des combats, ...)

    public int getHP() { return this.HP; }

    public int getATK() { return this.ATK; }

    public int getDEF() { return this.DEF; }

    public int getSPEED() { return this.SPEED; }

    public int getLUCK() { return this.LUCK; }

    public int getLEVEL() { return this.LEVEL; }

    public int getEXP() { return this.EXP; }

    public int getNeededEXP() { return this.neededEXP; }

    public String getName() { return this.Name; }

    public Boolean getIsDead() { return this.isDead; }

    public int getActualHP() { return this.actualHP; }

    public JLabel getImageN () { return this.imageN; }

    // Partie de controle des statistiques (utiles lors des montée de niveaux, ...)

    public void setATK(int atk) { this.ATK = atk; }

    public void setDEF(int def) { this.DEF = def; }

    public void setSPEED(int speed) { this.SPEED = speed; }

    public void setLUCK(int luck) { this.LUCK = luck; }

    public void setLEVEL(int level) { this.LEVEL = level; }

    public void setEXP(int exp) { this.EXP = exp; }

    public void setNeededEXP(int neededexp) { this.neededEXP = neededexp; }

    public void setIsDead(Boolean alive) { this.isDead = alive; }

    public void setActualHP (int hp) { this.actualHP = hp; }

    public void setImageN (JLabel image) { this.imageN = image; if (this.imageN == null) System.out.println("Picture error"); }

    // Affichage en String du personnage

    public abstract String toString();

    // Action en combat du personnage

    public abstract void actionCombat(Enemies enemies);

    // Utilisation d'un objet en combat

    public void useItem() { }

    // Méthode de montée de niveau

    public abstract void levelUP();

    public abstract String levelUpForWindow();

    // Méthode de montée d'obtention d'expérience (après avoir tué un monstre)

    public void earnExp(Mob m) { 
        System.out.println(this.Name + " has earned " + m.getExpDrop() + " experience points.\n");
        this.EXP = this.EXP + m.getExpDrop();
        if (this.EXP >= this.neededEXP) {
            this.EXP = 0;
            this.levelUP();
        }
    }

    public void earnExp(int earn) {
        System.out.println(this.Name + " has earned " + earn + " experience points.\n");
        this.EXP = this.EXP + earn;
        if (this.EXP >= this.neededEXP) {
            this.EXP = 0;
            this.levelUP();
        }
    }

}