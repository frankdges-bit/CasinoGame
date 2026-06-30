public class Combat {

    private Player player;
    private Enemy enemy;
    private Dice dice;

    public Combat(Player player, Enemy enemy, Dice dice) {
        this.player = player;
        this.enemy = enemy;
        this.dice = dice;
    }

    public void startBattle() {

        System.out.println("The battle begins!");

        while (player.isAlive() && enemy.isAlive()) {

            // Player attacks
            int playerDamage = calculateDamage(player.getLuck());
            enemy.takeDamage(playerDamage);

            System.out.println("You dealt " + playerDamage + " damage.");

            if (!enemy.isAlive()) {
                break;
            }

            // Enemy attacks
            int enemyDamage = calculateDamage(enemy.getLuck());
            player.takeDamage(enemyDamage);

            System.out.println(enemy.getName() +
                    " dealt " + enemyDamage + " damage.");
        }
    }

    private int calculateDamage(int luck) {
        return dice.roll() + luck;
    }

    public boolean determineWinner() {
        return player.isAlive();
    }
}
