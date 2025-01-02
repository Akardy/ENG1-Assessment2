package NPC;

import com.badlogic.UniSim2.resources.Assets;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import static com.badlogic.UniSim2.resources.Consts.*;
import com.badlogic.UniSim2.mapmanager.Grid;

import java.util.Random;

public class NPC {

    public enum State {RUN_UP, RUN_DOWN, RUN_LEFT, RUN_RIGHT}

    private State currentState;
    private State previousState;

    private Vector2 startPosition;
    private Vector2 targetPosition;
    private Vector2 currentPosition;
    private Vector2 direction;

    private Grid grid;
    private boolean isMoving;

    private Animation<TextureRegion> runUp;
    private Animation<TextureRegion> runDown;
    private Animation<TextureRegion> runLeft;
    private Animation<TextureRegion> runRight;
    private TextureRegion standUp;


    private float stateTimer;

    Random random = new Random();

    private float scaleX;
    private float scaleY;


    public NPC(Grid grid, float scaleX, float scaleY) {
        this.grid = grid;
        this.scaleX = scaleX;
        this.scaleY = scaleY;



        // first direction is randomly either right or left
        int randomDirection = random.nextBoolean() ? -1 : 1;


        direction = new Vector2(randomDirection, 0); // Starts moving randomly left/right
        currentState = getState();
        previousState = getState();

        // Define animations and standing frames
        TextureRegion texture = Assets.NPCTexture;
        defineAnimations(texture);


        // Initialize position
        startPosition = new Vector2(40, 27);
        targetPosition = new Vector2(startPosition.x + randomDirection, startPosition.y);
        currentPosition = new Vector2(startPosition.x * CELL_SIZE * scaleX, startPosition.y * CELL_SIZE * scaleY);


        isMoving = true;
        stateTimer = 0;
    }


    private void defineAnimations(TextureRegion texture) {
        Array<TextureRegion> frames = new Array<>();
        // Define running animations and standing textures
        frames.add(new TextureRegion(texture, 17, 4, 14, 18));
        frames.add(new TextureRegion(texture, 33, 4, 14, 18));
        runDown = new Animation<>(0.1f, frames);
        frames.clear();
        frames.add(new TextureRegion(texture, 17, 28, 14, 18));
        frames.add(new TextureRegion(texture, 33, 28, 14, 18));
        runUp = new Animation<>(0.1f, frames);
        frames.clear();
        frames.add(new TextureRegion(texture, 17, 52, 14, 18));
        frames.add(new TextureRegion(texture, 33, 52, 14, 18));
        runLeft = new Animation<>(0.1f, frames);
        frames.clear();
        frames.add(new TextureRegion(texture, 17, 52, 14, 18));
        frames.peek().flip(true, false);
        frames.add(new TextureRegion(texture, 33, 52, 14, 18));
        frames.peek().flip(true, false);
        runRight = new Animation<>(0.1f, frames);

        standUp = new TextureRegion(texture, 1, 28, 14, 18);
    }

    public void update() {
        if (isMoving) {
            moveTowardsTarget();
        } else {
            pickNewDirection();
        }

    }


    private void moveTowardsTarget() {
        Vector2 movement = direction.cpy().scl(NPCSpeed * CELL_SIZE * scaleX * scaleY);
        currentPosition.add(movement);

        // Check if the target position has been reached
        Vector2 targetCoordinate = new Vector2(targetPosition.x * CELL_SIZE * scaleX, targetPosition.y * CELL_SIZE * scaleY);
        if (currentPosition.dst(targetCoordinate) <= NPCSpeed * CELL_SIZE) {
            currentPosition.set(targetCoordinate);
            isMoving = false;
        }
    }

    private void pickNewDirection() {

        Array<Vector2> possibleDirections = new Array<>();

        // Check all possible directions - check if it's a path and if it's not where the NPC came from
        Vector2[] directions = {new Vector2(1, 0), new Vector2(-1, 0), new Vector2(0, 1), new Vector2(0, -1)};
        for (Vector2 dir : directions) {


            Vector2 nextCell = new Vector2(targetPosition.x + dir.x, targetPosition.y + dir.y);
            if (grid.isPathCell(nextCell) && !nextCell.equals(startPosition)) {
                possibleDirections.add(dir);}


        }
        // choose a possible path at random
        if (possibleDirections.size > 0) {
            direction = possibleDirections.random();
            startPosition.set(targetPosition);
            targetPosition.set(startPosition.x + direction.x, startPosition.y + direction.y);
            isMoving = true;
        }
        // if NPC is at the end of the path and no connecting paths
        else{

            Vector2 tempVector = new Vector2(currentPosition.x, currentPosition.y);
            startPosition.x = currentPosition.x / (CELL_SIZE * scaleX);
            startPosition.y = currentPosition.y / (CELL_SIZE * scaleY);
            targetPosition.set(tempVector.x / (CELL_SIZE * scaleX), tempVector.y / (CELL_SIZE * scaleY));
        }



    }

    public TextureRegion getFrame(float delta) {
        // get current frame - image state
        currentState = getState();
        TextureRegion region;

        switch (currentState) {
            case RUN_RIGHT:
                region = runRight.getKeyFrame(stateTimer, true);
                break;
            case RUN_LEFT:
                region = runLeft.getKeyFrame(stateTimer, true);
                break;
            case RUN_UP:
                region = runUp.getKeyFrame(stateTimer, true);
                break;
            default:
                region = runDown.getKeyFrame(stateTimer, true);
                break;

        }

        stateTimer = (currentState == previousState) ? stateTimer + delta : 0;
        previousState = currentState;
        return region;
    }

    private State getState() {
        if (direction.y > 0) {
            return State.RUN_UP;
        } else if (direction.y < 0) {
            return State.RUN_DOWN;
        } else if (direction.x > 0) {
            return State.RUN_RIGHT;
        } else {
            return State.RUN_LEFT;

        }
    }

    public Vector2 getPosition() {
        return currentPosition;
    }
    // returns half the size of the character, so it is displayed correctly
    public float getWidth(){ return 14f;}
    public float getHeight(){return 18;}

}
