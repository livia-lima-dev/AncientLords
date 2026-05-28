package org.example;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.gui.screens.Screen;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class VitoriaScreen extends Screen {

    private BufferedImage background;
    private BufferedImage jogarNovamenteButton;
    private BufferedImage inicioButton;

    private int screenWidth;
    private int screenHeight;

    private int buttonWidth;
    private int buttonHeight;
    private int buttonX;
    private int spacing;
    private int startY;

    public VitoriaScreen() {

        super("vitoria");

        PlayerData.vitorias++;

        PlayerData.totalPartidas++;

        loadImages();
    }

    private void loadImages() {

        try {

            background =
                    loadImage(
                            "/assets/finalizacao/vitoria/fundo_tela_vitoria.png"
                    );

            jogarNovamenteButton =
                    loadImage(
                            "/assets/finalizacao/vitoria/btn_jogarnovamente.png"
                    );

            inicioButton =
                    loadImage(
                            "/assets/finalizacao/vitoria/btn_menu.png"
                    );

        } catch (Exception e) {

            System.out.println("ERRO AO CARREGAR IMAGENS");

            e.printStackTrace();
        }
    }

    private BufferedImage loadImage(String path) throws Exception {

        return ImageIO.read(
                Objects.requireNonNull(
                        getClass().getResourceAsStream(path)
                )
        );
    }

    @Override
    public void render(Graphics2D g) {

        super.render(g);

        calcularMedidas();

        renderBackground(g);

        renderBotoes(g);
    }

    private void calcularMedidas() {

        screenWidth =
                Game.window().getWidth();

        screenHeight =
                Game.window().getHeight();

        buttonWidth = 440;

        buttonHeight = 96;

        buttonX =
                (screenWidth - buttonWidth) / 2;

        spacing = 18;

        int totalHeight =
                (buttonHeight * 2) + spacing;

        startY =
                ((screenHeight - totalHeight) / 2) + 130;
    }

    private void renderBackground(Graphics2D g) {

        g.drawImage(
                background,
                0,
                0,
                screenWidth,
                screenHeight,
                null
        );
    }

    private void renderBotoes(Graphics2D g) {

        g.drawImage(
                jogarNovamenteButton,
                buttonX,
                startY,
                buttonWidth,
                buttonHeight,
                null
        );

        g.drawImage(
                inicioButton,
                buttonX,
                startY + buttonHeight + spacing,
                buttonWidth,
                buttonHeight,
                null
        );
    }
}