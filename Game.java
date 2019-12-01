import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ArrayList<String> liste_choix = new ArrayList<String>();
        for (int v = 1; v <= 4; v++) { String adding = String.format("%d",v); liste_choix.add(adding); }

        System.out.println("THE WORST GAME EVER\n\n");

        Party p1 = new Party();

        for (int i = 0; i < 4; i++) {
            System.out.println("Enter your name :");
            String name = scanner.next();

            System.out.println("\nChoose your class :\n1 - Sweeper (High base Speed and Attack)\n2 - Tank (High base HP and Defense)\n3 - Mage (Ignore the enemy's defense, low base HP and DEF)\n4 - Healer (Heals an ally or inflict low damage to enemies)\n");
            String v = scanner.next();

            while ( liste_choix.contains(v) == false) {
                System.out.println("\nError while choosing your class (wrong value chosen)\nChoose your class :\n1 - Sweeper (High base Speed and Attack)\n2 - Tank (High base HP and Defense)\n3 - Mage (Ignore the enemy's defense, low base HP and DEF)\n4 - Healer (Heals an ally or inflict low damage to enemies)\n");
                v = scanner.next();
            }

            if ( v == "1") { Sweeper c = new Sweeper(name); p1.addMember(c); }
            else if ( v == "2" ) { Tank c = new Tank(name); p1.addMember(c); }
            else if ( v == "3" ) { Mage c = new Mage(name); p1.addMember(c); }
            else { Healer c = new Healer(name); p1.addMember(c); }
        }

        System.out.println(p1);

        scanner.close();
    }
}