package com.badlogic.UniSim2.Intgration;

import com.badlogic.UniSim2.GUImanager.EndScreen;
import com.badlogic.UniSim2.GUImanager.StartScreen;
import com.badlogic.UniSim2.Main;
import com.badlogic.UniSim2.resources.Consts;
import com.badlogic.UniSim2.stats.Satisfaction;
import com.badlogic.gdx.*;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LeaderboardTest {

    private Main main;

    private StartScreen startScreen;
    private FileHandle testFile;

    @BeforeEach
    void setUp() {
        main = mock(Main.class);
        testFile = new FileHandle("Test File");
        Gdx.files = mock(Files.class);
        when(Gdx.files.local("assets/leaderboard.txt")).thenReturn(testFile);
        startScreen = new StartScreen(main, testFile, null);
    }

    @Test
    void testGetLeaderboardData_ValidData() {
        // Setup the test file with valid data
        testFile.writeString("95.2\n87.5\n99.1\n", false);

        List<Float> leaderboard = startScreen.getLeaderboardData();
        assertEquals(3, leaderboard.size());
        assertTrue(leaderboard.contains(95.2f));
        assertTrue(leaderboard.contains(87.5f));
        assertTrue(leaderboard.contains(99.1f));
    }

    @Test
    void testGetLeaderboardData_EmptyFile() {
        // Setup the test file as empty
        testFile.writeString("", false);

        List<Float> leaderboard = startScreen.getLeaderboardData();
        assertTrue(leaderboard.isEmpty());
    }

    @Test
    void testGetLeaderboardData_MalformedData() {
        // Setup the test file with some malformed data
        testFile.writeString("95.2\nn/a\n87.5\n", false);

        List<Float> leaderboard = startScreen.getLeaderboardData();
        assertEquals(2, leaderboard.size());
        assertTrue(leaderboard.contains(95.2f));
        assertTrue(leaderboard.contains(87.5f));
    }

    @Test
    void testAddLeaderBored_SortedData() {
        // Setup the test file with unordered data
        testFile.writeString("95.2\n87.5\n99.1\n92.0\n80.5\n", false);

        List<Float> leaderboard = startScreen.getLeaderboardData();
        List<Float> sortedData = startScreen.sortLeaderboardData(leaderboard);

        // Verify that the leaderboard table has the entries sorted in descending order
        assertEquals(99.1, sortedData.get(0), 0.01);
        assertEquals(95.2, sortedData.get(1), 0.01);
    }

    @Test
    void testClearLeaderboard() {
        // Setup the test file with some data
        testFile.writeString("95.2\n87.5\n99.1\n", false);

        startScreen.clearLeaderboardSat();

        // Simulate the clear leaderboard button click
        startScreen.clearLeaderboardSat();

        // Verify the leaderboard file is cleared
        assertTrue(testFile.readString().isEmpty());
    }

    @Test
    void testAddValueToLeaderboard() {
        testFile.writeString("95.2\n87.5\n99.1\n", false);
        Satisfaction mockSatisfaction = mock(Satisfaction.class);
        EndScreen endScreen = new EndScreen(main, mockSatisfaction, null);
        when(mockSatisfaction.getSatis()).thenReturn(50.00f);
        endScreen.saveSatisfaction();

        List<Float> leaderboard = startScreen.getLeaderboardData();

        assertEquals(4, leaderboard.size());
        assertEquals(leaderboard.get(0), 95.2f);
        assertEquals(leaderboard.get(1), 87.5f);
        assertEquals(leaderboard.get(2), 99.1f);
        assertEquals(leaderboard.get(3), 50.00f);

    }

    @AfterEach
    void tearDown() {
        // Cleanup the test file after each test
        testFile.delete();
    }
}
