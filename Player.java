public class Player {

    private int life;
    private int luck;
    private int coins;

    public Player() {
        this.life = 40;
        this.luck = 30;
        this.coins = 0;
    }

    public int getLife() {
        return life;
    }

    public int getLuck() {
        return luck;
    }

    public int getCoins() {
        return coins;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public void setLuck(int luck) {
        this.luck = luck;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void takeDamage(int amount) {
        life -= amount;

        if (life < 0) {
            life = 0;
        }
    }

    public void addCoins(int amount) {
        coins += amount;
    }

    public void removeCoins(int amount) {
        coins -= amount;

        if (coins < 0) {
            coins = 0;
        }
    }

    public boolean isAlive() {
        return life > 0;
    }

}
