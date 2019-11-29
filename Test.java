public class Test {
    public static void main(String[] args) {
        Character p1 = new Mage("Dimitri");
        Character p2 = new Sweeper("Jonathan");
        Character p3 = new Tank("Denis");
        Character p4 = new Healer("MNO");

        Weapon w1 = new Weapon(10, 3, "Falchion 1");
        Weapon w2 = new Weapon(100, 0, "Falchion 2");
        Weapon w3 = new Weapon(10000, 10, "Falchion 3");

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