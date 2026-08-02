import java.util.Scanner;

public class Store {

    private final int SILVER_COST = 40;
    private final int GOLD_COST = 80;
    private final int DOUBLE_ROLL_COST = 15;
    private final int POTION_COST = 5;
    private final int ARMOR_COST = 100;

    private Player player;
    private Dice dice;
    private Scanner scanner;

    public Store(Player player, Dice dice, Scanner scanner) {
        this.player = player;
        this.dice = dice;
        this.scanner = scanner;
    }

    public void openStore() {
        int choice;

        do {
            displayStore();
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    buySilverDice();
                    break;

                case 2:
                    buyGoldDice();
                    break;

                case 3:
                    buyDoubleRoll();
                    break;

                case 4:
                    buyPotion();
                    break;

                case 5:
                    buyArmor();
                    break;

                case 6:
                    System.out.println("You leave the store.");
                    break;

                default:
                    System.out.println("Please enter a number from 1 to 6.");
            }

        } while (choice != 6);
    }

    private void displayStore() {
        System.out.println();
        System.out.println("========================");
        System.out.println("      CASINO STORE");
        System.out.println("========================");
        System.out.print("Coins: " + player.getCoins() + "  ");

        System.out.print(
                "Life: "
                        + player.getLife()
                        + "/"
                        + player.getMaxLife()
                        + "  "
        );

        System.out.println("Dice bonus: +" + dice.getBonus());

        System.out.println(
                "Double Roll Tokens: "
                        + player.getDoubleRollTokens()
                        + "/"
                        + player.getDoubleRollLimit()
        );

        System.out.println(
                "Armor: "
                        + (player.hasArmor() ? "Equipped" : "Not equipped")
        );

        System.out.println();

        System.out.println("1. Silver Dice: Permanent +3 to rolls - 40 coins");
        System.out.println("   Critical chance improves to 1/4");
        System.out.println("   Improves the Weighted Roll");

        System.out.println();

        System.out.println("2. Gold Dice Upgrade:  Permanent +7 to rolls - 80 coins");
        System.out.println("   Critical chance improves to 1/3");
        System.out.println("   Further improves the Weighted Roll");
        System.out.println("   Requires Silver Dice");

        System.out.println();

        System.out.println("3. Double Roll Token: Roll two dice during one attack - 15 coins");

        System.out.println();

        System.out.println("4. Life Potion: Restore 5 life - 5 coins");

        System.out.println();

        System.out.println("5. Armor: Increase maximum life by 20 - 100 coins");
        System.out.println("   Increase Double Roll Token limit to 3");

        System.out.println();

        System.out.println("6. Leave Store");
    }

    private void buySilverDice() {
        if (dice.getBonus() >= 3) {
            System.out.println("You already own the Silver Dice upgrade.");
        }
        else if (player.getCoins() < SILVER_COST) {
            System.out.println("You do not have enough coins.");
        }
        else {
            player.removeCoins(SILVER_COST);
            dice.setBonus(3);

            System.out.println("Silver Dice purchased.");
            System.out.println("Your rolls now receive +3.");
        }
    }

    private void buyGoldDice() {
        if (dice.getBonus() == 7) {
            System.out.println("You already own the Gold Dice.");
        }
        else if (dice.getBonus() < 3) {
            System.out.println("You must purchase the Silver Dice first.");
        }
        else if (player.getCoins() < GOLD_COST) {
            System.out.println("You do not have enough coins.");
        }
        else {
            player.removeCoins(GOLD_COST);
            dice.setBonus(7);

            System.out.println("Gold Dice purchased.");
            System.out.println("Your permanent dice bonus is now +7.");
        }
    }

    private void buyDoubleRoll() {

        if (player.getDoubleRollTokens()
                >= player.getDoubleRollLimit()) {

            System.out.println(
                    "You cannot carry more than "
                            + player.getDoubleRollLimit()
                            + " Double Roll Tokens."
            );
        }
        else if (player.getCoins() < DOUBLE_ROLL_COST) {
            System.out.println("You do not have enough coins.");
        }
        else {
            player.removeCoins(DOUBLE_ROLL_COST);
            player.addDoubleRollToken();

            System.out.println("Double Roll Token purchased.");

            System.out.println(
                    "Tokens: "
                            + player.getDoubleRollTokens()
                            + "/"
                            + player.getDoubleRollLimit()
            );
        }
    }

    private void buyPotion() {

        if (player.getLife() >= player.getMaxLife()) {
            System.out.println("Your life is already full.");
        }
        else if (player.getCoins() < POTION_COST) {
            System.out.println("You do not have enough coins.");
        }
        else {
            player.removeCoins(POTION_COST);
            player.heal(5);

            System.out.println("You recovered 5 life.");

            System.out.println(
                    "Current life: "
                            + player.getLife()
                            + "/"
                            + player.getMaxLife()
            );
        }
    }

    private void buyArmor() {

        if (player.hasArmor()) {
            System.out.println("You already own the armor.");
        }
        else if (player.getCoins() < ARMOR_COST) {
            System.out.println("You do not have enough coins.");
        }
        else {
            player.removeCoins(ARMOR_COST);
            player.equipArmor();

            System.out.println("Armor purchased.");
            System.out.println("Your maximum life increased by 20.");

            System.out.println(
                    "Current life: "
                            + player.getLife()
                            + "/"
                            + player.getMaxLife()
            );

            System.out.println(
                    "Your Double Roll Token limit increased to "
                            + player.getDoubleRollLimit()
                            + "."
            );
        }
    }
}
