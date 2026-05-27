package org.example;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.gui.screens.Screen;
import de.gurkenlabs.litiengine.input.Input;

import java.awt.*;
import java.awt.image.BufferedImage;

public class InicioScreen extends Screen {

    private BufferedImage background;

    private BufferedImage iniciarButton;

    private BufferedImage continuarButton;

    private BufferedImage perfilButton;

    private BufferedImage configuracoesButton;

    private BufferedImage sairButton;

    private BufferedImage logo;

    // =========================
    // ÁREAS CLICÁVEIS
    // =========================

    private Rectangle iniciarRect;

    private Rectangle continuarRect;

    private Rectangle perfilRect;

    private Rectangle configuracoesRect;

    private Rectangle sairRect;

    public InicioScreen() {

        super("inicio");

        loadImages();

        setupMouseInput();
    }

    // =========================
    // CARREGA AS IMAGENS
    // =========================

    private void loadImages() {

        try {

            background = javax.imageio.ImageIO.read(
                    getClass().getResourceAsStream(
                            "/assets/inicio/fundo_tela_inicio.png"
                    )
            );

            iniciarButton = javax.imageio.ImageIO.read(
                    getClass().getResourceAsStream(
                            "/assets/inicio/btn_iniciar.png"
                    )
            );

            continuarButton = javax.imageio.ImageIO.read(
                    getClass().getResourceAsStream(
                            "/assets/inicio/btn_continuar.png"
                    )
            );

            perfilButton = javax.imageio.ImageIO.read(
                    getClass().getResourceAsStream(
                            "/assets/inicio/btn_perfil.png"
                    )
            );

            configuracoesButton = javax.imageio.ImageIO.read(
                    getClass().getResourceAsStream(
                            "/assets/inicio/btn_configuracoes.png"
                    )
            );

            sairButton = javax.imageio.ImageIO.read(
                    getClass().getResourceAsStream(
                            "/assets/inicio/btn_sair.png"
                    )
            );

            logo = javax.imageio.ImageIO.read(
                    getClass().getResourceAsStream(
                            "/assets/inicio/logo.png"
                    )
            );

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // =========================
    // MOUSE
    // =========================

    private void setupMouseInput() {

        Input.mouse().onClicked(event -> {

            Point mousePosition = event.getPoint();

            // iniciar
            if (
                    iniciarRect != null &&
                            iniciarRect.contains(mousePosition)
            ) {

                System.out.println(
                        "CLICOU EM INICIAR"
                );
            }

            // continuar
            if (
                    continuarRect != null &&
                            continuarRect.contains(mousePosition)
            ) {

                System.out.println(
                        "CLICOU EM CONTINUAR"
                );
            }

            // perfil
            if (
                    perfilRect != null &&
                            perfilRect.contains(mousePosition)
            ) {

                System.out.println(
                        "CLICOU EM PERFIL"
                );
            }

            // configurações
            if (
                    configuracoesRect != null &&
                            configuracoesRect.contains(mousePosition)
            ) {

                System.out.println(
                        "CLICOU EM CONFIGURAÇÕES"
                );
            }

            // sair
            if (
                    sairRect != null &&
                            sairRect.contains(mousePosition)
            ) {

                System.out.println(
                        "CLICOU EM SAIR"
                );
            }
        });
    }

    // =========================
    // DESENHA NA TELA
    // =========================

    @Override
    public void render(Graphics2D g) {

        super.render(g);

        int screenWidth = Game.window().getWidth();

        int screenHeight = Game.window().getHeight();

        // =========================
        // FUNDO
        // =========================

        g.drawImage(
                background,
                0,
                0,
                screenWidth,
                screenHeight,
                null
        );

        // =========================
        // LOGO DO JOGO
        // =========================

        int logoWidth = 700;

        int logoHeight = 250;

        int logoX = (screenWidth - logoWidth) / 2;

        int logoY = 10;

        g.drawImage(
                logo,
                logoX,
                logoY,
                logoWidth,
                logoHeight,
                null
        );

        // =========================
        // CONFIGURAÇÕES DOS BOTÕES
        // =========================

        int buttonWidth = 480;

        int buttonHeight = 88;

        int x = (screenWidth - buttonWidth) / 2;

        int spacing = 12;

        int totalHeight =
                (buttonHeight * 5)
                        + (spacing * 4);

        int startY =
                (screenHeight - totalHeight) / 2
                        + 100;

        // =========================
        // INICIAR
        // =========================

        g.drawImage(
                iniciarButton,
                x,
                startY,
                buttonWidth,
                buttonHeight,
                null
        );

        iniciarRect =
                new Rectangle(
                        x,
                        startY,
                        buttonWidth,
                        buttonHeight
                );

        // =========================
        // CONTINUAR
        // =========================

        int continuarY =
                startY + (buttonHeight + spacing);

        g.drawImage(
                continuarButton,
                x,
                continuarY,
                buttonWidth,
                buttonHeight,
                null
        );

        continuarRect =
                new Rectangle(
                        x,
                        continuarY,
                        buttonWidth,
                        buttonHeight
                );

        // =========================
        // PERFIL
        // =========================

        int perfilY =
                startY + (buttonHeight + spacing) * 2;

        g.drawImage(
                perfilButton,
                x,
                perfilY,
                buttonWidth,
                buttonHeight,
                null
        );

        perfilRect =
                new Rectangle(
                        x,
                        perfilY,
                        buttonWidth,
                        buttonHeight
                );

        // =========================
        // CONFIGURAÇÕES
        // =========================

        int configuracoesY =
                startY + (buttonHeight + spacing) * 3;

        g.drawImage(
                configuracoesButton,
                x,
                configuracoesY,
                buttonWidth,
                buttonHeight,
                null
        );

        configuracoesRect =
                new Rectangle(
                        x,
                        configuracoesY,
                        buttonWidth,
                        buttonHeight
                );

        // =========================
        // SAIR
        // =========================

        int sairY =
                startY + (buttonHeight + spacing) * 4;

        g.drawImage(
                sairButton,
                x,
                sairY,
                buttonWidth,
                buttonHeight,
                null
        );

        sairRect =
                new Rectangle(
                        x,
                        sairY,
                        buttonWidth,
                        buttonHeight
                );
    }
}