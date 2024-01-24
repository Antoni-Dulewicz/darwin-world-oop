package agh.ics.oop.model;

import agh.ics.oop.model.elements.*;
import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;

public abstract class AbstractWorldMap implements WorldMap{
    private final Vector2d lowerLeft;
    private final Vector2d upperRight;
    private List<Animal> animals;
    private List<Animal> deadAnimals;
    private List<Animal> allDeadAnimals;
    private List<Plant> plants;
    private HashMap<Vector2d, Square> mapSquares;

    private final MutationType mutationType;

    private final PlantsType plantsType;

    private final Rectangle poisonRectangle;

    private final PositionGenerator positionGenerator;

    public AbstractWorldMap(Vector2d lowerLeft, Vector2d upperRight,MutationType mutationType,PlantsType plantsType) {
        this.lowerLeft = lowerLeft;
        this.upperRight = upperRight;
        this.animals = new ArrayList<>();
        this.deadAnimals = new ArrayList<>();
        this.allDeadAnimals = new ArrayList<>();
        this.plants = new ArrayList<>();
        this.mapSquares = new HashMap<>();
        this.mutationType = mutationType;
        this.plantsType = plantsType;
        this.poisonRectangle = createPoisonMap();
        this.positionGenerator = new PositionGenerator(lowerLeft,upperRight);
    }

    @Override
    public boolean place(WorldElement element, Vector2d position) {
        if (!this.canMoveTo(position)) {
            throw new IllegalArgumentException(position + " is out of map");
        }

        //put element on given position
        Square square = new Square(position,element);
        List<Animal> newAnimals = square.getAnimals();
        Plant newPlant = square.getPlant();
        if(!this.isOccupied(position)) {
            this.mapSquares.put(position, square);

        }else{
            if (newAnimals != null) {
                for (Animal animal : newAnimals) {
                    this.mapSquares.get(position).addAnimal(animal);
                }
            }
            if (newPlant != null) {
                Plant oldPlant = this.mapSquares.get(position).getPlant();
                plants.remove(oldPlant);
                this.mapSquares.get(position).setPlant(newPlant);
            }
        }

        if (newAnimals != null) {
            this.animals.addAll(newAnimals);
        }
        if (newPlant != null) {
            this.plants.add(newPlant);

        }



        return true;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        int x = position.x();
        int y = position.y();
        return (y >= this.lowerLeft.y() && y <= this.upperRight.y()) && (x >= this.lowerLeft.x() && x <= this.upperRight.x());


    }

    @Override
    public Object objectAt(Vector2d position) {

        return mapSquares.get(position);
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return mapSquares.containsKey(position);
    }

    @Override
    public Vector2d getLowerLeft() {
        return this.lowerLeft;
    }
    @Override
    public Vector2d getUpperRight() {
        return this.upperRight;
    }

    @Override
    public List<WorldElement> getElements() {
        List<WorldElement> elements = new ArrayList<>();

        for(int i = 0; i < this.mapSquares.size(); i++){
            WorldElement newElement = this.mapSquares.get(i).ObjectAt();
            elements.add(newElement);
        }

        return  elements;
    }

    @Override
    public int getHeigth() {
        return this.upperRight.y() - this.lowerLeft.y() + 1;
    }

    @Override
    public int getWidth() {
        return this.upperRight.x() - this.lowerLeft.x() + 1;
    }

    @Override
    public Collection<Square> getAllSquares() {
        return mapSquares.values();
    }

    public List<Animal> getAllDeadAnimals(){
        return this.allDeadAnimals;
    }

    public Plant getPlant(Vector2d position){
        Square square = this.mapSquares.get(position);
        if(square != null){
            return square.getPlant();
        }
        return null;
    }

    public List<Animal> getAnimals(Vector2d position){
        Square square = this.mapSquares.get(position);
        if(square != null){
            return square.getAnimals();
        }
        return null;
    }


    public String drawMap(){
        MapVisualizer visualizer = new MapVisualizer(this);
        return visualizer.draw(this.lowerLeft,this.upperRight);
    }
    public void createAnimals(int numberOfAnimals, int genomeSize, int startingEnergy){
        Random generator = new Random();

        for(int i = 0; i < numberOfAnimals; i++){
            Vector2d position = this.positionGenerator.randomPosition();
            int energy = startingEnergy;
            Genotype genotype = new Genotype(genomeSize);
            int age = 0;
            Animal newAnimal = new Animal(position,energy,genotype,age);
            WorldElement newElement = new WorldElement();
            newElement.addAnimal(newAnimal);
            this.place(newElement,newAnimal.getPosition());
        }
    }
    public void createMap(int numberOfAnimals,int numberOfPlants,int startingEnergy, int energyOfPlant, int genomeSize){
        createAnimals(numberOfAnimals,genomeSize,startingEnergy);

        growPlants(numberOfPlants,energyOfPlant);
    }

    public void moveOneAnimal(Animal animal){
        int energy = animal.getEnergy();





        if(energy <= 0){  //jesli energia zwierzecia jest mnijesza lub rowna 0 to dodajemy do listy martwych zwierzat
            deadAnimals.add(animal);
            Square oldSquare = this.mapSquares.get(animal.getPosition());
            if(oldSquare != null){  //i usuwamy je z oldSquare jesli nie jest nullem
                oldSquare.removeAnimal(animal);
                if(oldSquare.isEmpty()){  //jesli po usunieciu zwierzecia oldSquare jest puste to usuwamy je z mapy
                    this.mapSquares.remove(animal.getPosition());
                }
            }
            return;
        }

        //jesli energia jest wieksza od 0 to:

        Square oldSquare = this.mapSquares.get(animal.getPosition());
        Vector2d oldPosition = animal.getPosition();
        animal.move(this);  //ruszamy zwierze ze wczesniejszym zapamiętaniem starej pozycji
        Vector2d newPosition = animal.getPosition();


        if(oldSquare != null){   // jesli stara pozycja nie jest nullem to usuwamy zwierze z oldSquare
            oldSquare.removeAnimal(animal);
        }

        Square newSquare = this.mapSquares.get(newPosition); //pobieramy newSquare

        if(newSquare == null){   //jesli jeszcze nie istnieje to
            WorldElement element = new WorldElement(); // tworzymy nowy element
            element.addAnimal(animal); // dodajemy do niego zwierze
            newSquare = new Square(newPosition,element);
            this.mapSquares.put(newPosition,newSquare); //dodajemy do mapy newSquare ktore zawiera nowy element ktory posiada nowe zwierze
            if(oldSquare != null){
                if(oldSquare.isEmpty()){
                    this.mapSquares.remove(oldPosition);
                }
            }
            return;
        }else{  //jesli newSquare juz istnieje to
            Plant currPlant = newSquare.getPlant();
            if(currPlant != null && currPlant.isPoisonous()){  //jesli na newSquare jest roslina i jest trujaca to
                Random generator = new Random();
                double probability = generator.nextDouble(); //zwierze wykonuje test na spostrzegawczość
                if(probability <= 0.2){ //jesli udalo mu sie zauwazyc to ze roslina jest trujaca to
                    animal.setEnergy(animal.getEnergy() + 1); //stara energia
                    animal.setPosition(oldPosition); //zwierze wraca na swoja stara pozycje
                    oldSquare.addAnimal(animal); //dodajemy zwierze do oldSquare

                    // to samo co wyzej drugi raz bo zwierze dostaje drugą szansę na ruch
                    animal.move(this);
                    Vector2d newPosition2 = animal.getPosition();

                    if(oldSquare != null){
                        oldSquare.removeAnimal(animal);
                        if(oldSquare.isEmpty()){
                            this.mapSquares.remove(oldPosition);
                        }
                    }

                    Square newSquare2 = this.mapSquares.get(newPosition2);

                    if(newSquare2 == null){
                        WorldElement element = new WorldElement();
                        element.addAnimal(animal);
                        newSquare2 = new Square(newPosition,element);
                        this.mapSquares.put(newPosition,newSquare2);
                        return;
                    }else{ //jesli newSquare2 juz istnieje to dodajemy do niego zwierze
                        newSquare2.addAnimal(animal);
                    }
                }else{ //jesli nie udalo mu sie zauwazyc to
                    newSquare.addAnimal(animal);
                    if(oldSquare != null){
                        if(oldSquare.isEmpty()){
                            this.mapSquares.remove(oldPosition);
                        }
                    }
                }
            }else{  //jesli na newSquare nie ma rosliny lub ma ale nie jest trujaca to dodajemy do niego zwierze
                newSquare.addAnimal(animal);
                if(oldSquare != null){
                    if(oldSquare.isEmpty()){
                        this.mapSquares.remove(oldPosition);
                    }
                }
            }
        }



    }

    public void changeAnimalsAge(){
        for(Square square : getAllSquares()){
            if(square != null && square.getElement().hasAnimals()){
                List<Animal> currAnimals = square.getAnimals();
                for(Animal animal : currAnimals){
                    animal.setAge(animal.getAge() + 1);
                }
            }
        }
    }


    public void moveAllAnimals(){
        for(Animal animal : this.animals){
            this.moveOneAnimal(animal);
        }
        changeAnimalsAge();
    }

    public void removeDeadAnimals(){
        this.allDeadAnimals.addAll(this.deadAnimals);
        for(Animal animal : this.deadAnimals){
            this.animals.remove(animal);
            Vector2d position = animal.getPosition();
            Square square = this.mapSquares.get(position);
            if(square != null){
                square.removeAnimal(animal);
                if(square.isEmpty()){
                    this.mapSquares.remove(position);
                }
            }

        }
        this.deadAnimals.clear();
    }
    public void eatNormalPlant(PriorityQueue currAnimals,int plantEnergy,Plant currPlant,Square square){
        Animal strongestAnimal = (Animal) currAnimals.poll();
        int animalEnergy = strongestAnimal.getEnergy();
        int newAnimalEnergy = animalEnergy + plantEnergy; //dodajemy energie
        strongestAnimal.setEnergy(newAnimalEnergy);
        strongestAnimal.setPlantsEaten(strongestAnimal.getPlantsEaten() + 1);
        square.setPlant(null);
        this.plants.remove(currPlant);
    }

    public void eatPoisonousPlant(PriorityQueue currAnimals,int plantEnergy,Plant currPlant,Square square){
        Animal strongestAnimal = (Animal) currAnimals.poll();
        int animalEnergy = strongestAnimal.getEnergy();
        int newAnimalEnergy = animalEnergy - plantEnergy; //odejmujemy energie
        strongestAnimal.setEnergy(newAnimalEnergy);
        strongestAnimal.setPlantsEaten(strongestAnimal.getPlantsEaten() + 1);
        square.setPlant(null);
        this.plants.remove(currPlant);
        if(newAnimalEnergy <= 0){
            this.deadAnimals.add(strongestAnimal);
        }
    }

    public void eatPlants(){
        for (Square square : getAllSquares()) {

            if(square.getElement().hasPlant()){
                Plant currPlant = square.getPlant();
                PriorityQueue<Animal> currAnimals = new PriorityQueue<>(square.getElement().getAnimalsAsQueue());

                int plantEnergy = currPlant.energy();
                if(currAnimals.isEmpty()){
                    continue;
                } else{
                    if(!currPlant.isPoisonous()){
                        eatNormalPlant(currAnimals,plantEnergy,currPlant,square);
                    }else{
                        eatPoisonousPlant(currAnimals,plantEnergy,currPlant,square);
                    }

                }

            }
        }

    }

    public Animal copulate(Animal firstAnimal,Animal secondAnimal,int energyUsed,int numberOfMutatedGenes){

        Random generator = new Random();
        int side = generator.nextInt(2); // 0 - left, 1 - right
        int genomSize = firstAnimal.getGenotype().getGenome().size();

        double tmp = ((double)firstAnimal.getEnergy()/(double) (firstAnimal.getEnergy()+ (double)secondAnimal.getEnergy()));
        int part = (int)(tmp*(double)genomSize);


        List<Integer> firstGenome = firstAnimal.getGenotype().getGenome();
        List<Integer> secondGenome = secondAnimal.getGenotype().getGenome();

        if(firstGenome.size() != secondGenome.size()){
            System.out.println("ERROR");
        }

        List<Integer> newGenome = new ArrayList<>();

        if (firstAnimal.getEnergy() > secondAnimal.getEnergy()) {
            if (side == 0) {
                for(int i = 0; i < part; i++){
                    newGenome.add(firstGenome.get(i));
                }
                for(int i = part; i < genomSize; i++){
                    newGenome.add(secondGenome.get(i));
                }
            } else {
                for(int i = 0; i < genomSize - part; i++){
                    newGenome.add(firstGenome.get(i));
                }
                for(int i = genomSize - part; i < genomSize; i++){
                    newGenome.add(secondGenome.get(i));
                }
            }
        } else {
            if (side == 0) {
                for (int i = 0; i < part; i++) {
                    newGenome.add(secondGenome.get(i));
                }
                for (int i = part; i < genomSize; i++) {
                    newGenome.add(firstGenome.get(i));
                }
            } else {
                for (int i = 0; i < genomSize - part; i++) {
                    newGenome.add(secondGenome.get(i));
                }
                for (int i = genomSize - part; i < genomSize; i++) {
                    newGenome.add(firstGenome.get(i));
                }
            }
        }


        firstAnimal.setEnergy(firstAnimal.getEnergy() - energyUsed);
        secondAnimal.setEnergy(secondAnimal.getEnergy() - energyUsed);

        firstAnimal.setChildrenCount(firstAnimal.getChildrenCount() + 1);
        secondAnimal.setChildrenCount(secondAnimal.getChildrenCount() + 1);

        Genotype newGenotype = new Genotype(newGenome);
        newGenotype.mutate(this.mutationType,numberOfMutatedGenes);
        int childDirection = generator.nextInt(8);

        Animal childAnimal = new Animal(childDirection,firstAnimal.getPosition(),energyUsed*2,newGenotype,0);
        return childAnimal;

    }
    public void allAnimalsCopulation(int energyNeededForCopulation,int energyUsedForCopulation,int minMutatedGenes,int maxMutatedGenes){
        for (Square square : getAllSquares()) {
            if(square != null && square.getElement().hasAnimals()){
                PriorityQueue<Animal> currAnimals = new PriorityQueue<>(square.getElement().getAnimalsAsQueue());
                List<Animal> childrenForSquare = new ArrayList<>();
                while(currAnimals.size() > 1){
                    Animal firstAnimal = currAnimals.poll();
                    Animal secondAnimal = currAnimals.poll();
                    if(firstAnimal.getEnergy() >= energyNeededForCopulation && secondAnimal.getEnergy() >= energyNeededForCopulation){
                        Random generator = new Random();
                        int numberOfMutatedGenes = generator.nextInt(maxMutatedGenes - minMutatedGenes + 1) + minMutatedGenes;
                        Animal child = copulate(firstAnimal,secondAnimal,energyUsedForCopulation,numberOfMutatedGenes);
                        square.addAnimal(child);
                        this.animals.add(child);
                    }
                }
            }
        }
    }

    public Rectangle createPoisonMap(){
        int startX = this.lowerLeft.x();
        int startY = this.lowerLeft.y();
        int endX = this.upperRight.x();
        int endY = this.upperRight.y();
        int sizeX = (int) ((endX - startX)*0.4);
        int sizeY = (int) ((endY - startY)*0.4);

        Random generator = new Random();

        int newStartX = generator.nextInt(endX - sizeX - startX) + startX;
        int newStartY = generator.nextInt(endY - sizeY - startY) + startY;

        int newEndX = newStartX + sizeX;
        int newEndY = newStartY + sizeY;

        Vector2d newLowerLeft = new Vector2d(newStartX,newStartY);
        Vector2d newUpperRight = new Vector2d(newEndX,newEndY);

        Rectangle poisonRectangle = new Rectangle(newLowerLeft,newUpperRight);

        return poisonRectangle;

    }

    public void growNormalPlant(Vector2d position,int energyOfPlant){
        Plant newPlant = new Plant(position,energyOfPlant,false);
        WorldElement newElement = new WorldElement();
        newElement.addPlant(newPlant);
        this.place(newElement,newPlant.position());
    }

    public void growPoisonousPlant(Vector2d position,int energyOfPlant){
        Plant newPlant = new Plant(position,energyOfPlant,true);
        WorldElement newElement = new WorldElement();
        newElement.addPlant(newPlant);
        this.place(newElement,newPlant.position());
    }

    public void growPlants(int numberOfPlants,int energyOfPlant){
        Random generator = new Random();
        for(int i = 0; i < numberOfPlants; i++){
            Vector2d position;
            double probability = generator.nextDouble();
            if(probability <= 0.8){
                position = this.positionGenerator.randomPositionInMiddle();

            }else{
                position = this.positionGenerator.randomPositionOutsideMiddle();
            }

            Square currSquare = this.mapSquares.get(position);

            if(currSquare != null && this.plants.size() < (getHeigth())*(getWidth())){
                if(currSquare.getElement().hasPlant()){
                    i--;
                    continue;
                }
            }


            if(this.plantsType == PlantsType.REGULARPLANTS){
                growNormalPlant(position,energyOfPlant);
            }else{
                if(this.poisonRectangle.contains(position)){
                    int x = generator.nextInt(2);
                    if(x == 0){
                        growPoisonousPlant(position,energyOfPlant);
                    }else{
                        growNormalPlant(position,energyOfPlant);
                    }
                }else{
                    growNormalPlant(position,energyOfPlant);
                }

            }
        }
        for(Square square: getAllSquares()){
            if(square != null && !square.getElement().hasAnimals() && !square.getElement().hasPlant()){
                this.mapSquares.remove(square);
            }
        }
    }

    public int getHighestEnergy(){
        int highestEnergy = 0;
        for(Square square : getAllSquares()){
            if(square != null && square.getElement().hasAnimals()){
                List<Animal> currAnimals = square.getAnimals();
                for(Animal animal : currAnimals){
                    if(animal.getEnergy() > highestEnergy){
                        highestEnergy = animal.getEnergy();
                    }
                }
            }
        }
        return highestEnergy;
    }
}
