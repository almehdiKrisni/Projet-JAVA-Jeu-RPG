public class Weapon {
    private int Might;
    private int Weigth;
    private String Name;
    private String Description;
    private int price;

    public Weapon(int might, int weigth, String name, String desc) {
        this.Might = might;
        this.Weigth = weigth;
        this.Name = name;
        this.Description = desc;
        this.price = (this.Might * 50 - this.Weigth * 5);
    }

    public Weapon(int might, int weigth, String name, String desc, int price) {
        this.Might = might;
        this.Weigth = weigth;
        this.Name = name;
        this.Description = desc;
        this.price = price;
    }

    // Partie de récupération des informations des armes

    public int getMigth() { return this.Might; }

    public int getWeigth() { return this.Weigth; }

    public String getName() { return this.Name;}

    public String getDescription() { return this.Description; }

    public int getPrice() { return this.price; }

    // Affichage des informations des armes

    public String toString() { return "Weapon Name - " + this.Name + "\n" + this.Description + "\nMight - " + this.Might + "\tWeigth - " + this.Weigth + "\n"; }

    // Création aléatoire d'armes
    
    public Weapon createWeapon() {
        double r = Math.random();
        Weapon w;

        if (r < 0.25) {
            w = new Weapon(4, 3, "Iron Sword", "A basic Iron Sword. The go-to weapon for beginners.");
        }
        else if (r < 0.45) {
            w = new Weapon(3, 0, "Knife", "A small knife, commonly used by thieves.");
        }
        else if (r < 0.68) {
            w = new Weapon(6, 2, "Steel Sword", "A sword for experienced fighters. Perfect for mercenaries.");
        }
        else if (r < 0.88) {
            w = new Weapon(9, 5, "Silver Sword", "A heavy sword but sharp sword. Use at your risk.");
        }
        else if (r < 0.90) {
            w = new Weapon(12, 2, "Ragnell", "A legendary sword. Used by an ancient king.");
        }
        else if (r < 0.95) {
            w = new Weapon(2, 0, "Wooden Stick", "A basic wooden stick.", 1);
        }
        else {
            w = new Weapon(2, 2, "Wooden Sword", "The classic Minecraft weapon.");
        }
        
        return w;
    }
}