package agh.ics.oop.presenters;

import agh.ics.oop.Simulation;
import agh.ics.oop.Statistics;
import agh.ics.oop.model.GameMap;
import agh.ics.oop.model.MutationType;
import agh.ics.oop.model.PlantsType;
import agh.ics.oop.model.Vector2d;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulationGUI extends JFrame {
    private JTextField numberOfAnimalsField;
    private JTextField numberOfPlantsField;
    private JTextField numberOfDaysField;
    private JTextField minMutatedGenesField;
    private JTextField maxMutatedGenesField;
    private JTextField energyOfPlantField;
    private JTextField genomeSizeField;
    private JTextField energyNeededForCopulationField;
    private JTextField energyUsedForCopulationField;
    private JTextField numberOfPlantsPerDayField;
    private JTextField mapWidthField;
    private JTextField mapHeightField;
    private JTextArea mapTextArea;
    private JTextArea statisticsTextArea;
    private int currentDay;
    private Simulation simulation;

    public SimulationGUI() {
        super("Simulation GUI");

        numberOfAnimalsField = new JTextField(10);
        numberOfPlantsField = new JTextField(10);
        numberOfDaysField = new JTextField(10);
        minMutatedGenesField = new JTextField(10);
        maxMutatedGenesField = new JTextField(10);
        energyOfPlantField = new JTextField(10);
        genomeSizeField = new JTextField(10);
        energyNeededForCopulationField = new JTextField(10);
        energyUsedForCopulationField = new JTextField(10);
        numberOfPlantsPerDayField = new JTextField(10);
        mapWidthField = new JTextField(10);
        mapHeightField = new JTextField(10);

        mapTextArea = new JTextArea(30, 40);
        mapTextArea.setEditable(false);

        statisticsTextArea = new JTextArea(15, 30);
        statisticsTextArea.setEditable(false);

        JButton startButton = new JButton("Start");

        setLayout(new FlowLayout());

        add(new JLabel("Map width:"));
        add(mapWidthField);

        add(new JLabel("Map height:"));
        add(mapHeightField);

        add(new JLabel("Number of animals:"));
        add(numberOfAnimalsField);

        add(new JLabel("Number of plants:"));
        add(numberOfPlantsField);

        add(new JLabel("Number of days:"));
        add(numberOfDaysField);

        add(new JLabel("Min mutated genes:"));
        add(minMutatedGenesField);

        add(new JLabel("Max mutated genes:"));
        add(maxMutatedGenesField);

        add(new JLabel("Energy of plant:"));
        add(energyOfPlantField);

        add(new JLabel("Genome size:"));
        add(genomeSizeField);

        add(new JLabel("Energy needed for copulation:"));
        add(energyNeededForCopulationField);

        add(new JLabel("Energy used for copulation:"));
        add(energyUsedForCopulationField);

        add(new JLabel("Number of plants per day:"));
        add(numberOfPlantsPerDayField);


        add(startButton);

        add(new JScrollPane(mapTextArea));
        add(new JScrollPane(statisticsTextArea));



        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startSimulation();
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setVisible(true);

    }
private void startSimulation() {
        int numberOfAnimals = Integer.parseInt(numberOfAnimalsField.getText());
        int numberOfPlants = Integer.parseInt(numberOfPlantsField.getText());
        int numberOfDays = Integer.parseInt(numberOfDaysField.getText());
        int minMutatedGenes = Integer.parseInt(minMutatedGenesField.getText());
        int maxMutatedGenes = Integer.parseInt(maxMutatedGenesField.getText());
        int energyOfPlant = Integer.parseInt(energyOfPlantField.getText());
        int genomeSize = Integer.parseInt(genomeSizeField.getText());
        int energyNeededForCopulation = Integer.parseInt(energyNeededForCopulationField.getText());
        int energyUsedForCopulation = Integer.parseInt(energyUsedForCopulationField.getText());
        int numberOfPlantsPerDay = Integer.parseInt(numberOfPlantsPerDayField.getText());
        int mapWidth = Integer.parseInt(mapWidthField.getText());
        int mapHeight = Integer.parseInt(mapHeightField.getText());

        runSimulation(numberOfAnimals, numberOfPlants, numberOfDays,
                minMutatedGenes, maxMutatedGenes, energyOfPlant,
                genomeSize, energyNeededForCopulation, energyUsedForCopulation,
                numberOfPlantsPerDay, mapWidth, mapHeight);
    }

    private void runSimulation(int numberOfAnimals, int numberOfPlants, int numberOfDays,
                               int minMutatedGenes, int maxMutatedGenes, int energyOfPlant,
                               int genomeSize, int energyNeededForCopulation, int energyUsedForCopulation,
                               int numberOfPlantsPerDay, int mapWidth, int mapHeight) {
        Vector2d lowerLeft = new Vector2d(0, 0);
        Vector2d upperRight = new Vector2d(mapWidth, mapHeight);

        GameMap map = new GameMap(lowerLeft, upperRight, MutationType.NORMALMUTATION, PlantsType.POISONOUSPLANTS);

        simulation = new Simulation(map);

        map.createMap(numberOfAnimals,numberOfPlants,energyOfPlant,genomeSize);

        Statistics statistics = new Statistics(map);


        currentDay = 0;

        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentDay < numberOfDays) {
                    currentDay++;
                    simulation.run(minMutatedGenes, maxMutatedGenes, energyOfPlant,
                            energyNeededForCopulation, energyUsedForCopulation,
                            numberOfPlantsPerDay);
                    mapTextArea.setText("Day " + currentDay + ":\n" + simulation.getMap().toString() + "\n\n");
                    statisticsTextArea.setText("Day " + currentDay + ":\n" + statistics.show() + "\n\n");
                } else {
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        timer.start();

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SimulationGUI();
            }
        });
    }




}
