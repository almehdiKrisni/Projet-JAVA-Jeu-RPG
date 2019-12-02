public class Game {
    public static void main(String[] args) {
        Party p1 = new Party();

        for (int i = 0; i < 2; i++) {
            p1.addMember();
        }

        System.out.println(p1);

        Enemies e1 = new Enemies(p1.getAverageLevel(), (int)(Math.random() * 4 + 1));

        Fight f1 = new Fight(p1, e1);

        f1.actionFight();
    }
}