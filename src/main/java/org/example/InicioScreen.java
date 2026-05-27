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

    private Rectangle perfilRect;

    private Rectangle configuracoesRect;

    private Rectangle sairRect;

    public InicioScreen() {

        super("inicio");

        loadImages();

        setupMouseInput();
    }

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

    private void setupMouseInput() {

        Input.mouse().onClicked(event -> {

            Point mousePosition = event.getPoint();

            if (
                    perfilRect != null &&
                            perfilRect.contains(mousePosition)
            ) {

                Game.screens().display("perfil");

                return;
            }

            if (
                    configuracoesRect != null &&
                            configuracoesRect.contains(mousePosition)
            ) {

                Game.screens().display("configuracoes");

                return;
            }

            if (
                    sairRect != null &&
                            sairRect.contains(mousePosition)
            ) {

                Game.exit();
            }
        });
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

        int buttonWidth = 480;

        int buttonHeight = 88;

        int x = (screenWidth - buttonWidth) / 2;

        int spacing = 12;

        int startY =
                ((screenHeight - ((buttonHeight * 5) + (spacing * 4))) / 2)
                        + 100;

        g.drawImage(
                iniciarButton,
                x,
                startY,
                buttonWidth,
                buttonHeight,
                null
        );

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

        int perfilY =
                startY + ((buttonHeight + spacing) * 2);

        g.drawImage(
                perfilButton,
                x,
                perfilY,
                buttonWidth,
                buttonHeight,
                null
        );

        perfilRect = new Rectangle(
                x,
                perfilY,
                buttonWidth,
                buttonHeight
        );

        int configuracoesY =
                startY + ((buttonHeight + spacing) * 3);

        g.drawImage(
                configuracoesButton,
                x,
                configuracoesY,
                buttonWidth,
                buttonHeight,
                null
        );

        configuracoesRect = new Rectangle(
                x,
                configuracoesY,
                buttonWidth,
                buttonHeight
        );

        int sairY =
                startY + ((buttonHeight + spacing) * 4);

        g.drawImage(
                sairButton,
                x,
                sairY,
                buttonWidth,
                buttonHeight,
                null
        );

        sairRect = new Rectangle(
                x,
                sairY,
                buttonWidth,
                buttonHeight
        );
    }
}