public class Enemy {

    private String name;
    private int life;
    private int luck;
    private int difficulty;
    private int reward;

    public Enemy(String name, int life, int luck, int difficulty, int reward) {
        this.name = name;
        this.life = life;
        this.luck = luck;
        this.difficulty = difficulty;
        this.reward = reward;
    }

    public String getName() {
        return name;
    }

    public int getLife() {
        return life;
    }

    public int getLuck() {
        return luck;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public int getReward() {
        return reward;
    }

    public void takeDamage(int amount) {
        life -= amount;

        if (life < 0) {
            life = 0;
        }
    }

    public boolean isAlive() {
        return life > 0;
    }
}
