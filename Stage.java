public class Stage {

    private int stageNumber;
    private String stageName;
    private String stageDescription;
    private Enemy[] enemies;

    public Stage(int stageNumber) {
        this.stageNumber = stageNumber;

        if (stageNumber == 7) {
            this.enemies = new Enemy[1];
        }
        else {
            this.enemies = new Enemy[2];
        }

        loadStage();
    }

    public void loadStage() {

        if (stageNumber == 1) {
            stageName = "THE PRISON";

            stageDescription =
                    "The prison reeks of smoke and decay.\n"
                            + "Bodies are carried away like trash and burned in the furnaces.\n"
                            + "From somewhere deeper inside, you can hear distant screams.";

            enemies[0] = new Enemy(
                    "Jailer",
                    "This sadistic jailer enjoys playing with prisoners "
                            + "before getting rid of them.",
                    25,
                    1,
                    1,
                    30
            );

            enemies[1] = new Enemy(
                    "Torturer",
                    "Silent and patient, he watches you with a disturbing smile.",
                    30,
                    2,
                    2,
                    35
            );
        }
        else if (stageNumber == 2) {
            stageName = "THE BACKROOM";

            stageDescription =
                    "People in chains serve the casino's entertainers.\n"
                            + "Once they are no longer useful, they are discarded.";

            enemies[0] = new Enemy(
                    "The Magician",
                    "A trickster with dice. Watch his hands—and your back.",
                    35,
                    2,
                    3,
                    40
            );

            enemies[1] = new Enemy(
                    "The Ballerina",
                    "Unpredictable and dangerous. She wants a new toy.",
                    40,
                    3,
                    4,
                    45
            );
        }
        else if (stageNumber == 3) {
            stageName = "THE BAR";

            stageDescription =
                    "Music and laughter fill the room.\n"
                            + "Wild beasts wait inside iron cages for the show.\n"
                            + "The losers become their next meal.";

            enemies[0] = new Enemy(
                    "The Drinker",
                    "He drinks... and somehow knows things.",
                    45,
                    3,
                    4,
                    50
            );

            enemies[1] = new Enemy(
                    "The Mixer",
                    "No one knows what goes into his drinks. Do not ask.",
                    50,
                    4,
                    5,
                    55
            );
        }
        else if (stageNumber == 4) {
            stageName = "THE HOTEL";

            stageDescription =
                    "You awaken inside a hotel room with no windows.\n"
                            + "A strange gas slowly fills the air.\n"
                            + "There are only two telephone numbers you can call.";

            enemies[0] = new Enemy(
                    "The Plumber",
                    "Almost nothing is left behind after he finishes his work.",
                    55,
                    4,
                    5,
                    65
            );

            enemies[1] = new Enemy(
                    "The Maid",
                    "Her favorite color is the red left behind by her victims.",
                    60,
                    5,
                    6,
                    75
            );
        }
        else if (stageNumber == 5) {
            stageName = "THE MANSION";

            stageDescription =
                    "Other survivors have reached the mansion with you.\n"
                            + "The House will accept only one more guest.\n"
                            + "Everyone else must disappear.";

            enemies[0] = new Enemy(
                    "Lisa",
                    "A compulsive gambler who refuses to fold.",
                    70,
                    5,
                    6,
                    80
            );

            enemies[1] = new Enemy(
                    "Martin",
                    "A man with nothing left to lose.",
                    80,
                    6,
                    7,
                    90
            );
        }
        else if (stageNumber == 6) {
            stageName = "THE MASK PARTY";

            stageDescription =
                    "Masked guests dance inside a grand ballroom.\n"
                            + "They are waiting eagerly for the main performance.\n"
                            + "Tonight, the performance is you.";

            enemies[0] = new Enemy(
                    "Sergei",
                    "He can swallow knives. Now he wants you to share the thrill.",
                    90,
                    6,
                    7,
                    100
            );

            enemies[1] = new Enemy(
                    "Ivan",
                    "A contortionist who prefers bending other people.",
                    100,
                    7,
                    8,
                    120
            );
        }
        else if (stageNumber == 7) {
            stageName = "THE SECRET SOCIETY";

            stageDescription =
                    "The music finally stops.\n"
                            + "Dark figures surround you in silence.\n"
                            + "One figure stands above them all.";

            enemies[0] = new Enemy(
                    "The Casino Owner",
                    "The person responsible for every game, every death, "
                            + "and every debt inside the casino.",
                    150,
                    9,
                    10,
                    150
            );
        }
        else {
            stageName = "UNKNOWN";
            stageDescription = "Invalid stage.";
        }
    }

    public Enemy chooseEnemy(int choice) {

        if (stageNumber == 7) {
            return enemies[0];
        }

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

    public String getStageName() {
        return stageName;
    }

    public String getStageDescription() {
        return stageDescription;
    }

    public Enemy[] getEnemies() {
        return enemies;
    }

    public boolean isBossStage() {
        return stageNumber == 7;
    }
}