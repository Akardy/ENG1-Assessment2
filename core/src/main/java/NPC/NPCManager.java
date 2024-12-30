package NPC;

import com.badlogic.UniSim2.mapmanager.Grid;
import com.badlogic.UniSim2.resources.Consts;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;


public class NPCManager {

    private static Array<NPC> NPCList;
    private static Grid grid;
    private final SpriteBatch batch;

    private static float scaleX;
    private static float scaleY;

    public NPCManager(float scaleX, float scaleY){
        grid = new Grid();
        NPCList = new Array<>();
        batch = new SpriteBatch();
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        createPath();
    }
    private void createPath(){
        // horizontal paths
        grid.markPathRange(27, 10, 27, 100);
        grid.markPathRange(15, 10, 15, 100);

        //vertical paths
        grid.markPathRange(1, 10, 100, 10);
        grid.markPathRange(1, 27, 100, 27);
        grid.markPathRange(1, 47, 27, 47);
        grid.markPathRange(1, 61, 100, 61);


    }
    public void update(float delta) {
        for (NPC npc : NPCList) {
            npc.update(delta); // Update each NPC
            batch.begin();
            // draw and adjust NPC size
            batch.draw(npc.getFrame(delta), npc.getPosition().x - (Consts.CELL_SIZE * scaleX / 2), npc.getPosition().y - (Consts.CELL_SIZE * scaleX / 2), 0, 0, npc.getWidth(), npc.getHeight(), scaleX, scaleY, 0);
            batch.end();
        }
    }


    // ADD NPCs with a 0.3s cool down - using threads so doesn't steal all system resources
    public static void addNPC(int count) {
        new Thread(() -> {
            for (int i = 0; i < count; i++) {
                NPC npc = new NPC(grid, scaleX, scaleY);
                NPCList.add(npc); // Add the NPC to the list
                try {
                    Thread.sleep(300); // 0.3s cool down
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
