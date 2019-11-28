public class Test {
    public static void main(String[] args) {
        Personnage p1 = new Mage("Dimitri Le Renoi");
        Personnage p2 = new Sweeper("Johnathan La Menace");
        Personnage p3 = new Tank("Denis Le Furry");
        Personnage p4 = new Healer("MNO La Dent Cassée");

        Weapon w1 = new Weapon(10, 3, "Fucking Falchion");
        Weapon w2 = new Weapon(100, 0, "La bite de Lucina");
        Weapon w3 = new Weapon(10000, 10, "Donkey's Fist");

        // Affichage des Objets

        System.out.println(p1);
        System.out.println(p2);
        System.out.println(p3);
        System.out.println(p4);

        System.out.println(w1);
        System.out.println(w2);
        System.out.println(w3);
    }
}