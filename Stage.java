public class Stage {

    private int stageNumber;
    private Enemy[] enemies;

    public Stage(int stageNumber) {
        this.stageNumber = stageNumber;
        this.enemies = new Enemy[2];
        loadStage();
    }

    public void loadStage() {
        if (stageNumber == 1) {
            enemies[0] = new Enemy("Dealer", 15, 1, 1, 10);
            enemies[1] = new Enemy("Pit Boss", 20, 2, 2, 15);

        }
        else if (stageNumber == 2) {
            enemies[0] = new Enemy("Card Shark", 25, 2, 3, 20);
            enemies[1] = new Enemy("House Guard", 30, 3, 4, 25);
        }
        else {
            enemies[0] = new Enemy("Casino Owner", 40, 4, 5, 50);
            enemies[1] = new Enemy("The House", 50, 5, 6, 100);
        }
    }

    public Enemy chooseEnemy(int choice) {
        if (choice == 1) {
            return enemies[0];
        }
        else {
            return enemies[1];

        }
    }

    public int getStageNumber() {
        return stageNumber;
    }

    public Enemy[] getEnemies() {
        return enemies;
    }
}