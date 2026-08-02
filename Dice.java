import java.util.Random;

public class Dice {

    private int sides;
    private int bonus;
    private Random random;

    public Dice(int sides) {
        this.sides = sides;
        this.bonus = 0;
        this.random = new Random();
    }

    public int rollBase() {
        return random.nextInt(sides) + 1;
    }

    public int roll() {
        return rollBase() + bonus;
    }

    public int getSides() {
        return sides;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }
}