import java.util.Scanner;

public class Game {
    private Player player;
    private Enemy enemy;
    private Dice dice;
    private Combat combat;
    private Stage stage;
    private Scanner scanner;

    public Game() {
        scanner = new Scanner(System.in);
        player = new Player();
        dice = new Dice(20);
        stage = new Stage(1);
    }

    public void start() {
        showIntro();

        if (askToStart()) {
            enemy = promptEnemyChoice();
            runCombat(enemy);
            endGame();
        }
        else {
            System.out.println("Maybe next time.");
        }
    }

    private void showIntro() {
        System.out.println("Welcome to the casino.");
        System.out.println("Where fortunes are made and lives are wagered.");
    }

    private boolean askToStart() {
        System.out.println("Do you want to play?");
        System.out.println("1. Yes");
        System.out.println("2. No");

        int choice = scanner.nextInt();

        return choice == 1;
    }

    private Enemy promptEnemyChoice() {
        Enemy[] enemies = stage.getEnemies();

        System.out.println("Choose your opponent:");
        System.out.println("1. " + enemies[0].getName());
        System.out.println("2. " + enemies[1].getName());

        int choice = scanner.nextInt();

        return stage.chooseEnemy(choice);
    }

    public void runCombat(Enemy enemy) {
        combat = new Combat(player, enemy, dice);
        combat.startBattle();
    }

    private void endGame() {
        if (combat.determineWinner()) {
            System.out.println("Victory!");
            player.addCoins(enemy.getReward());
            System.out.println("You earned " + enemy.getReward() + " coins.");
            System.out.println("Total coins: " + player.getCoins());
        }
        else {
            System.out.println("Game Over.");
        }
    }
}