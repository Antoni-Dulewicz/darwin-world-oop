package agh.ics.oop.presenters;

import agh.ics.oop.Simulation;
import agh.ics.oop.Statistics.Statistics;
import agh.ics.oop.model.GameMap;
import agh.ics.oop.model.MutationType;
import agh.ics.oop.model.PlantsType;
import agh.ics.oop.model.Vector2d;


import agh.ics.oop.model.elements.Animal;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.control.Slider;

import java.util.List;


public class SimulationGUI extends Application {
    private Slider numberOfAnimalsField;
    private Slider numberOfPlantsField;
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
    private int currentDay;
    private Simulation simulation;
    private boolean isSimulationRunning = false;
    private Button stopButton;
    private MapPanel mapPanel;
    private ComboBox<MutationType> mutationTypeComboBox;
    private ComboBox<PlantsType> plantsTypeComboBox;
    private Timeline simulationTimeline;
    private Stage mapStage;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Simulation GUI");

        numberOfAnimalsField = createSlider(0, 1000, 500, 100);
        numberOfPlantsField = createSlider(0, 1000, 300, 100);
        numberOfDaysField = createSlider(0, 1000, 100, 100);
        minMutatedGenesField = createSlider(0, 10, 1, 1);
        maxMutatedGenesField = createSlider(0, 10, 3, 1);
        energyOfPlantField = createSlider(0, 30, 5, 1);
        genomeSizeField = createSlider(0, 10, 5, 1);
        energyNeededForCopulationField = createSlider(0, 20, 8, 1);
        energyUsedForCopulationField = createSlider(0, 20, 5, 1);
        numberOfPlantsPerDayField = createSlider(0, 500, 10, 10);
        mapWidthField = createSlider(3, 100, 10, 10);
        mapHeightField = createSlider(3, 100, 10, 10);
        mutationTypeComboBox = new ComboBox<>();
        plantsTypeComboBox = new ComboBox<>();

        statisticsTextArea = new TextArea();
        statisticsTextArea.setEditable(false);

        Button startButton = new Button("Start");



        GridPane inputGrid = new GridPane();
        inputGrid.setHgap(10);
        inputGrid.setVgap(10);
        inputGrid.setPadding(new Insets(10, 10, 10, 10));

        addLabelAndField(inputGrid, 0, "Map width:", mapWidthField);
        addLabelAndField(inputGrid, 1, "Map height:", mapHeightField);
        addLabelAndField(inputGrid, 2, "Number of animals:", numberOfAnimalsField);
        addLabelAndField(inputGrid, 3, "Number of plants:", numberOfPlantsField);
        addLabelAndField(inputGrid, 4, "Number of days:", numberOfDaysField);
        addLabelAndField(inputGrid, 5, "Min mutated genes:", minMutatedGenesField);
        addLabelAndField(inputGrid, 6, "Max mutated genes:", maxMutatedGenesField);
        addLabelAndField(inputGrid, 7, "Energy of plant:", energyOfPlantField);
        addLabelAndField(inputGrid, 8, "Genome size:", genomeSizeField);
        addLabelAndField(inputGrid, 9, "Energy needed for copulation:", energyNeededForCopulationField);
        addLabelAndField(inputGrid, 10, "Energy used for copulation:", energyUsedForCopulationField);
        addLabelAndField(inputGrid, 11, "Number of plants per day:", numberOfPlantsPerDayField);
        addLabelAndField(inputGrid, 12, "Mutation type:", mutationTypeComboBox);
        addLabelAndField(inputGrid, 13, "Plants type:", plantsTypeComboBox);

        mutationTypeComboBox.getItems().addAll(MutationType.values());
        plantsTypeComboBox.getItems().addAll(PlantsType.values());

        inputGrid.add(startButton, 0, 14, 2, 1);

        BorderPane mainPane = new BorderPane();
        mainPane.setTop(inputGrid);

        HBox centerPane = new HBox();
        centerPane.setVisible(false);
        centerPane.setSpacing(10);


        Scene scene = new Scene(mainPane, 1000, 700);

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

    private void addLabelAndField(GridPane grid, int row, String labelText, Control control) {
        grid.add(new Label(labelText), 0, row);
        grid.add(control, 1, row);
    }

    private Scene createMapScene(){
        BorderPane mapPane = new BorderPane();
        mapPane.setPadding(new Insets(10, 10, 10, 10));

        mapPanel = new MapPanel();
        mapPanel.setMinSize(400, 400);
        mapPanel.setMaxSize(400, 400);

        VBox mapVBox = new VBox(mapPanel);
        ScrollPane statisticsScrollPane = new ScrollPane(statisticsTextArea);

        mapPane.setCenter(mapVBox);
        mapPane.setRight(statisticsScrollPane);

        stopButton = new Button("Stop");
        stopButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pauseSimulation();
            }
        });

        mapPane.setBottom(stopButton);

        Scene mapScene = new Scene(mapPane, 800, 600);

        return mapScene;
    }

    private void startSimulation() {
        int numberOfAnimals = (int) numberOfAnimalsField.getValue();
        int numberOfPlants = (int) numberOfPlantsField.getValue();
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

        runSimulation(numberOfAnimals, numberOfPlants, numberOfDays,
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
        if(mapPanel != null){
            mapPanel.setMap(null);
        }
        currentDay = 0;
        if (stopButton != null) {
            stopButton.setText("Stop");
        }
    }

    private void runSimulation(int numberOfAnimals, int numberOfPlants, int numberOfDays,
                               int minMutatedGenes, int maxMutatedGenes, int energyOfPlant,
                               int genomeSize, int energyNeededForCopulation, int energyUsedForCopulation,
                               int numberOfPlantsPerDay, int mapWidth, int mapHeight, MutationType mutationType, PlantsType plantsType){
        Vector2d lowerLeft = new Vector2d(0, 0);
        Vector2d upperRight = new Vector2d(mapWidth, mapHeight);

        GameMap map = new GameMap(lowerLeft, upperRight, mutationType, plantsType);

        simulation = new Simulation(map);

        map.createMap(numberOfAnimals,numberOfPlants,energyOfPlant,genomeSize);

        Statistics statistics = new Statistics(map);


        currentDay = 0;

        simulationTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (currentDay < numberOfDays) {
                currentDay++;
                simulation.run(minMutatedGenes, maxMutatedGenes, energyOfPlant,
                        energyNeededForCopulation, energyUsedForCopulation,
                        numberOfPlantsPerDay);

                mapPanel.setMap(map);

                statisticsTextArea.setText("Day " + currentDay + ":\n" + statistics.show() + "\n\n");
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

