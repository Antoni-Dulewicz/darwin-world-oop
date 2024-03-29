package agh.ics.oop.model;

import java.util.Random;

public class PositionGenerator {
    private final Vector2d lowerLeft;
    private final Vector2d upperRight;

    private final int width;
    private final int height;

    public PositionGenerator(Vector2d lowerLeft, Vector2d upperRight) {
        this.lowerLeft = lowerLeft;
        this.upperRight = upperRight;

        this.width = upperRight.x() - lowerLeft.x();
        this.height = upperRight.y() - lowerLeft.y();
    }

    public Vector2d randomPosition(){
        Random generator = new Random();
        int x = generator.nextInt(this.width + 1) + lowerLeft.x();
        int y = generator.nextInt(this.height + 1) + lowerLeft.y();

        return new Vector2d(x,y);
    }

    public Vector2d randomPositionInMiddle(){
        Random generator = new Random();
        int middleY = (this.height)/2 + lowerLeft.y();

        int yRange = this.height / 10;
        if (yRange == 0) {
            yRange = 1;
        }

        int x = generator.nextInt(this.width+1) + lowerLeft.x();
        int y = generator.nextInt(2*yRange+1) + middleY - yRange;

        return new Vector2d(x,y);
    }

    public Vector2d randomPositionOutsideMiddle(){
        Random generator = new Random();
        int x = generator.nextInt(this.width+1) + lowerLeft.x();

        int middleY = this.height / 2 + lowerLeft.y();

        int yRange = this.height / 10;
        if (yRange == 0) {
            yRange = 1;
        }
        if(middleY-yRange < 0){
            middleY = yRange;
        }
        int y = 1;
        if(middleY!=yRange){
            int y1 = generator.nextInt((middleY - yRange)) + lowerLeft.y();
            int y2 = generator.nextInt(middleY-yRange+1) + middleY + yRange; // +1 because nextInt is exclusive

            y = generator.nextInt(2) == 0 ? y1 : y2;
        }


        return new Vector2d(x,y);
    }

}
