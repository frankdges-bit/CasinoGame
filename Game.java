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

        if (askToStart()){
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
            
            if (player.getCoins() >= 500){
                System.out.println("Game Over, You got 500 coins and won! \n (this is not part of the story just for testing)");
                return;
            }
            else {
                continueGame();
            }
        }
        else {
            System.out.println("Game Over.");
        }
    }

    private void continueGame() {

        if (player.getCoins() >= 50 && stage.getStageNumber() <= 3){
            System.out.println("You have spent 50 coins to get to stage " + (stage.getStageNumber()+1) +"! \n (this is not part of the story just for testing)");
            player.setCoins(player.getCoins()-50);
            stage = new Stage(stage.getStageNumber() + 1);
        }


        enemy = promptEnemyChoice();
        runCombat(enemy);
        endGame();
    }
}