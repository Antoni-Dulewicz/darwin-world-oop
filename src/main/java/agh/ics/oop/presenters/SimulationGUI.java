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
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.control.Slider;
import java.util.List;

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
    private Button stopTrackingButton;
    private MapPanel mapPanel;
    private ComboBox<MutationType> mutationTypeComboBox;
    private ComboBox<PlantsType> plantsTypeComboBox;
    private Timeline simulationTimeline;
    private Stage mapStage;

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Simulation GUI");
        VBox centerVBox = new VBox();
        centerVBox.setPadding(new Insets(20, 100, 70, 100));
        centerVBox.setSpacing(10);

        mapWidthField = createSlider(10, 100, 20, 10);
        addLabelAndField(centerVBox, "Map width:", mapWidthField);

        mapHeightField = createSlider(10, 100, 20, 10);
        addLabelAndField(centerVBox, "Map height:", mapHeightField);

        numberOfAnimalsField = createSlider(0, 1000, 500, 100);
        addLabelAndField(centerVBox, "Number of animals:", numberOfAnimalsField);

        numberOfPlantsField = createSlider(0, 1000, 300, 100);
        addLabelAndField(centerVBox, "Number of plants:", numberOfPlantsField);

        startingEnergyField = createSlider(4, 100, 10, 8);
        addLabelAndField(centerVBox, "Starting animal energy:", startingEnergyField);

        numberOfDaysField = createSlider(0, 1000, 100, 100);
        addLabelAndField(centerVBox, "Number of days:", numberOfDaysField);

        minMutatedGenesField = createSlider(0, 10, 1, 1);
        addLabelAndField(centerVBox, "Min mutated genes:", minMutatedGenesField);

        maxMutatedGenesField = createSlider(0, 10, 3, 1);
        addLabelAndField(centerVBox,"Max mutated genes:", maxMutatedGenesField);

        energyOfPlantField = createSlider(0, 30, 5, 3);
        addLabelAndField(centerVBox,"Energy of plant:", energyOfPlantField);

        genomeSizeField = createSlider(0, 10, 5, 1);
        addLabelAndField(centerVBox, "Genome size:", genomeSizeField);

        energyNeededForCopulationField = createSlider(0, 20, 8, 2);
        addLabelAndField(centerVBox, "Energy needed for copulation:", energyNeededForCopulationField);

        energyUsedForCopulationField = createSlider(0, 20, 5, 2);
        addLabelAndField(centerVBox, "Energy used for copulation:", energyUsedForCopulationField);

        numberOfPlantsPerDayField = createSlider(0, 500, 10, 50);
        addLabelAndField(centerVBox, "Number of plants per day:", numberOfPlantsPerDayField);

        mutationTypeComboBox = new ComboBox<>();
        mutationTypeComboBox.setPrefSize(160, 50);
        addLabelAndField(centerVBox, "Mutation type:", mutationTypeComboBox);
        mutationTypeComboBox.getItems().addAll(MutationType.values());

        plantsTypeComboBox = new ComboBox<>();
        plantsTypeComboBox.setPrefSize(160, 50);
        addLabelAndField(centerVBox, "Plants type:", plantsTypeComboBox);
        plantsTypeComboBox.getItems().addAll(PlantsType.values());

        Region spacer = new Region();
        spacer.setMinHeight(10);
        centerVBox.getChildren().add(spacer);
        Button startButton = new Button("Start");
        startButton.setPrefSize(160, 50);
        centerVBox.getChildren().add(startButton);

        statisticsTextArea = new TextArea();
        statisticsTextArea.setEditable(false);

        statisticsPerAnimalTextArea = new TextArea();
        statisticsPerAnimalTextArea.setEditable(false);

        ScrollPane mainScrollPane = new ScrollPane(centerVBox);
        mainScrollPane.setFitToWidth(true);
        mainScrollPane.setFitToHeight(true);

        Scene scene = new Scene(mainScrollPane, 1100, 750);

        startButton.setOnAction(event -> {
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
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private void addLabelAndField(VBox vbox, String labelText, Control control) {
        Label label = new Label(labelText);
        label.setFont(new Font(12));
        vbox.getChildren().addAll(label, control);
    }


    private Scene createMapScene(){
        BorderPane mapPane = new BorderPane();
        mapPane.setPadding(new Insets(10, 10, 10, 10));

        mapPanel = new MapPanel();
        mapPanel.setMinSize(700, 700);
        mapPanel.setMaxSize(700, 700);

        stopButton = new Button("Stop");
        stopButton.setOnAction(event -> pauseSimulation());

        Button popularGenButton = new Button("Popular Gen");
        popularGenButton.setOnAction(event -> showPopularGen());

        stopTrackingButton = new Button("Stop tracking animal");
        stopTrackingButton.setOnAction(event -> stopTracking());

        VBox statisticsHBox = new VBox(statisticsTextArea,statisticsPerAnimalTextArea,stopTrackingButton);

        statisticsPerAnimalTextArea.setVisible(false);
        stopTrackingButton.setVisible(false);

        mapPane.setCenter(mapPanel);
        mapPane.setRight(statisticsHBox);

        HBox buttonsHBox = new HBox(stopButton, popularGenButton);
        mapPane.setBottom(buttonsHBox);

        return new Scene(mapPane, 1200, 750);
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
            if(simulationTimeline.getStatus() == Timeline.Status.RUNNING) {
                simulationTimeline.pause();
                isSimulationRunning = false;
                stopButton.setText("Resume");
            } else {
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

    private void stopTracking(){
        mapPanel.setAnimalColor(mapPanel.getChosenAnimal(), Color.web("#999900"));
        mapPanel.setChosenAnimal(null);
        statisticsPerAnimalTextArea.setVisible(false);
        stopTrackingButton.setVisible(false);
    }

    private void showPopularGen(){
        if(simulationTimeline != null){
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
        map.createMap(numberOfAnimals, numberOfPlants, startingEnergy, energyOfPlant, genomeSize);
        Statistics statistics = new Statistics(map);
        currentDay = 0;

        simulationTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if(currentDay < numberOfDays) {
                if(currentDay == 0) {
                    mapPanel.setMap(map);
                    statisticsTextArea.setText("Day " + currentDay + ":\n" + statistics.show() + "\n\n");
                    currentDay++;
                } else {
                    currentDay++;
                    simulation.run(minMutatedGenes, maxMutatedGenes, energyOfPlant, energyNeededForCopulation, energyUsedForCopulation, numberOfPlantsPerDay);
                    mapPanel.setMap(map);
                    statisticsTextArea.setText("Day " + currentDay + ":\n" + statistics.show() + "\n\n");
                    Animal chosenAnimal = mapPanel.getChosenAnimal();
                    if(chosenAnimal != null) {
                        statisticsPerAnimalTextArea.setVisible(true);
                        stopTrackingButton.setVisible(true);
                        statisticsPerAnimalTextArea.setText(chosenAnimal.showStats());
                        if(chosenAnimal.isDead()) {
                            if(chosenAnimal.getDayOfDeath() == -1) {
                                chosenAnimal.setDayOfDeath(currentDay);
                            }
                            statisticsPerAnimalTextArea.setText(chosenAnimal.showStats() + "\n" + "Day of death: " + chosenAnimal.getDayOfDeath());
                        }
                    }
                }
            } else { simulationTimeline.stop(); }
        }));
        simulationTimeline.setCycleCount(Timeline.INDEFINITE);
        simulationTimeline.play();
    }


    private Slider createSlider(double min, double max, double initialValue, int tickUnit) {
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
