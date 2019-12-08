public class FightMatrixCharacter {
    private int x;
    private int y;
    private Character chara;

    public FightMatrixCharacter(Character c, int x, int y) {
        this.chara = c;
        this.x = x;
        this.y = y;
    }

    public Character getC() { return this.chara; }
    public int getX() { return this.x; }
    public int getY() { return this.y; }
}