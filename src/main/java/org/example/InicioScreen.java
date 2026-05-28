package org.example;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.gui.screens.Screen;
import de.gurkenlabs.litiengine.input.Input;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class InicioScreen extends Screen {

    private BufferedImage background;
    private BufferedImage iniciarButton;
    private BufferedImage continuarButton;
    private BufferedImage perfilButton;
    private BufferedImage configuracoesButton;
    private BufferedImage sairButton;
    private BufferedImage logo;

    private Rectangle perfilRect;
    private Rectangle configuracoesRect;
    private Rectangle sairRect;

    private int screenWidth;
    private int screenHeight;

    private int logoWidth;
    private int logoHeight;
    private int logoX;
    private int logoY;

    private int buttonWidth;
    private int buttonHeight;
    private int buttonX;
    private int spacing;
    private int startY;

    public InicioScreen() {

        super("inicio");

        loadImages();

        setupMouseInput();
    }

    private void loadImages() {

        try {

            background = loadImage("/assets/inicio/fundo_tela_inicio.png");

            iniciarButton = loadImage("/assets/inicio/btn_iniciar.png");

            continuarButton = loadImage("/assets/inicio/btn_continuar.png");

            perfilButton = loadImage("/assets/inicio/btn_perfil.png");

            configuracoesButton = loadImage("/assets/inicio/btn_configuracoes.png");

            sairButton = loadImage("/assets/inicio/btn_sair.png");

            logo = loadImage("/assets/inicio/logo.png");

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

    private void setupMouseInput() {

        Input.mouse().onClicked(event -> {

            // ========================================
            // IGNORA INPUT SE A TELA NÃO ESTIVER ATIVA
            // ========================================

            if (!Game.screens().current().getName().equals(getName())) {
                return;
            }

            Point mousePosition = event.getPoint();

            // ========================================
            // PERFIL
            // ========================================

            if (
                    perfilRect != null
                            && perfilRect.contains(mousePosition)
            ) {

                Game.screens().display("perfil");

                return;
            }

            // ========================================
            // CONFIGURAÇÕES
            // ========================================

            if (
                    configuracoesRect != null
                            && configuracoesRect.contains(mousePosition)
            ) {

                Game.screens().display("configuracoes");

                return;
            }

            // ========================================
            // SAIR
            // ========================================

            if (
                    sairRect != null
                            && sairRect.contains(mousePosition)
            ) {

                Game.exit();
            }
        });
    }

    @Override
    public void render(Graphics2D g) {

        super.render(g);

        calcularMedidas();

        renderBackground(g);

        renderLogo(g);

        renderBotoes(g);
    }

    private void calcularMedidas() {

        screenWidth = Game.window().getWidth();

        screenHeight = Game.window().getHeight();

        logoWidth = 700;

        logoHeight = 250;

        logoX = (screenWidth - logoWidth) / 2;

        logoY = 10;

        buttonWidth = 480;

        buttonHeight = 88;

        buttonX = (screenWidth - buttonWidth) / 2;

        spacing = 12;

        startY =
                (
                        (
                                screenHeight
                                        - (
                                        (buttonHeight * 5)
                                                + (spacing * 4)
                                )
                        ) / 2
                ) + 100;
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

    private void renderLogo(Graphics2D g) {

        g.drawImage(
                logo,
                logoX,
                logoY,
                logoWidth,
                logoHeight,
                null
        );
    }

    private void renderBotoes(Graphics2D g) {

        int iniciarY = startY;

        int continuarY =
                startY + (buttonHeight + spacing);

        int perfilY =
                startY + ((buttonHeight + spacing) * 2);

        int configuracoesY =
                startY + ((buttonHeight + spacing) * 3);

        int sairY =
                startY + ((buttonHeight + spacing) * 4);

        g.drawImage(
                iniciarButton,
                buttonX,
                iniciarY,
                buttonWidth,
                buttonHeight,
                null
        );

        g.drawImage(
                continuarButton,
                buttonX,
                continuarY,
                buttonWidth,
                buttonHeight,
                null
        );

        g.drawImage(
                perfilButton,
                buttonX,
                perfilY,
                buttonWidth,
                buttonHeight,
                null
        );

        g.drawImage(
                configuracoesButton,
                buttonX,
                configuracoesY,
                buttonWidth,
                buttonHeight,
                null
        );

        g.drawImage(
                sairButton,
                buttonX,
                sairY,
                buttonWidth,
                buttonHeight,
                null
        );

        perfilRect =
                new Rectangle(
                        buttonX,
                        perfilY,
                        buttonWidth,
                        buttonHeight
                );

        configuracoesRect =
                new Rectangle(
                        buttonX,
                        configuracoesY,
                        buttonWidth,
                        buttonHeight
                );

        sairRect =
                new Rectangle(
                        buttonX,
                        sairY,
                        buttonWidth,
                        buttonHeight
                );
    }
}