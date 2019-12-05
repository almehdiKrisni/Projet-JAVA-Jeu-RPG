public class Fight {
    private int Turn;
    private Party allies;
    private Enemies enemies;

    public Fight(Party allies, Enemies enemies){
        this.Turn = 1;
        this.allies = allies;
        this.enemies = enemies;
    }

    // Accesseurs aux éléments d'un objet de la classe

    public int getTurn() { return this.Turn; }

    public Party getParty() { return this.allies; } 
    
    public Enemies getEnemies() { return this.enemies; }

    // Méthode servant à simuler les combats

    public void actionFight() {
        System.out.println("################################## FIGHT ###################################\n");

        int expIfWin = this.enemies.getAverageExp();

        while (!this.enemies.haveBeenDefeated()) {
            System.out.println("\n--------------------------------- TURN " + this.Turn + " -----------------------------------\n");
            this.Turn++;

            System.out.println(this.allies + "\nEnemies : \n" + this.enemies);
            for (Character c : this.allies.getTeam()) {
                if (c.getIsDead() == false) {
                    try { Thread.sleep(2500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

                    c.actionCombat(this.enemies);

                    // Fin du combat lorsque tout les ennemis ont été vaincus

                    if (this.enemies.haveBeenDefeated()) {
                        try { Thread.sleep(2500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                        System.out.println("______________________________________________________________________________\n");
                        double r = Math.random();
                        if (r < 0.33) System.out.println("The enemies have been defeated! Let's move on...\n");
                        else if (r < 0.67) System.out.println("Victory! To the next fight...\n");
                        else System.out.println("That settles it! It's crazy how strong we are...\n");

                        try { Thread.sleep(2500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

                        for (Character perso : this.allies.getTeam()) {
                            perso.earnExp(expIfWin);
                        }

                        System.out.println("############################# END OF THE FIGHT #############################\n");
                        return;
                    }
                }
            }
            for (Mob m : this.enemies.getEnemies()) {
                try { Thread.sleep(2500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

                m.actionCombat(this.allies);

                // Fin du combat si tout l'équipe est à terre

                if (this.allies.haveLost()) {
                    try { Thread.sleep(1500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                    System.out.println("______________________________________________________________________________\n");
                        double r = Math.random();
                        if (r < 0.33) System.out.println("It's a shame ...\n");
                        else if (r < 0.67) System.out.println("How could something like that happen\n");
                        else System.out.println("It's a cruel world we live in ...\n");

                        System.out.println("############################# END OF THE GAME ##############################\n");

                        return;
                }
            }

        }
    }
}