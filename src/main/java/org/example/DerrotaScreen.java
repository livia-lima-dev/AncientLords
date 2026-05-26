package org.example;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.gui.screens.Screen;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DerrotaScreen extends Screen {

    private BufferedImage background;
    private BufferedImage tentarNovamenteButton;
    private BufferedImage menuButton;

    public DerrotaScreen() {
        super("derrota");
        loadImages();
    }

    private void loadImages() {
        try {

            background = javax.imageio.ImageIO.read(
                    getClass().getResourceAsStream(
                            "/assets/finalizacao/derrota/fundo_tela_derrota.png"
                    )
            );

            tentarNovamenteButton = javax.imageio.ImageIO.read(
                    getClass().getResourceAsStream(
                            "/assets/finalizacao/derrota/btn_tentenovamente.png"
                    )
            );

            menuButton = javax.imageio.ImageIO.read(
                    getClass().getResourceAsStream(
                            "/assets/finalizacao/derrota/btn_menu.png"
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

        // =========================
        // BOTÕES
        // =========================

        // diminuiu tamanho
        int buttonWidth = 360;

        int buttonHeight = 78;

        // centraliza certinho
        int x = (screenWidth - buttonWidth) / 2;

        // espaço entre eles
        int spacing = 20;

        // posição vertical mais equilibrada
        int startY = screenHeight / 2 + 40;

        // tentar novamente

        g.drawImage(
                tentarNovamenteButton,
                x,
                startY,
                buttonWidth,
                buttonHeight,
                null
        );

        // menu

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