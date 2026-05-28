package org.example;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.gui.screens.Screen;
import de.gurkenlabs.litiengine.input.Input;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class EsqueceuSenhaScreen extends Screen {

    private BufferedImage background;

    private BufferedImage confirmarButtonImage;

    private Font medievalFont;

    private String email = "";

    private boolean emailSelecionado = false;

    private String mensagemPopup = "";
    private boolean mostrarPopup = false;
    private boolean popupSucesso = false;
    private long tempoPopup = 0;

    private Rectangle emailRect;

    private Rectangle confirmarRect;

    private Rectangle voltarRect;

    public EsqueceuSenhaScreen() {

        super("esqueceuSenha");

        loadAssets();

        setupKeyboardInput();

        setupMouseInput();
    }

    @Override
    public void prepare() {

        super.prepare();

        resetarTela();
    }

    private void resetarTela() {

        email = "";

        emailSelecionado = false;

        mensagemPopup = "";
        mostrarPopup = false;
        popupSucesso = false;
        tempoPopup = 0;
    }

    private void loadAssets() {

        try {

            background = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/assets/esqueceuSenha/fundo_tela_esqueceusenha.png"
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

            GraphicsEnvironment ge =
                    GraphicsEnvironment.getLocalGraphicsEnvironment();

            ge.registerFont(medievalFont);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private void setupKeyboardInput() {

        Input.keyboard().onKeyTyped(event -> {

            if (!Game.screens().current().getName().equals(getName())) {
                return;
            }

            if (!emailSelecionado) {
                return;
            }

            char c = event.getKeyChar();

            if (
                    c == KeyEvent.CHAR_UNDEFINED
                            || Character.isISOControl(c)
            ) {
                return;
            }

            if (
                    !Character.isLetterOrDigit(c)
                            && "@._-".indexOf(c) == -1
            ) {
                return;
            }

            if (email.length() >= 28) {
                return;
            }

            email += c;
        });

        Input.keyboard().onKeyReleased(event -> {

            if (!Game.screens().current().getName().equals(getName())) {
                return;
            }

            if (event.getKeyCode() != KeyEvent.VK_BACK_SPACE) {
                return;
            }

            if (
                    emailSelecionado
                            && email.length() > 0
            ) {

                email =
                        email.substring(
                                0,
                                email.length() - 1
                        );
            }
        });
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

            if (
                    voltarRect != null
                            && voltarRect.contains(mousePosition)
            ) {

                Game.screens().display("login");

                return;
            }

            emailSelecionado = false;

            if (
                    emailRect != null
                            && emailRect.contains(mousePosition)
            ) {

                emailSelecionado = true;
            }

            if (
                    confirmarRect != null
                            && confirmarRect.contains(mousePosition)
            ) {

                validarEmail();
            }
        });
    }

    private void validarEmail() {

        if (
                email.isEmpty()
                        || !email.contains("@")
                        || !email.contains(".")
        ) {

            mostrarPopup(
                    "Digite um e-mail válido.",
                    false
            );

        } else {

            mostrarPopup(
                    "E-mail de recuperação enviado.",
                    true
            );

            System.out.println(
                    "E-mail para recuperação: " + email
            );
        }
    }

    private void mostrarPopup(String mensagem, boolean sucesso) {

        mensagemPopup = mensagem;

        popupSucesso = sucesso;

        mostrarPopup = true;

        tempoPopup = System.currentTimeMillis();
    }

    private void updateCursor(Point mousePosition) {

        if (
                emailRect != null
                        && emailRect.contains(mousePosition)
        ) {

            Game.window().getRenderComponent().setCursor(
                    Cursor.getPredefinedCursor(
                            Cursor.TEXT_CURSOR
                    )
            );

            return;
        }

        if (
                (
                        confirmarRect != null
                                && confirmarRect.contains(mousePosition)
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

    private void renderBotaoVoltar(Graphics2D g) {

        g.setFont(
                medievalFont.deriveFont(22f)
        );

        String texto =
                "‹ Voltar";

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

    private void drawInputBox(
            Graphics2D g,
            Rectangle rect,
            String texto,
            boolean selecionado
    ) {

        g.setColor(
                new Color(
                        35,
                        25,
                        15,
                        220
                )
        );

        g.fillRoundRect(
                rect.x,
                rect.y,
                rect.width,
                rect.height,
                18,
                18
        );

        if (selecionado) {

            g.setColor(
                    new Color(
                            210,
                            180,
                            90
                    )
            );

        } else {

            g.setColor(
                    new Color(
                            130,
                            100,
                            50
                    )
            );
        }

        g.setStroke(
                new BasicStroke(3)
        );

        g.drawRoundRect(
                rect.x,
                rect.y,
                rect.width,
                rect.height,
                18,
                18
        );

        g.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        20
                )
        );

        FontMetrics metrics =
                g.getFontMetrics();

        int textX =
                rect.x + 20;

        int textY =
                rect.y
                        + (
                        (
                                rect.height
                                        - metrics.getHeight()
                        ) / 2
                )
                        + metrics.getAscent();

        drawOutlinedText(
                g,
                texto,
                textX,
                textY,
                Color.WHITE
        );

        if (selecionado) {

            int cursorX =
                    textX
                            + metrics.stringWidth(texto)
                            + 2;

            drawOutlinedText(
                    g,
                    "|",
                    cursorX,
                    textY,
                    Color.WHITE
            );
        }
    }

    @Override
    public void render(Graphics2D g) {

        super.render(g);

        int screenWidth =
                Game.window().getWidth();

        int screenHeight =
                Game.window().getHeight();

        g.drawImage(
                background,
                0,
                0,
                screenWidth,
                screenHeight,
                null
        );

        renderBotaoVoltar(g);

        g.setFont(
                medievalFont.deriveFont(34f)
        );

        String titulo =
                "Digite seu e-mail para recuperar sua senha";

        drawOutlinedText(
                g,
                titulo,
                getCenteredTextX(
                        g,
                        titulo,
                        screenWidth / 2
                ),
                240,
                Color.WHITE
        );

        int inputWidth = 620;

        int inputHeight = 65;

        int inputX =
                (screenWidth / 2) - (inputWidth / 2);

        int inputY = 330;

        emailRect =
                new Rectangle(
                        inputX,
                        inputY,
                        inputWidth,
                        inputHeight
                );

        drawInputBox(
                g,
                emailRect,
                email,
                emailSelecionado
        );

        int confirmarWidth = 250;

        int confirmarHeight = 85;

        int confirmarX =
                (screenWidth / 2) - (confirmarWidth / 2);

        int confirmarY = 470;

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

        renderPopup(g, screenWidth);
    }

    private void renderPopup(Graphics2D g, int screenWidth) {

        if (!mostrarPopup) {
            return;
        }

        if (
                System.currentTimeMillis()
                        - tempoPopup > 2000
        ) {

            mostrarPopup = false;

            return;
        }

        g.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )
        );

        FontMetrics metrics =
                g.getFontMetrics();

        int x =
                (
                        screenWidth
                                - metrics.stringWidth(mensagemPopup)
                ) / 2;

        int y = 445;

        if (popupSucesso) {

            g.setColor(
                    new Color(
                            40,
                            120,
                            40,
                            220
                    )
            );

        } else {

            g.setColor(
                    new Color(
                            120,
                            40,
                            40,
                            220
                    )
            );
        }

        g.fillRoundRect(
                x - 20,
                y - 28,
                metrics.stringWidth(mensagemPopup) + 40,
                40,
                15,
                15
        );

        if (popupSucesso) {

            g.setColor(
                    new Color(
                            180,
                            220,
                            180
                    )
            );

        } else {

            g.setColor(
                    new Color(
                            220,
                            160,
                            160
                    )
            );
        }

        g.setStroke(
                new BasicStroke(2)
        );

        g.drawRoundRect(
                x - 20,
                y - 28,
                metrics.stringWidth(mensagemPopup) + 40,
                40,
                15,
                15
        );

        drawOutlinedText(
                g,
                mensagemPopup,
                x,
                y,
                Color.WHITE
        );
    }
}