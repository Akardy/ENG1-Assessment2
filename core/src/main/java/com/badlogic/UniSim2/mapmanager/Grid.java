package com.badlogic.UniSim2.mapmanager;

import com.badlogic.UniSim2.buildingmanager.Building;
import com.badlogic.UniSim2.resources.Consts;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * This class is used to simulate the grid structure of the game.
 * Currently the only method that should be called is {@link #draw(StretchViewport)}.
 */
public class Grid {

    private final int rows;
    private final int cols;
    private final Color gridColor;
    private final float cellSize;

    // Each grid space will have a status (Not used yet)
    public enum Status{
        Building,
        Path,
        Unoccupied
    }
    private Status[][] grid; // 2D array to get status at any point (Not used yet)

    private ShapeRenderer shapeRenderer; // Used to draw lines onto the screen.

    public Grid() {
        rows = Consts.GRID_ROWS;// + 15;
        //System.out.println(rows);
        cols = Consts.GRID_COLS;// + 21;
        grid = new Status[rows][cols];
        cellSize = Consts.CELL_SIZE;
        gridColor = Consts.GRID_COLOR;
        shapeRenderer = new ShapeRenderer();
    }

    // Called when a building is placed to update the status of the corresponding grid spaces (Not used yet)
    private void updateGrid(Building building){

        int startCol = building.getCol();
        int endCol = startCol + building.getBuildingWidth();
        int endRow = building.getRow();
        int startRow = endRow - building.getBuildingHeight();

        for (int row = startRow; row <= endRow; row++) {
            for(int col = startCol; col <= endCol; col++){
                grid[row][col] = Status.Building;
            }
        }
    }

    /**
     * Draws the lines of the grid.
     * @param viewport
     */
    public void draw(StretchViewport viewport) {
        setupRenderer(viewport);
        drawHorizontalLines();
        drawVerticalLines();
        shapeRenderer.end();
    }

    // Set up the ShapeRenderer with the viewport
    private void setupRenderer(StretchViewport viewport) {
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(gridColor);
    }

    // Draw horizontal grid lines
    private void drawHorizontalLines() {
        for (float i = 0; i < rows; i++) {
            float y = i * cellSize;
            shapeRenderer.line(0, y, cols * cellSize, y); // Line from left to right
        }
    }
    public void markPathRange(int startRow, int startCol, int endRow, int endCol) {

        for (int row = startRow; row <= endRow; row++) {
            for (int col = startCol; col <= endCol; col++) {
                markPathCell(row, col);
            }
        }
    }
    public void markPathCell(int row, int col) {
        if (isValidCell(row, col)) {
            grid[row][col] = Status.Path;
        }
    }
    private boolean isValidCell(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    // Draw vertical grid lines
    private void drawVerticalLines() {
        for (float i = 0; i < cols; i++) {
            float x = i * cellSize;
            shapeRenderer.line(x, 0, x, rows * cellSize); // Line from top to bottom
        }
    }
    public boolean isPathCell(Vector2 position) {
        int col = (int) position.x;
        int row = (int) position.y;

        if (isValidCell(row, col)) {
            return grid[row][col] == Status.Path;
        }
        return false;
    }

    public void dispose() {
        shapeRenderer.dispose();
    }
}
