import java.awt.*;
import java.util.LinkedList;

public class Snake {
    private LinkedList<Cell> snake;
    private int direction;
    private GameSnake gameSnake;

    public Snake(int x, int y, int length, int direction, GameSnake gameSnake) {
        snake = new LinkedList<>();
        snake.add(new Cell(x, y, gameSnake.CELL_SIZE, gameSnake.SNAKE_HEAD_COLOR));
        for (int i = 1; i < length; i++){
            snake.add(new Cell(x-i, y, gameSnake.CELL_SIZE, gameSnake.SNAKE_COLOR));
        }
        this.direction = direction;
        this.gameSnake = gameSnake;
    }

    public void paint (Graphics g) {
        for (Cell cell : snake){
            cell.paint(g);
        }
    }

    public void move(){
        int x = snake.getFirst().getX();
        int y = snake.getFirst().getY();

        switch (direction){
            case GameSnake.KEY_LEFT: x--;
                if (x < 0)
                    x = gameSnake.CANVAS_WIDTH ;
                break;
            case GameSnake.KEY_RIGHT: x++;
                if (x == gameSnake.CANVAS_WIDTH)
                    x = 0;
                break;
            case GameSnake.KEY_UP: y--;
                if(y < 0)
                    y = gameSnake.CANVAS_HEIGHT -1;
                break;
            case GameSnake.KEY_DOWN: y++;
                if (y == gameSnake.CANVAS_HEIGHT)
                    y = 0;
                break;
        }
        snake.getFirst().setColor(gameSnake.SNAKE_COLOR);

        snake.addFirst(new Cell(x, y, gameSnake.CELL_SIZE, gameSnake.SNAKE_HEAD_COLOR));
        if(gameSnake.food.isFood(x, y)) {
           gameSnake.food.eat();
           gameSnake.setTitle(gameSnake.TITLE_OF_PROGRAM + " : " + snake.size());
        }else{
            snake.removeLast();
        }

        if (isInSnake(x, y))
            gameSnake.gameOver = true;

        gameSnake.setReadyForNextTurn();

    }

    public void setDirection(int newDirection){
         if(newDirection >= GameSnake.KEY_LEFT && newDirection <= GameSnake.KEY_DOWN){
               if(Math.abs(newDirection-this.direction) != 2) {
                   this.direction = newDirection;
                   gameSnake.setWaitForNextTurn();

                   System.out.println("Direction was changed: "+newDirection);
               }else{
                   System.out.println("A command was ignored: " + newDirection);
               }
        }else{
             System.out.println("A command was ignored: " + newDirection);
         }
        System.out.println("Commands queue: "+gameSnake.commandsToString());
    }

    public boolean isInSnake (int x, int y){
        boolean isInSnake = false;
        for (Cell cell : snake){
            if (cell != snake.getFirst() && cell.getX()==x && cell.getY()==y)
                isInSnake = true;
        }
        return isInSnake;
    }
}
