import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class Game {

    private Player player;
    private Enemy enemy;
    private Dice dice;
    private Combat combat;
    private Stage stage;
    private Scanner scanner;
    private Store store;
    private ArrayList<String> chosenEnemies;
    private int secondEnemyChoices;

    public Game() {
        this.scanner = new Scanner(System.in);
        this.player = new Player();
        this.dice = new Dice(20);
        this.store = new Store(player, dice, scanner);
        this.chosenEnemies = new ArrayList<String>();
        this.secondEnemyChoices = 0;
    }

    private void waitForContinue() {
        int choice;

        System.out.println();
        System.out.println("Press 1 to continue.");

        choice = scanner.nextInt();

        while (choice != 1) {
            System.out.println("Please press 1 to continue.");
            choice = scanner.nextInt();
        }

        System.out.println();
    }

    public void start() {

        resizePrompt();

        showTitleScreen();

        boolean accepted = askToStart();

        if (!accepted) {
            System.out.println();
            System.out.println("...");
            System.out.println("A guard steps in front of the exit.");
            System.out.println();
            System.out.println("\"You do not leave until the House says you do.\"");
            System.out.println("\"This was never a choice.\"");

            waitForContinue();
        }

        showStoryIntroduction();
        for (int stageNumber = 1; stageNumber <= 7; stageNumber++) {

            stage = new Stage(stageNumber);

            displayStageTitle();

            if (stage.isBossStage()) {
                enemy = loadFinalBoss();
            }
            else {
                enemy = promptEnemyChoice();
            }

            runCombat(enemy);
            endGame();

            if (!player.isAlive()) {
                createScoreFile(false);
                return;
            }

            if (stageNumber < 7) {

                if (stageNumber == 4) {
                    displayHotelTransition();
                }

                prepareForNextStage();
            }
        }

        displayVictoryEnding();
        createScoreFile(true);
    }

    private void showTitleScreen() {
        System.out.println();
        System.out.println(" _______  _______  _______  _______  _______  _______   _________          _______    _______  _______  _______ _________ _        _______ ");
        System.out.println("(  ____ \\(  ____ \\(  ____ \\(  ___  )(  ____ )(  ____ \\  \\__   __/|\\     /|(  ____ \\  (  ____ \\(  ___  )(  ____ \\\\__   __/( (    /|(  ___  )");
        System.out.println("| (    \\/| (    \\/| (    \\/| (   ) || (    )|| (    \\/     ) (   | )   ( || (    \\/  | (    \\/| (   ) || (    \\/   ) (   |  \\  ( || (   ) |");
        System.out.println("| (__    | (_____ | |      | (___) || (____)|| (__         | |   | (___) || (__      | |      | (___) || (_____    | |   |   \\ | || |   | |");
        System.out.println("|  __)   (_____  )| |      |  ___  ||  _____)|  __)        | |   |  ___  ||  __)     | |      |  ___  |(_____  )   | |   | (\\ \\) || |   | |");
        System.out.println("| (            ) || |      | (   ) || (      | (           | |   | (   ) || (        | |      | (   ) |      ) |   | |   | | \\   || |   | |");
        System.out.println("| (____/\\/\\____) || (____/\\| )   ( || )      | (____/\\     | |   | )   ( || (____/\\  | (____/\\| )   ( |/\\____) |___) (___| )  \\  || (___) |");
        System.out.println("(_______/\\_______)(_______/|/     \\||/       (_______/     )_(   |/     \\|(_______/  (_______/|/     \\|\\_______)\\_______/|/    )_)(_______)");
        System.out.println();
        System.out.println();
        System.out.println("                                                         ______________");
        System.out.println("                                            __,.,---'''''              '''''---..._");
        System.out.println("                                         ,-'             .....:::''::.:            '`-.");
        System.out.println("                                       ''           ...:::.....       '                 |");
        System.out.println("                                      |             ''':::'''''       .               ,");
        System.out.println("                                       '|'-.._           ''''':::..::':          __,,-");
        System.out.println("                                         '-.._''`---.....______________.....---''__,,-");
        System.out.println("                                              ''`---.....______________.....---''");
        System.out.println();
        System.out.println();
    }

    private void showStoryIntroduction() {
        System.out.println();
        System.out.println("You are trapped inside a casino.");
        System.out.println();
        System.out.println(
                "You lost more money than you could ever repay."
        );
        System.out.println();
        System.out.println(
                "Now the House owns your debt—and your life."
        );
        System.out.println();
        System.out.println(
                "You are being forced to fight for your freedom."
        );
        System.out.println();
        System.out.println("Win, and you may leave.");
        System.out.println();
        System.out.println("Lose...");
        System.out.println();
        System.out.println("...and you die.");
        System.out.println();
        waitForContinue();
    }
    private void resizePrompt() {
        int input;
        do {
            System.out.print("\033[0;33;49m --------------------------------------------------------------------------------------" +
                    "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n \033[0;35;49m " +
                    "Please resize your terminal until the yellow line is visible. Enter 1 to continue: \033[0;37;49m");
            input = scanner.nextInt();
        } while (input != 1);
    }

    private boolean askToStart() {
        System.out.println("1. Start");
        System.out.println("2. Quit");

        int choice = scanner.nextInt();

        while (choice != 1 && choice != 2) {
            System.out.println("Please enter 1 or 2.");
            choice = scanner.nextInt();
        }

        return choice == 1;
    }

    private void displayStageTitle() {
        System.out.println();

        if (stage.isBossStage()) {
            System.out.println("==================================");
            System.out.println("           FINAL STAGE");
            System.out.println("==================================");
        }
        else {
            System.out.println("==================================");
            System.out.println(
                    "             STAGE "
                            + stage.getStageNumber()
            );
            System.out.println("==================================");
        }

        System.out.println();
        System.out.println(stage.getStageName());
        System.out.println();
        System.out.println(stage.getStageDescription());
        System.out.println();
        waitForContinue();
    }

    private Enemy promptEnemyChoice() {
        Enemy[] enemies = stage.getEnemies();

        System.out.println();
        System.out.println("Choose your opponent:");

        displayEnemy(1, enemies[0]);
        displayEnemy(2, enemies[1]);

        int choice = scanner.nextInt();

        while (choice < 1 || choice > 2) {
            System.out.println("Please enter 1 or 2.");
            choice = scanner.nextInt();
        }

        Enemy selectedEnemy = stage.chooseEnemy(choice);

        chosenEnemies.add(selectedEnemy.getName());

        if (choice == 2) {
            secondEnemyChoices++;
        }

        return selectedEnemy;
    }

    private Enemy loadFinalBoss() {
        Enemy finalBoss = stage.chooseEnemy(1);
        chosenEnemies.add(finalBoss.getName());

        System.out.println();
        System.out.println("The dark figures remain completely silent.");
        System.out.println();
        System.out.println(
                finalBoss.getName()
                        + " rises above them."
        );
        System.out.println();
        waitForContinue();

        System.out.println(
                "\"This is where the music stops, dear player.\""
        );

        System.out.println(
                "\"You were never intended to succeed.\""
        );

        System.out.println(
                "\"All the people you defeated to reach this room...\""
        );

        System.out.println(
                "\"They will greet you soon enough.\""
        );
        waitForContinue();

        displayEnemy(1, finalBoss);

        return finalBoss;
    }

    private void displayEnemy(int number, Enemy enemy) {
        System.out.println();
        System.out.println(number + ". " + enemy.getName());
        System.out.println("   " + enemy.getDescription());
        System.out.println();
        System.out.println("   Life: " + enemy.getLife());
        System.out.println("   Luck: " + enemy.getLuck());
        System.out.println(
                "   Difficulty: " + enemy.getDifficulty()
        );

        if (stage.isBossStage()) {
            System.out.println("   Reward: Freedom");
        }
        else {
            System.out.println(
                    "   Reward: "
                            + enemy.getReward()
                            + " coins"
            );
        }
    }

    private void displayHotelTransition() {
        System.out.println();
        System.out.println("The lights suddenly go out.");
        System.out.println();
        System.out.println(
                "Before you can react, a cloth is pulled over your face."
        );
        System.out.println();
        System.out.println("You are dragged away.");
        System.out.println();
        System.out.println("When you awaken...");
        System.out.println();
        System.out.println("You are somewhere else.");
        waitForContinue();
    }

    public void runCombat(Enemy enemy) {
        combat = new Combat(
                player,
                enemy,
                dice,
                scanner
        );

        combat.startBattle();
    }

    private void endGame() {
        if (combat.determineWinner()) {
            System.out.println();
            System.out.println("Victory!");

            if (stage.isBossStage()) {
                System.out.println("Reward: Freedom.");
            }
            else {
                player.addCoins(enemy.getReward());

                System.out.println(
                        "You earned "
                                + enemy.getReward()
                                + " coins."
                );

                System.out.println(
                        "Total coins: "
                                + player.getCoins()
                );
            }
        }
        else {
            System.out.println();
            System.out.println("You collapse to the floor.");
            System.out.println();
            System.out.println("\"The House always collects.\"");
            System.out.println();
            System.out.println(" ______     ______     __    __     ______           ______     __   __   ______     ______  ");
            System.out.println("/\\  ___\\   /\\  __ \\   /\\ \"-./  \\   /\\  ___\\         /\\  __ \\   /\\ \\ / /  /\\  ___\\   /\\  == \\  ");
            System.out.println("\\ \\ \\__ \\  \\ \\  __ \\  \\ \\ \\-./\\ \\  \\ \\  __\\         \\ \\ \\/\\ \\  \\ \\ \\'/   \\ \\  __\\   \\ \\  __<  ");
            System.out.println(" \\ \\_____\\  \\ \\_\\ \\_\\  \\ \\_\\ \\ \\_\\  \\ \\_____\\        \\ \\_____\\  \\ \\__|    \\ \\_____\\  \\ \\_\\ \\_\\ ");
            System.out.println("  \\/_____/   \\/_/\\/_/   \\/_/  \\/_/   \\/_____/         \\/_____/   \\/_/      \\/_____/   \\/_/ /_/ ");
        }
    }

    private void prepareForNextStage() {
        System.out.println();
        System.out.println("You advance to the next stage.");

        player.heal(5);

        System.out.println("You recover 5 life.");
        System.out.println(
                "Current life: "
                        + player.getLife()
                        + "/"
                        + player.getMaxLife()
        );

        System.out.println("\nGoing to store in 5 seconds...");

        try {
            Thread.sleep(5000);
        }
        catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }

        store.openStore();

        System.out.println();
        System.out.println("You leave the store.");

        if (stage.getStageNumber() == 6) {
            System.out.println(
                    "The final arena is waiting."
            );
        }
        else {
            System.out.println(
                    "The next arena is waiting."
            );
        }
    }

    private void displayVictoryEnding() {
        System.out.println();
        System.out.println("==============================");
        System.out.println("The Casino Owner falls.");
        System.out.println();
        System.out.println("The casino becomes silent.");
        System.out.println("The guards slowly step aside.");
        System.out.println();
        System.out.println(
                "\"You have defeated the House.\""
        );
        System.out.println(
                "\"You have earned your freedom.\""
        );
        System.out.println();
        System.out.println("The front doors unlock.");
        System.out.println("You step outside.");
        System.out.println();
        System.out.println(" __     __                                              _   _   _                          _             ");
        System.out.println(" \\ \\   / /                                             | | | | | |                        (_)            ");
        System.out.println("  \\ \\_/ /__  _   _    ___  ___  ___ __ _ _ __   ___  __| | | |_| |__   ___    ___ __ _ ___ _ _ __   ___  ");
        System.out.println("   \\   / _ \\| | | |  / _ \\/ __|/ __/ _` | '_ \\ / _ \\/ _` | | __| '_ \\ / _ \\  / __/ _` / __| | '_ \\ / _ \\ ");
        System.out.println("    | | (_) | |_| | |  __/\\__ \\ (_| (_| | |_) |  __/ (_| | | |_| | | |  __/ | (_| (_| \\__ \\ | | | | (_) |");
        System.out.println("    |_|\\___/ \\__,_|  \\___||___/\\___\\__,_| .__/ \\___|\\__,_|  \\__|_| |_|\\___|  \\___\\__,_|___/_|_| |_|\\___/ ");
        System.out.println("                                        | |                                                              ");
        System.out.println("                                        |_|                                                              ");
        System.out.println();
    }

    private void createScoreFile(boolean escaped) {

        /*
         * nextInt() leaves a newline in the Scanner.
         * This clears that newline before reading the name.
         */
        scanner.nextLine();

        System.out.println();
        System.out.println("==============================");
        System.out.println("       ARCADE RECORD");
        System.out.println("==============================");
        System.out.print("Enter your name or initials: ");

        String playerName = scanner.nextLine().trim();

        while (playerName.isEmpty()) {
            System.out.print("Please enter a name or initials: ");
            playerName = scanner.nextLine().trim();
        }

        int goldScore = player.getCoins();
        int stageScore = stage.getStageNumber() * 10;
        int enemyBonus = secondEnemyChoices * 10;

        int totalScore =
                goldScore
                        + stageScore
                        + enemyBonus;

        /*
         * Remove characters that cannot safely be used
         * as part of a file name.
         */
        String safeName =
                playerName.replaceAll(
                        "[^a-zA-Z0-9_-]",
                        "_"
                );

        String fileName =
                safeName + "_CasinoScore.txt";

        try {
            FileOutputStream fileStream =
                    new FileOutputStream(fileName);

            PrintWriter scoreWriter =
                    new PrintWriter(fileStream);

            scoreWriter.println("==============================");
            scoreWriter.println("       ESCAPE THE CASINO");
            scoreWriter.println("==============================");
            scoreWriter.println();

            scoreWriter.println("Name: " + playerName);
            scoreWriter.println();

            scoreWriter.println("Enemies Fought:");

            for (int i = 0; i < chosenEnemies.size(); i++) {
                scoreWriter.println(
                        (i + 1)
                                + ". "
                                + chosenEnemies.get(i)
                );
            }

            scoreWriter.println();
            scoreWriter.println("SCORES");
            scoreWriter.println("------------------------------");
            scoreWriter.println("Gold: " + goldScore);
            scoreWriter.println(
                    "Stage Score: "
                            + stageScore
            );
            scoreWriter.println(
                    "Enemy Bonus: "
                            + enemyBonus
            );
            scoreWriter.println("------------------------------");
            scoreWriter.println("Total: " + totalScore);
            scoreWriter.println();

            if (escaped) {
                scoreWriter.println(
                        "You escaped the casino."
                );
            }
            else {
                scoreWriter.println(
                        "You did not escape the casino."
                );
            }

            scoreWriter.println();
            scoreWriter.println("Thank you for playing.");

            scoreWriter.close();

            System.out.println();
            System.out.println(
                    "Your score was saved in:"
            );
            System.out.println(fileName);
            System.out.println();
            System.out.println("Final score: " + totalScore);
        }
        catch (IOException exception) {
            System.out.println(
                    "The score file could not be created."
            );
        }
    }
}


