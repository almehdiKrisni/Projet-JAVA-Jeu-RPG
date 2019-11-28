public abstract class Magic extends Personnage {
    private final String MainClass = "Magic User";

    public Magic(String name) {
        super(name);
    }

    // Partie de récupération des informations des personnages

    public String getMainClass() { return this.MainClass; }

    // Partie d'affichage des informations des personnages

    public abstract String toString();

}
