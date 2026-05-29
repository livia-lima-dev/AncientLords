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

    private Font pixelFont;
    private Font medievalFont;

    private Rectangle iniciarRect;
    private Rectangle continuarRect;
    private Rectangle perfilRect;
    private Rectangle configuracoesRect;
    private Rectangle sairRect;
    private Rectangle voltarRect;

    private int screenWidth;
    private int screenHeight;

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

            pixelFont = Font.createFont(
                    Font.TRUETYPE_FONT,
                    Objects.requireNonNull(
                            getClass().getResourceAsStream(
                                    "/assets/fontes/Pixel.ttf"
                            )
                    )
            );

            medievalFont = Font.createFont(
                    Font.TRUETYPE_FONT,
                    Objects.requireNonNull(
                            getClass().getResourceAsStream(
                                    "/assets/fontes/Cinzel-Bold.ttf"
                            )
                    )
            ).deriveFont(22f);

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

        Input.mouse().onMoved(event -> {

            if (!Game.screens().current().getName().equals(getName())) {
                return;
            }

            updateCursor(event.getPoint());
        });

        Input.mouse().onClicked(event -> {

            if (!Game.screens().current().getName().equals(getName())) {
                return;
            }

            Point mousePosition = event.getPoint();

            // ========================================
            // VOLTAR
            // ========================================

            if (
                    voltarRect != null
                            && voltarRect.contains(mousePosition)
            ) {

                Game.screens().display("login");

                return;
            }

            if (
                    perfilRect != null
                            && perfilRect.contains(mousePosition)
            ) {

                Game.screens().display("perfil");

                return;
            }

            if (
                    configuracoesRect != null
                            && configuracoesRect.contains(mousePosition)
            ) {

                Game.screens().display("configuracoes");

                return;
            }

            if (
                    sairRect != null
                            && sairRect.contains(mousePosition)
            ) {

                Game.exit();
            }
        });
    }

    private void updateCursor(Point mousePosition) {

        if (
                (
                        iniciarRect != null
                                && iniciarRect.contains(mousePosition)
                )
                        ||
                        (
                                continuarRect != null
                                        && continuarRect.contains(mousePosition)
                        )
                        ||
                        (
                                perfilRect != null
                                        && perfilRect.contains(mousePosition)
                        )
                        ||
                        (
                                configuracoesRect != null
                                        && configuracoesRect.contains(mousePosition)
                        )
                        ||
                        (
                                sairRect != null
                                        && sairRect.contains(mousePosition)
                        )
                        ||
                        (
                                voltarRect != null
                                        && voltarRect.contains(mousePosition)
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

        renderBotaoVoltar(g);

        renderTitulo(g);

        renderBotoes(g);
    }

    private void calcularMedidas() {

        screenWidth = Game.window().getWidth();

        screenHeight = Game.window().getHeight();

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
                ) + 40;
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

    private void renderBotaoVoltar(Graphics2D g) {

        g.setFont(medievalFont);

        String texto = "‹ Voltar";

        FontMetrics metrics =
                g.getFontMetrics();

        int x = 55;

        int y = 75;

        drawOutlinedText(
                g,
                texto,
                x,
                y,
                Color.WHITE
        );

        voltarRect =
                new Rectangle(
                        x - 10,
                        y - 30,
                        metrics.stringWidth(texto) + 20,
                        40
                );
    }

    private void renderTitulo(Graphics2D g) {

        g.setFont(
                pixelFont.deriveFont(72f)
        );

        String titulo = "Ancient Lords";

        int x =
                centralizarTextoX(
                        g,
                        titulo,
                        0,
                        screenWidth
                );

        int y =
                (int) (screenHeight * 0.16);

        drawOutlinedText(
                g,
                titulo,
                x,
                y,
                Color.WHITE
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

        iniciarRect =
                new Rectangle(
                        buttonX,
                        iniciarY,
                        buttonWidth,
                        buttonHeight
                );

        g.drawImage(
                continuarButton,
                buttonX,
                continuarY,
                buttonWidth,
                buttonHeight,
                null
        );

        continuarRect =
                new Rectangle(
                        buttonX,
                        continuarY,
                        buttonWidth,
                        buttonHeight
                );

        g.drawImage(
                perfilButton,
                buttonX,
                perfilY,
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

        g.drawImage(
                configuracoesButton,
                buttonX,
                configuracoesY,
                buttonWidth,
                buttonHeight,
                null
        );

        configuracoesRect =
                new Rectangle(
                        buttonX,
                        configuracoesY,
                        buttonWidth,
                        buttonHeight
                );

        g.drawImage(
                sairButton,
                buttonX,
                sairY,
                buttonWidth,
                buttonHeight,
                null
        );

        sairRect =
                new Rectangle(
                        buttonX,
                        sairY,
                        buttonWidth,
                        buttonHeight
                );
    }

    private int centralizarTextoX(
            Graphics2D g,
            String texto,
            int x,
            int width
    ) {

        return x
                + (width / 2)
                - (
                g.getFontMetrics()
                        .stringWidth(texto) / 2
        );
    }

    private void drawOutlinedText(
            Graphics2D g,
            String text,
            int x,
            int y,
            Color mainColor
    ) {

        g.setColor(Color.BLACK);

        g.drawString(text, x - 1, y - 1);

        g.drawString(text, x + 1, y - 1);

        g.drawString(text, x - 1, y + 1);

        g.drawString(text, x + 1, y + 1);

        g.setColor(mainColor);

        g.drawString(text, x, y);
    }
}