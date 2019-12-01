public class Test {
    public static void main(String[] args) {
        // Affichage des personnages

        Character p1 = new Mage("Dimitri");
        Character p2 = new Sweeper("Jonathan");
        Character p3 = new Tank("Denis");
        Character p4 = new Healer("MNO");

        System.out.println(p1);
        System.out.println(p2);
        System.out.println(p3);
        System.out.println(p4);

        // Affichage des armes

        Weapon w1 = new Weapon(10, 3, "Falchion 1", "A weapon used to cut apples a thousand year ago.");
        Weapon w2 = new Weapon(100, 0, "Falchion 2", "A weapon used to cut apples a thousand year ago.");
        Weapon w3 = new Weapon(10000, 10, "Falchion 3", "A weapon used to cut apples a thousand year ago.");

        System.out.println(w1);
        System.out.println(w2);
        System.out.println(w3);

        

        
    }
}