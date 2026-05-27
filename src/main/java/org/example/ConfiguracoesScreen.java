package org.example;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.gui.screens.Screen;
import de.gurkenlabs.litiengine.input.Input;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class ConfiguracoesScreen extends Screen {

    // =========================
    // IMAGENS
    // =========================

    private BufferedImage background;

    private BufferedImage emailInputImage;

    private BufferedImage confirmarButtonImage;

    // =========================
    // FONTES
    // =========================

    private Font medievalFont;

    private Font medievalSmallFont;

    // =========================
    // INPUT EMAIL
    // =========================

    private String emailText = "";

    private boolean editingEmail = false;

    // =========================
    // DIFICULDADE
    // =========================

    private String dificuldadeSelecionada = "Fácil";

    // =========================
    // ÁREAS CLICÁVEIS
    // =========================

    private Rectangle facilRect;

    private Rectangle medioRect;

    private Rectangle dificilRect;

    private Rectangle emailRect;

    private Rectangle confirmarRect;

    // =========================
    // POSICIONAMENTO
    // =========================

    private final int leftSectionX = 430;

    private final int rightSectionX = 1030;

    private final int sectionTitleY = 240;

    public ConfiguracoesScreen() {

        super("configuracoes");

        loadAssets();

        setupKeyboardInput();

        setupMouseInput();
    }

    // =========================
    // CARREGAR ASSETS
    // =========================

    private void loadAssets() {

        try {

            background = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/assets/configuracoes/background_2.png"
                    )
            );

            emailInputImage = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/assets/configuracoes/ct_email.png"
                    )
            );

            confirmarButtonImage = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/assets/configuracoes/btn_confirmar.png"
                    )
            );

            medievalFont = Font.createFont(
                    Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream(
                            "/assets/fontes/Cinzel-Bold.ttf"
                    )
            ).deriveFont(30f);

            medievalSmallFont = Font.createFont(
                    Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream(
                            "/assets/fontes/Cinzel-Regular.ttf"
                    )
            ).deriveFont(24f);

            GraphicsEnvironment ge =
                    GraphicsEnvironment.getLocalGraphicsEnvironment();

            ge.registerFont(medievalFont);

            ge.registerFont(medievalSmallFont);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // =========================
    // INPUT TECLADO
    // =========================

    private void setupKeyboardInput() {

        Input.keyboard().onKeyTyped(event -> {

            if (!editingEmail) {
                return;
            }

            char c = event.getKeyChar();

            if (
                    c == KeyEvent.CHAR_UNDEFINED
                            || Character.isISOControl(c)
            ) {
                return;
            }

            // =========================
            // LIMITAR TAMANHO TEXTO
            // =========================

            if (emailText.length() >= 22) {
                return;
            }

            emailText += c;
        });

        Input.keyboard().onKeyReleased(event -> {

            if (!editingEmail) {
                return;
            }

            // BACKSPACE
            if (event.getKeyCode() == KeyEvent.VK_BACK_SPACE) {

                if (emailText.length() > 0) {

                    emailText =
                            emailText.substring(
                                    0,
                                    emailText.length() - 1
                            );
                }
            }
        });
    }

    // =========================
    // INPUT MOUSE
    // =========================

    private void setupMouseInput() {

        Input.mouse().onClicked(event -> {

            Point mousePosition = event.getPoint();

            // =========================
            // DIFICULDADE
            // =========================

            if (
                    facilRect != null
                            && facilRect.contains(mousePosition)
            ) {

                dificuldadeSelecionada = "Fácil";
            }

            if (
                    medioRect != null
                            && medioRect.contains(mousePosition)
            ) {

                dificuldadeSelecionada = "Médio";
            }

            if (
                    dificilRect != null
                            && dificilRect.contains(mousePosition)
            ) {

                dificuldadeSelecionada = "Difícil";
            }

            // =========================
            // EMAIL
            // =========================

            if (
                    emailRect != null
                            && emailRect.contains(mousePosition)
            ) {

                editingEmail = true;

            } else {

                editingEmail = false;
            }

            // =========================
            // CONFIRMAR
            // =========================

            if (
                    confirmarRect != null
                            && confirmarRect.contains(mousePosition)
            ) {

                System.out.println(
                        "EMAIL ENVIADO PARA: "
                                + emailText
                );
            }
        });
    }

    // =========================
    // TEXTO COM BORDA
    // =========================

    private void drawOutlinedText(
            Graphics2D g,
            String text,
            int x,
            int y
    ) {

        // BORDA
        g.setColor(Color.BLACK);

        g.drawString(text, x - 1, y - 1);

        g.drawString(text, x + 1, y - 1);

        g.drawString(text, x - 1, y + 1);

        g.drawString(text, x + 1, y + 1);

        // TEXTO
        g.setColor(Color.WHITE);

        g.drawString(text, x, y);
    }

    // =========================
    // CENTRALIZAR TEXTO
    // =========================

    private int getCenteredTextX(
            Graphics2D g,
            String text,
            int centerX
    ) {

        FontMetrics metrics =
                g.getFontMetrics();

        return centerX -
                (
                        metrics.stringWidth(text) / 2
                );
    }

    // =========================
    // RENDER
    // =========================

    @Override
    public void render(Graphics2D g) {

        super.render(g);

        int screenWidth =
                Game.window().getWidth();

        int screenHeight =
                Game.window().getHeight();

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

        // =====================================================
        // ÁREA ESQUERDA - DIFICULDADE
        // =====================================================

        g.setFont(
                medievalFont.deriveFont(30f)
        );

        String dificuldadeTitulo =
                "DIFICULDADE";

        drawOutlinedText(
                g,
                dificuldadeTitulo,
                getCenteredTextX(
                        g,
                        dificuldadeTitulo,
                        leftSectionX
                ),
                sectionTitleY
        );

        // =========================
        // OPÇÕES
        // =========================

        g.setFont(
                medievalSmallFont.deriveFont(28f)
        );

        int optionStartY = 300;

        int optionSpacing = 95;

        // Fácil
        String facilTexto =
                dificuldadeSelecionada.equals("Fácil")
                        ? "> FÁCIL <"
                        : "FÁCIL";

        drawOutlinedText(
                g,
                facilTexto,
                getCenteredTextX(
                        g,
                        facilTexto,
                        leftSectionX
                ),
                optionStartY
        );

        facilRect =
                new Rectangle(
                        leftSectionX - 120,
                        optionStartY - 45,
                        240,
                        60
                );

        // Médio
        String medioTexto =
                dificuldadeSelecionada.equals("Médio")
                        ? "> MÉDIO <"
                        : "MÉDIO";

        drawOutlinedText(
                g,
                medioTexto,
                getCenteredTextX(
                        g,
                        medioTexto,
                        leftSectionX
                ),
                optionStartY + optionSpacing
        );

        medioRect =
                new Rectangle(
                        leftSectionX - 120,
                        optionStartY + optionSpacing - 45,
                        240,
                        60
                );

        // Difícil
        String dificilTexto =
                dificuldadeSelecionada.equals("Difícil")
                        ? "> DIFÍCIL <"
                        : "DIFÍCIL";

        drawOutlinedText(
                g,
                dificilTexto,
                getCenteredTextX(
                        g,
                        dificilTexto,
                        leftSectionX
                ),
                optionStartY + (optionSpacing * 2)
        );

        dificilRect =
                new Rectangle(
                        leftSectionX - 120,
                        optionStartY + (optionSpacing * 2) - 45,
                        240,
                        60
                );

        // =====================================================
        // ÁREA DIREITA - REDEFINIR SENHA
        // =====================================================

        g.setFont(
                medievalFont.deriveFont(30f)
        );

        String senhaTitulo =
                "REDEFINIR SENHA";

        drawOutlinedText(
                g,
                senhaTitulo,
                getCenteredTextX(
                        g,
                        senhaTitulo,
                        rightSectionX
                ),
                sectionTitleY
        );

        // =========================
        // INPUT EMAIL
        // =========================

        int emailWidth = 650;

        int emailHeight = 130;

        int emailX =
                rightSectionX - (emailWidth / 2);

        int emailY = 260;

        g.drawImage(
                emailInputImage,
                emailX,
                emailY,
                emailWidth,
                emailHeight,
                null
        );

        emailRect =
                new Rectangle(
                        emailX,
                        emailY,
                        emailWidth,
                        emailHeight
                );

        // =========================
        // TEXTO EMAIL CENTRALIZADO
        // =========================

        g.setFont(
                medievalSmallFont.deriveFont(20f)
        );

        FontMetrics emailMetrics =
                g.getFontMetrics();

        // centraliza horizontalmente
        int textX =
                emailX
                        + (emailWidth / 2)
                        - (
                        emailMetrics.stringWidth(emailText) / 2
                );

        // centraliza verticalmente
        int textY =
                emailY
                        + (
                        (
                                emailHeight
                                        - emailMetrics.getHeight()
                        ) / 2
                )
                        + emailMetrics.getAscent();

        drawOutlinedText(
                g,
                emailText,
                textX,
                textY
        );

        // =========================
        // CURSOR
        // =========================

        if (editingEmail) {

            int cursorX =
                    textX
                            + emailMetrics.stringWidth(emailText)
                            + 2;

            drawOutlinedText(
                    g,
                    "|",
                    cursorX,
                    textY
            );
        }

        // =========================
        // BOTÃO CONFIRMAR
        // =========================

        int confirmarWidth = 240;

        int confirmarHeight = 90;

        int confirmarX =
                rightSectionX - (confirmarWidth / 2);

        int confirmarY = 390;

        g.drawImage(
                confirmarButtonImage,
                confirmarX,
                confirmarY,
                confirmarWidth,
                confirmarHeight,
                null
        );

        confirmarRect =
                new Rectangle(
                        confirmarX,
                        confirmarY,
                        confirmarWidth,
                        confirmarHeight
                );
    }
}