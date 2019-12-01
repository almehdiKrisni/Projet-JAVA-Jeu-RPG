public class Test {
    public static void main(String[] args) {
        // Affichage des personnages

        Mage p1 = new Mage("Dimitri");
        Sweeper p2 = new Sweeper("Jonathan");
        Tank p3 = new Tank("Denis");
        Healer p4 = new Healer("MNO");

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

        // Affichage des soins

        HealingItem h1 = new HealingItem("Potion", 5, 20);
        HealingItem h2 = new HealingItem("Elixir", 1, 40);
        HealingItem h3 = new HealingItem("Bonbon", 1, 10);

        System.out.println(h1);
        System.out.println(h2);
        System.out.println(h3);

        // Affichage de l'inventaire

        Inventory i1 = new Inventory();
        i1.addItem(h1);
        i1.addItem(h2);
        i1.addItem(h3);

        //System.out.println(i1);

        // Affichage d'une Party

        Party team1 = new Party();
        team1.addMember(p1);
        team1.addMember(p2);
        team1.addMember(p3);
        team1.addMember(p4);

        team1.addItem(h1);
        team1.addItem(h1);
        team1.addItem(h2);
        team1.addItem(h3);

        p2.giveWeapon(w1);
        
        System.out.println(team1);

        Enemies e1 = new Enemies(1, 4);
        System.out.println(e1);
    }
}