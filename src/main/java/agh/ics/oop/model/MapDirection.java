package agh.ics.oop.model;

public enum MapDirection {
    NORTH,
    NORTH_EAST,
    EAST,
    SOUTH_EAST,
    SOUTH,
    SOUTH_WEST,
    WEST,
    NORTH_WEST;

    public String toString(){
        switch(this){
            case EAST -> {
                return "Wschód";
            }
            case WEST -> {
                return "Zachód";
            }
            case NORTH -> {
                return "Północ";
            }
            case SOUTH -> {
                return "Południe";
            }
            case NORTH_EAST -> {
                return "Północny wschód";
            }
            case NORTH_WEST -> {
                return "Północny zachód";
            }
            case SOUTH_EAST -> {
                return "Południowy wschód";
            }
            case SOUTH_WEST -> {
                return "Południowy zachód";
            }
            default -> throw new IllegalArgumentException();
        }
    }


    public Vector2d toUnitVector(){
        switch(this){
            case WEST -> {
                int x = -1;
                int y = 0;
                return new Vector2d(x,y);
            }
            case NORTH_WEST -> {
                int x = -1;
                int y = 1;
                return new Vector2d(x,y);
            }
            case NORTH -> {
                int x = 0;
                int y = 1;
                return new Vector2d(x,y);
            }
            case NORTH_EAST -> {
                int x = 1;
                int y = 1;
                return new Vector2d(x,y);
            }
            case EAST -> {
                int x = 1;
                int y = 0;
                return new Vector2d(x,y);
            }
            case SOUTH_EAST -> {
                int x = 1;
                int y = -1;
                return new Vector2d(x,y);
            }
            case SOUTH -> {
                int x = 0;
                int y = -1;
                return new Vector2d(x,y);
            }
            case SOUTH_WEST -> {
                int x = -1;
                int y = -1;
                return new Vector2d(x,y);
            }
            default -> throw new IllegalArgumentException();
        }
    }

}
