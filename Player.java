public class Player {

    private int maxLife;

    private int life;
    private int luck;
    private int coins;
    private int doubleRollTokens;
    private boolean hasArmor;

    public Player() {
        this.maxLife = 25;
        this.life = maxLife;
        this.luck = 1;
        this.coins = 0;
        this.doubleRollTokens = 0;
        this.hasArmor = false;
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

    public int getDoubleRollTokens() {
        return doubleRollTokens;
    }

    public void setLife(int life) {
        if (life < 0) {
            this.life = 0;
        }
        else if (life > maxLife) {
            this.life = maxLife;
        }
        else {
            this.life = life;
        }
    }

    public void setLuck(int luck) {
        this.luck = luck;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getMaxLife() {
        return maxLife;
    }

    public boolean hasArmor() {
        return hasArmor;
    }

    public int getDoubleRollLimit() {
        if (hasArmor) {
            return 3;
        }

        return 2;
    }

    public void equipArmor() {
        if (!hasArmor) {
            hasArmor = true;
            maxLife += 20;
            life += 20;
        }
    }

    public void takeDamage(int amount) {
        life -= amount;

        if (life < 0) {
            life = 0;
        }
    }

    public void heal(int amount) {
        life += amount;

        if (life > maxLife) {
            life = maxLife;
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

    public boolean addDoubleRollToken() {
        if (doubleRollTokens < getDoubleRollLimit()) {
            doubleRollTokens++;
            return true;
        }

        return false;
    }

    public boolean useDoubleRollToken() {
        if (doubleRollTokens > 0) {
            doubleRollTokens--;
            return true;
        }

        return false;
    }

    public boolean isAlive() {
        return life > 0;
    }
}