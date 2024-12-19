package NPC;

import com.badlogic.UniSim2.mapmanager.Grid;
import com.badlogic.UniSim2.resources.Consts;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;


public class NPCManager {

    private static Array<NPC> NPCList;
    private static Grid grid;
    private final SpriteBatch batch;

    public NPCManager(){
        grid = new Grid();
        NPCList = new Array<>();
        batch = new SpriteBatch();
        createPath();
    }
    private void createPath(){
        // horizontal paths
        grid.markPathRange(37, 12, 37, 100);
        grid.markPathRange(20, 13, 20, 100);

        //vertical paths
        grid.markPathRange(0, 12, 52, 12);
        grid.markPathRange(0, 34, 52, 34);
        grid.markPathRange(0, 60, 37, 60);
        grid.markPathRange(0, 78, 52, 78);

    }
    public void update(float delta) {
        for (NPC npc : NPCList) {
            npc.update(delta); // Update each NPC
            batch.begin();
            batch.draw(npc.getFrame(delta), npc.getPosition().x - ((float) Consts.CELL_SIZE / 2) + (npc.getHalfSize()),
                npc.getPosition().y + ((float) Consts.CELL_SIZE / 2) + (npc.getHalfSize()), Consts.CELL_SIZE, Consts.CELL_SIZE);
            batch.end();
        }
    }


    // ADD NPCs with a 0.3s cool down - using threads so doesn't steal all system resources
    public static void addNPC(int count) {
        new Thread(() -> {
            for (int i = 0; i < count; i++) {
                NPC npc = new NPC(grid);
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
