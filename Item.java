public abstract class Item {
    protected String Name;

    public Item(String name) {
        this.Name = name;
    }

    // Accesseurs aux noms de l'objet

    public String getName() { return this.Name; }

    // Méethode abstraite de toString (redéfinie dans les sous-classes)

    public abstract String toString();
}