package agh.ics.oop.presenters;

import agh.ics.oop.Simulation;
import agh.ics.oop.Statistics.Statistics;
import agh.ics.oop.model.GameMap;
import agh.ics.oop.model.MutationType;
import agh.ics.oop.model.PlantsType;
import agh.ics.oop.model.Vector2d;


import agh.ics.oop.model.elements.Animal;
import agh.ics.oop.model.elements.Genotype;
import agh.ics.oop.model.elements.Square;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.control.Slider;

import java.util.List;

import static java.lang.Math.min;


public class SimulationGUI extends Application {
    private Slider numberOfAnimalsField;
    private Slider numberOfPlantsField;
    private Slider startingEnergyField;
    private Slider minMutatedGenesField;
    private Slider maxMutatedGenesField;
    private Slider energyOfPlantField;
    private Slider numberOfDaysField;
    private Slider genomeSizeField;
    private Slider energyNeededForCopulationField;
    private Slider energyUsedForCopulationField;
    private Slider numberOfPlantsPerDayField;
    private Slider mapWidthField;
    private Slider mapHeightField;
    private TextArea statisticsTextArea;
    private TextArea statisticsPerAnimalTextArea;
    private int currentDay;
    private Simulation simulation;
    private boolean isSimulationRunning = false;
    private Button stopButton;
    private Button popularGenButton;
    private Button stopTrackingButton;
    private MapPanel mapPanel;
    private ComboBox<MutationType> mutationTypeComboBox;
    private ComboBox<PlantsType> plantsTypeComboBox;
    private Timeline simulationTimeline;
    private Stage mapStage;
    private int[] dayOfDeath;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Simulation GUI");

        numberOfAnimalsField = createSlider(0, 1000, 500, 100);
        numberOfPlantsField = createSlider(0, 1000, 300, 100);
        startingEnergyField = createSlider(3, 100, 10, 10);
        numberOfDaysField = createSlider(0, 1000, 100, 100);
        minMutatedGenesField = createSlider(0, 10, 1, 1);
        maxMutatedGenesField = createSlider(0, 10, 3, 1);
        energyOfPlantField = createSlider(0, 30, 5, 1);
        genomeSizeField = createSlider(0, 10, 5, 1);
        energyNeededForCopulationField = createSlider(0, 20, 8, 1);
        energyUsedForCopulationField = createSlider(0, 20, 5, 1);
        numberOfPlantsPerDayField = createSlider(0, 500, 10, 10);
        mapWidthField = createSlider(10, 100, 20, 10);
        mapHeightField = createSlider(10, 100, 20, 10);
        mutationTypeComboBox = new ComboBox<>();
        plantsTypeComboBox = new ComboBox<>();

        dayOfDeath = new int[1];

        statisticsTextArea = new TextArea();
        statisticsTextArea.setEditable(false);

        statisticsPerAnimalTextArea = new TextArea();
        statisticsPerAnimalTextArea.setEditable(false);



        Button startButton = new Button("Start");




        GridPane inputGrid = new GridPane();
        inputGrid.setHgap(10);
        inputGrid.setVgap(20);
        inputGrid.setPadding(new Insets(10, 10, 10, 10));

        addLabelAndField(inputGrid,0, 0, "Map width:", mapWidthField);
        addLabelAndField(inputGrid,0, 1, "Map height:", mapHeightField);
        addLabelAndField(inputGrid,0, 2, "Number of animals:", numberOfAnimalsField);
        addLabelAndField(inputGrid,0, 3, "Number of plants:", numberOfPlantsField);
        addLabelAndField(inputGrid,0, 4, "Starting animal energy:", startingEnergyField);
        addLabelAndField(inputGrid,0, 5, "Number of days:", numberOfDaysField);
        addLabelAndField(inputGrid,0, 6, "Min mutated genes:", minMutatedGenesField);
        addLabelAndField(inputGrid,0, 7, "Max mutated genes:", maxMutatedGenesField);
        addLabelAndField(inputGrid,0, 8, "Energy of plant:", energyOfPlantField);
        addLabelAndField(inputGrid,0, 9, "Genome size:", genomeSizeField);
        addLabelAndField(inputGrid,0, 10, "Energy needed for copulation:", energyNeededForCopulationField);
        addLabelAndField(inputGrid,0, 11, "Energy used for copulation:", energyUsedForCopulationField);
        addLabelAndField(inputGrid,0, 12, "Number of plants per day:", numberOfPlantsPerDayField);
        addLabelAndField(inputGrid,10, 1, "Mutation type:", mutationTypeComboBox);
        addLabelAndField(inputGrid,10, 2, "Plants type:", plantsTypeComboBox);

        mutationTypeComboBox.getItems().addAll(MutationType.values());
        plantsTypeComboBox.getItems().addAll(PlantsType.values());

        inputGrid.add(startButton, 10, 0, 2, 1);

        BorderPane mainPane = new BorderPane();
        mainPane.setTop(inputGrid);


        Scene scene = new Scene(mainPane, 1100, 750);

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!isSimulationRunning) {
                    startSimulation();
                    isSimulationRunning = true;

                    mapStage = new Stage();
                    mapStage.setTitle("Simulation Map");
                    mapStage.setScene(createMapScene());
                    mapStage.setOnCloseRequest(closeEvent -> {
                        stopSimulation();
                        isSimulationRunning = false;
                    });
                    mapStage.show();

                }
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addLabelAndField(GridPane grid,int col, int row, String labelText, Control control) {
        grid.add(new Label(labelText), col, row);
        grid.add(control, col+1, row);
    }

    private Scene createMapScene(){
        BorderPane mapPane = new BorderPane();
        mapPane.setPadding(new Insets(10, 10, 10, 10));

        mapPanel = new MapPanel();
        mapPanel.setMinSize(700, 700);
        mapPanel.setMaxSize(700, 700);

        VBox mapVBox = new VBox(mapPanel);

        stopButton = new Button("Stop");
        stopButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pauseSimulation();
            }
        });

        popularGenButton = new Button("Popular Gen");
        popularGenButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showPopularGen();
            }
        });

        stopTrackingButton = new Button("Stop tracking animal");

        stopTrackingButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mapPanel.setAnimalColor(mapPanel.getChosenAnimal(), Color.web("#999900"));
                mapPanel.setChosenAnimal(null);
                statisticsPerAnimalTextArea.setVisible(false);
                stopTrackingButton.setVisible(false);
                dayOfDeath[0] = Integer.MAX_VALUE;
            }
        });

        VBox statisticsHBox = new VBox(statisticsTextArea,statisticsPerAnimalTextArea,stopTrackingButton);

        statisticsPerAnimalTextArea.setVisible(false);
        stopTrackingButton.setVisible(false);

        mapPane.setCenter(mapVBox);
        mapPane.setRight(statisticsHBox);


        HBox buttonsHBox = new HBox(stopButton,popularGenButton);

        mapPane.setBottom(buttonsHBox);

        Scene mapScene = new Scene(mapPane, 1200, 750);

        return mapScene;
    }

    private void startSimulation() {
        int numberOfAnimals = (int) numberOfAnimalsField.getValue();
        int numberOfPlants = (int) numberOfPlantsField.getValue();
        int startingEnergy = (int) startingEnergyField.getValue();
        int numberOfDays = (int) numberOfDaysField.getValue();
        int minMutatedGenes = (int) minMutatedGenesField.getValue();
        int maxMutatedGenes = (int) maxMutatedGenesField.getValue();
        int energyOfPlant = (int) energyOfPlantField.getValue();
        int genomeSize = (int) genomeSizeField.getValue();
        int energyNeededForCopulation = (int) energyNeededForCopulationField.getValue();
        int energyUsedForCopulation = (int) energyUsedForCopulationField.getValue();
        int numberOfPlantsPerDay = (int) numberOfPlantsPerDayField.getValue();
        int mapWidth = (int) mapWidthField.getValue();
        int mapHeight = (int) mapHeightField.getValue();
        MutationType selectedMutationType = mutationTypeComboBox.getValue();
        PlantsType selectedPlantsType = plantsTypeComboBox.getValue();

        runSimulation(numberOfAnimals, numberOfPlants,startingEnergy, numberOfDays,
                minMutatedGenes, maxMutatedGenes, energyOfPlant,
                genomeSize, energyNeededForCopulation, energyUsedForCopulation,
                numberOfPlantsPerDay, mapWidth, mapHeight,selectedMutationType,selectedPlantsType);
    }



    private void pauseSimulation() {
        if(simulationTimeline != null) {
            if(simulationTimeline.getStatus() == Timeline.Status.RUNNING){
                simulationTimeline.pause();
                isSimulationRunning = false;
                stopButton.setText("Resume");

            } else{
                simulationTimeline.play();
                isSimulationRunning = true;
                stopButton.setText("Stop");
            }

        }

    }

    private void stopSimulation(){
        if(simulationTimeline != null){
            simulationTimeline.stop();
        }
        if(simulation != null){
            simulation = null;
        }
        if(statisticsTextArea != null){
            statisticsTextArea.clear();
        }
        if(statisticsPerAnimalTextArea != null){
            statisticsPerAnimalTextArea.clear();
        }

        if(mapPanel != null){
            mapPanel.setMap(null);
        }
        currentDay = 0;
        if (stopButton != null) {
            stopButton.setText("Stop");
        }
    }

    public void showPopularGen(){
        if(simulationTimeline != null){
            /*if(simulationTimeline.getStatus() != Timeline.Status.RUNNING) {*/

                Statistics statistics = new Statistics(simulation.getMap());
                Genotype mostCommonGenotype = statistics.getMostCommonGenotype();

                for (Square square : simulation.getMap().getAllSquares()) {
                    List<Animal> animals = square.getAnimals();
                    for (Animal currAnimal : animals) {
                        if (currAnimal.getGenotype().equals(mostCommonGenotype)) {
                            mapPanel.setAnimalColor(currAnimal, Color.RED);
                        }
                    }

                }


        }
    }


    private void runSimulation(int numberOfAnimals, int numberOfPlants,int startingEnergy, int numberOfDays,
                               int minMutatedGenes, int maxMutatedGenes, int energyOfPlant,
                               int genomeSize, int energyNeededForCopulation, int energyUsedForCopulation,
                               int numberOfPlantsPerDay, int mapWidth, int mapHeight, MutationType mutationType, PlantsType plantsType){
        Vector2d lowerLeft = new Vector2d(0, 0);
        Vector2d upperRight = new Vector2d(mapWidth, mapHeight);

        GameMap map = new GameMap(lowerLeft, upperRight, mutationType, plantsType);

        simulation = new Simulation(map);

        map.createMap(numberOfAnimals,numberOfPlants,startingEnergy,energyOfPlant,genomeSize);

        Statistics statistics = new Statistics(map);



        currentDay = 0;


        dayOfDeath[0] = Integer.MAX_VALUE;

        simulationTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (currentDay < numberOfDays) {
                if(currentDay == 0){
                    mapPanel.setMap(map);
                    statisticsTextArea.setText("Day " + currentDay + ":\n" + statistics.show() + "\n\n");
                    currentDay++;
                }else{
                    currentDay++;
                    simulation.run(minMutatedGenes, maxMutatedGenes, energyOfPlant,
                            energyNeededForCopulation, energyUsedForCopulation,
                            numberOfPlantsPerDay);

                    mapPanel.setMap(map);

                    statisticsTextArea.setText("Day " + currentDay + ":\n" + statistics.show() + "\n\n");

                    Animal chosenAnimal = mapPanel.getChosenAnimal();
                    if(chosenAnimal != null){
                        statisticsPerAnimalTextArea.setVisible(true);
                        stopTrackingButton.setVisible(true);
                        statisticsPerAnimalTextArea.setText(chosenAnimal.showStats());
                        dayOfDeath[0] = Integer.MAX_VALUE;
                        if(chosenAnimal.isDead()){
                            dayOfDeath[0] = min(currentDay,dayOfDeath[0]);
                            int animalDayOfDeath = chosenAnimal.getDayOfDeath();
                            if(animalDayOfDeath == -1){
                                chosenAnimal.setDayOfDeath(min(currentDay,dayOfDeath[0]));
                                animalDayOfDeath = chosenAnimal.getDayOfDeath();
                            }
                            statisticsPerAnimalTextArea.setText(chosenAnimal.showStats() + "\n" + "Day of death: " + animalDayOfDeath);
                        }

                    }
                }

            } else {
                simulationTimeline.stop();
            }
        }));

        simulationTimeline.setCycleCount(Timeline.INDEFINITE);
        simulationTimeline.play();

    }

    private Slider createSlider(double min, double max, double initialValue,int tickUnit) {
        Slider slider = new Slider();
        slider.setMin(min);
        slider.setMax(max);
        slider.setValue(initialValue);
        slider.setPrefWidth(500);

        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);

        slider.setMajorTickUnit(tickUnit);

        return slider;
    }
}

