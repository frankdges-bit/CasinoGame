import java.util.Scanner;

public class Combat {

    private Player player;
    private Enemy enemy;
    private Dice dice;
    private Scanner scanner;

    public Combat(Player player, Enemy enemy, Dice dice, Scanner scanner) {
        this.player = player;
        this.enemy = enemy;
        this.dice = dice;
        this.scanner = scanner;
    }

    public void startBattle() {

        System.out.println("\nThe battle begins!");

        while (player.isAlive() && enemy.isAlive()) {

            displayStats();

            int playerDamage = playerTurn();
            enemy.takeDamage(playerDamage);

            System.out.println("You dealt " + playerDamage + " damage.");

            if (!enemy.isAlive()) {
                break;
            }

            enemyTurn();
        }
    }

    private int playerTurn() {

        while (true) {
            System.out.println("\nChoose an action:");
            System.out.println("1. Normal roll (d20 + Luck)");
            System.out.println("2. Weighted roll — costs 5 coins " + "(stronger attack and restores life)");
            System.out.println("3. Critical roll (triple damage but risk of losing life)");
            System.out.println("4. Double roll — costs 1 Double Roll Token " + "(Permanently increases Luck by 1)");
            System.out.println("   Tokens available: " + player.getDoubleRollTokens());

            int choice = scanner.nextInt();

            if (choice == 1) {
                return normalRoll();
            }
            else if (choice == 2) {
                if (player.getCoins() >= 5) {
                    return weightedRoll();
                }

                System.out.println("You do not have enough coins.");
            }
            else if (choice == 3) {
                return criticalRoll();
            }

            else if (choice == 4) {
                if (player.useDoubleRollToken()) {
                    return doubleRoll();
                }

                System.out.println("You do not have a Double Roll Token.");
            }
            else {
                System.out.println("Please enter 1, 2, 3 or 4.");
            }
        }
    }

    private int normalRoll() {

        int roll = dice.roll();

        System.out.println("You rolled: " + roll);

        return roll + player.getLuck();
    }

    private int weightedRoll() {

        final int COST = 5;

        int damageBonus;
        int healing;

        /*
         * Better dice improve both the damage bonus
         * and the amount of life restored.
         */
        if (dice.getBonus() >= 7) {
            damageBonus = 8;
            healing = 5;
        }
        else if (dice.getBonus() >= 3) {
            damageBonus =5;
            healing = 4;
        }
        else {
            damageBonus = 3;
            healing = 3;
        }

        player.removeCoins(COST);

        int roll = dice.roll();
        player.heal(healing);

        System.out.println("You spent " + COST + " coins.");
        System.out.println("You rolled: " + roll);
        System.out.println("Weighted damage bonus: +" + damageBonus);
        System.out.println("You recovered " + healing + " life.");
        System.out.println(
                "Current life: "
                        + player.getLife()
                        + "/"
                        + player.getMaxLife()
        );

        return roll + player.getLuck() + damageBonus;
    }

    private int criticalRoll() {

        int numberOfChoices;

        if (dice.getBonus() >= 7) {
            numberOfChoices = 3;
        }
        else if (dice.getBonus() >= 3) {
            numberOfChoices = 4;
        }
        else {
            numberOfChoices = 5;
        }

        System.out.println();
        System.out.println(
                "Choose a critical prediction from 1 to "
                        + numberOfChoices
                        + ":"
        );

        int prediction = scanner.nextInt();

        while (prediction < 1 || prediction > numberOfChoices) {
            System.out.println(
                    "Please enter a number from 1 to "
                            + numberOfChoices
                            + "."
            );

            prediction = scanner.nextInt();
        }

        /*
         * The critical result is separate from the damage roll.
         * This produces exact chances of 1/5, 1/4, or 1/3.
         */
        int criticalResult =
                (int) (Math.random() * numberOfChoices) + 1;

        int roll = dice.roll();
        int normalDamage = roll + player.getLuck();

        System.out.println("You predicted: " + prediction);
        System.out.println("Critical result: " + criticalResult);
        System.out.println("Damage roll: " + roll);

        if (prediction == criticalResult) {
            System.out.println("Critical hit!");
            return normalDamage * 3;
        }

        System.out.println("Prediction failed. You lose 4 HP.");
        player.takeDamage(4);

        return normalDamage;
    }

    private int doubleRoll() {

        int firstRoll = dice.roll();
        int secondRoll = dice.roll();

        int damage =
                firstRoll
                        + secondRoll
                        + player.getLuck();

        System.out.println("First roll: " + firstRoll);
        System.out.println("Second roll: " + secondRoll);

        player.setLuck(player.getLuck() + 1);

        System.out.println("Your luck permanently increased by 1.");
        System.out.println("Current luck: " + player.getLuck());

        return damage;
    }

    private void enemyTurn() {

        int roll = dice.rollBase();

        /*
         * Enemy damage uses:
         *
         * One-fourth of the d20 roll
         * Half of the enemy's luck
         * Half of the enemy's difficulty
         *
         * This keeps later enemies dangerous without allowing
         * most of them to defeat the player in one attack.
         */
        int rollDamage = (roll + 3) / 4;
        int luckDamage = enemy.getLuck() / 2;
        int difficultyDamage = enemy.getDifficulty() / 2;

        int enemyDamage =
                rollDamage
                        + luckDamage
                        + difficultyDamage;

        /*
         * Every successful enemy attack should cause
         * at least 1 point of damage.
         */
        if (enemyDamage < 1) {
            enemyDamage = 1;
        }

        player.takeDamage(enemyDamage);

        System.out.println(
                enemy.getName() + " rolled " + roll + "."
        );

        System.out.println(
                enemy.getName()
                        + " dealt "
                        + enemyDamage
                        + " damage."
        );
    }

    private void displayStats() {

        System.out.println("\n-------------------------");

        System.out.println("PLAYER");
        System.out.println("Life: " + player.getLife() + "/" + player.getMaxLife());
        System.out.println("Luck: " + player.getLuck());
        System.out.println("Coins: " + player.getCoins());

        System.out.println();

        System.out.println(enemy.getName().toUpperCase());
        System.out.println("Life: " + enemy.getLife());
        System.out.println("Luck: " + enemy.getLuck());
        System.out.println(
                "Difficulty: " + enemy.getDifficulty()
        );

        System.out.println("-------------------------");
    }

    public boolean determineWinner() {
        return player.isAlive() && !enemy.isAlive();
    }
}