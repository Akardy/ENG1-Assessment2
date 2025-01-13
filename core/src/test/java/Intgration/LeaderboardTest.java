package Intgration;

import com.badlogic.UniSim2.GUImanager.StartScreen;
import com.badlogic.UniSim2.Main;
import com.badlogic.UniSim2.resources.Consts;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LeaderboardTest {

    private Main main;

    @BeforeEach
    void setUp() {
        main = mock(Main.class);
        Gdx.graphics = mock(Graphics.class);
    }

    @Test
    void readsLeaderboardTest() {
        FileHandle mockFileHandle = mock(FileHandle.class);
        when(mockFileHandle.readString()).thenReturn("11.00\n11.00\n");
        when(main.getViewport()).thenReturn(new StretchViewport(Consts.WORLD_WIDTH, Consts.WORLD_HEIGHT));
        StartScreen startScreen = new StartScreen(main, mockFileHandle);


        List<Float> data = startScreen.getLeaderboardData();

        assertEquals(2, data.size());
        assertEquals(11.00f, data.get(0));
        assertEquals(11.00f, data.get(1));
    }
}
