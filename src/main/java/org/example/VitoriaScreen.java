package org.example;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.gui.screens.Screen;

import java.awt.*;
import java.awt.image.BufferedImage;

public class VitoriaScreen extends Screen {

    private BufferedImage background;
    private BufferedImage jogarNovamenteButton;
    private BufferedImage menuButton;

    public VitoriaScreen() {
        super("vitoria");
        loadImages();
    }

    private void loadImages() {
        try {
            background = javax.imageio.ImageIO.read(
                    getClass().getResourceAsStream(
                            "/assets/finalizacao/vitoria/fundo_tela_vitoria.png"
                    )
            );

            jogarNovamenteButton = javax.imageio.ImageIO.read(
                    getClass().getResourceAsStream(
                            "/assets/finalizacao/vitoria/btn_jogarnovamente.png"
                    )
            );

            menuButton = javax.imageio.ImageIO.read(
                    getClass().getResourceAsStream(
                            "/assets/finalizacao/vitoria/btn_menu.png"
                    )
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render(Graphics2D g) {
        super.render(g);

        int screenWidth = Game.window().getWidth();
        int screenHeight = Game.window().getHeight();

        g.drawImage(
                background,
                0,
                0,
                screenWidth,
                screenHeight,
                null
        );

        int buttonWidth = 440;
        int buttonHeight = 96;

        int x = (screenWidth - buttonWidth) / 2;

        int spacing = 18;

        int totalHeight =
                (buttonHeight * 2)
                        + spacing;

        int startY =
                (screenHeight - totalHeight) / 2
                        + 130;

        g.drawImage(
                jogarNovamenteButton,
                x,
                startY,
                buttonWidth,
                buttonHeight,
                null
        );

        g.drawImage(
                menuButton,
                x,
                startY + buttonHeight + spacing,
                buttonWidth,
                buttonHeight,
                null
        );
    }
}