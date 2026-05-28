package org.example;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.gui.screens.Screen;
import de.gurkenlabs.litiengine.input.Input;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class DerrotaScreen extends Screen {

    private BufferedImage background;
    private BufferedImage tentarNovamenteButton;
    private BufferedImage inicioButton;

    private int screenWidth;
    private int screenHeight;

    private int buttonWidth;
    private int buttonHeight;
    private int buttonX;
    private int spacing;
    private int startY;

    private Rectangle tentarNovamenteRect;
    private Rectangle inicioRect;

    public DerrotaScreen() {

        super("derrota");

        PlayerData.derrotas++;

        PlayerData.totalPartidas++;

        loadImages();

        setupMouseInput();
    }

    private void loadImages() {

        try {

            background =
                    loadImage(
                            "/assets/finalizacao/derrota/fundo_tela_derrota.png"
                    );

            tentarNovamenteButton =
                    loadImage(
                            "/assets/finalizacao/derrota/btn_tentenovamente.png"
                    );

            inicioButton =
                    loadImage(
                            "/assets/finalizacao/derrota/btn_menu.png"
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

    // ========================================
    // INPUT MOUSE
    // ========================================

    private void setupMouseInput() {

        Input.mouse().onMoved(event -> {

            // ========================================
            // IGNORA INPUT SE A TELA NÃO ESTIVER ATIVA
            // ========================================

            if (!Game.screens().current().getName().equals(getName())) {
                return;
            }

            updateCursor(event.getPoint());
        });
    }

    // ========================================
    // CURSOR
    // ========================================

    private void updateCursor(Point mousePosition) {

        if (
                (
                        tentarNovamenteRect != null
                                && tentarNovamenteRect.contains(mousePosition)
                )
                        ||
                        (
                                inicioRect != null
                                        && inicioRect.contains(mousePosition)
                        )
        ) {

            Game.window().getRenderComponent().setCursor(
                    Cursor.getPredefinedCursor(
                            Cursor.HAND_CURSOR
                    )
            );

            return;
        }

        Game.window().getRenderComponent().setCursor(
                Cursor.getDefaultCursor()
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

        buttonWidth = 360;

        buttonHeight = 78;

        buttonX =
                (screenWidth - buttonWidth) / 2;

        spacing = 20;

        startY =
                screenHeight / 2 + 40;
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
                tentarNovamenteButton,
                buttonX,
                startY,
                buttonWidth,
                buttonHeight,
                null
        );

        tentarNovamenteRect =
                new Rectangle(
                        buttonX,
                        startY,
                        buttonWidth,
                        buttonHeight
                );

        g.drawImage(
                inicioButton,
                buttonX,
                startY + buttonHeight + spacing,
                buttonWidth,
                buttonHeight,
                null
        );

        inicioRect =
                new Rectangle(
                        buttonX,
                        startY + buttonHeight + spacing,
                        buttonWidth,
                        buttonHeight
                );
    }
}