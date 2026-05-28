package org.example;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.gui.screens.Screen;
import de.gurkenlabs.litiengine.input.Input;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class EsqueceuSenhaScreen extends Screen {

    // =========================
    // IMAGENS
    // =========================

    private BufferedImage background;

    private BufferedImage confirmarButtonImage;

    // =========================
    // FONTES
    // =========================

    private Font medievalFont;

    // =========================
    // INPUT EMAIL
    // =========================

    private String email = "";

    // =========================
    // VALIDAÇÃO
    // =========================

    private String mensagemValidacao = "";

    private boolean emailEnviado = false;

    // =========================
    // CONTROLE INPUT
    // =========================

    private boolean emailSelecionado = false;

    // =========================
    // ÁREAS CLICÁVEIS
    // =========================

    private Rectangle emailRect;

    private Rectangle confirmarRect;

    private Rectangle voltarRect;

    // =========================
    // CONSTRUTOR
    // =========================

    public EsqueceuSenhaScreen() {

        super("esqueceuSenha");

        loadAssets();

        setupKeyboardInput();

        setupMouseInput();
    }

    // =========================
    // RESETAR ESTADO DA TELA
    // =========================

    @Override
    public void prepare() {

        super.prepare();

        mensagemValidacao = "";

        emailEnviado = false;

        emailSelecionado = false;
    }

    // =========================
    // CARREGAR ASSETS
    // =========================

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

    // =========================
    // INPUT TECLADO
    // =========================

    private void setupKeyboardInput() {

        Input.keyboard().onKeyTyped(event -> {

            // IGNORA INPUT SE A TELA NÃO ESTIVER ATIVA
            if (!Game.screens().current().getName().equals(getName())) {
                return;
            }

            // IGNORA SE NÃO ESTIVER SELECIONADO
            if (!emailSelecionado) {
                return;
            }

            char c = event.getKeyChar();

            // IGNORA CARACTERES INVÁLIDOS
            if (
                    c == KeyEvent.CHAR_UNDEFINED
                            || Character.isISOControl(c)
            ) {
                return;
            }

            // EVITA CARACTERES ESTRANHOS
            if (
                    !Character.isLetterOrDigit(c)
                            && "@._-".indexOf(c) == -1
            ) {
                return;
            }

            // LIMITE DE CARACTERES
            if (email.length() >= 28) {
                return;
            }

            email += c;
        });

        // =========================
        // BACKSPACE
        // =========================

        Input.keyboard().onKeyReleased(event -> {

            // IGNORA INPUT SE A TELA NÃO ESTIVER ATIVA
            if (!Game.screens().current().getName().equals(getName())) {
                return;
            }

            if (event.getKeyCode() != KeyEvent.VK_BACK_SPACE) {
                return;
            }

            // APAGA 1 CARACTERE POR CLIQUE
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

    // =========================
    // INPUT MOUSE
    // =========================

    private void setupMouseInput() {

        Input.mouse().onMoved(event -> {

            // IGNORA INPUT SE A TELA NÃO ESTIVER ATIVA
            if (!Game.screens().current().getName().equals(getName())) {
                return;
            }

            updateCursor(event.getPoint());
        });

        Input.mouse().onClicked(event -> {

            // IGNORA INPUT SE A TELA NÃO ESTIVER ATIVA
            if (!Game.screens().current().getName().equals(getName())) {
                return;
            }

            Point mousePosition = event.getPoint();

            // =========================
            // VOLTAR
            // =========================

            if (
                    voltarRect != null
                            && voltarRect.contains(mousePosition)
            ) {

                Game.screens().display("login");

                return;
            }

            // =========================
            // INPUT EMAIL
            // =========================

            emailSelecionado = false;

            if (
                    emailRect != null
                            && emailRect.contains(mousePosition)
            ) {

                emailSelecionado = true;
            }

            // =========================
            // CONFIRMAR
            // =========================

            if (
                    confirmarRect != null
                            && confirmarRect.contains(mousePosition)
            ) {

                // =========================
                // VALIDAR E-MAIL
                // =========================

                if (
                        email.isEmpty()
                                || !email.contains("@")
                                || !email.contains(".")
                ) {

                    mensagemValidacao =
                            "Digite um e-mail válido.";

                    emailEnviado = false;

                } else {

                    mensagemValidacao =
                            "E-mail de recuperação enviado.";

                    emailEnviado = true;

                    System.out.println(
                            "E-mail para recuperação: " + email
                    );
                }
            }
        });
    }

    // =========================
    // CURSOR
    // =========================

    private void updateCursor(Point mousePosition) {

        // =========================
        // INPUT → CURSOR TEXTO
        // =========================

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

        // =========================
        // BOTÕES → MÃOZINHA
        // =========================

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

        // =========================
        // CURSOR NORMAL
        // =========================

        Game.window().getRenderComponent().setCursor(
                Cursor.getDefaultCursor()
        );
    }

    // =========================
    // TEXTO COM BORDA
    // =========================

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

    // =========================
    // BOTÃO VOLTAR
    // =========================

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
    // DESENHAR INPUT
    // =========================

    private void drawInputBox(
            Graphics2D g,
            Rectangle rect,
            String texto,
            boolean selecionado
    ) {

        // FUNDO
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

        // BORDA
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

        // TEXTO
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

        // CURSOR
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

        renderBotaoVoltar(g);

        // =========================
        // TÍTULO
        // =========================

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

        // =========================
        // INPUT EMAIL
        // =========================

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

        // =========================
        // MENSAGEM VALIDAÇÃO
        // =========================

        if (!mensagemValidacao.isEmpty()) {

            g.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            20
                    )
            );

            Color mensagemColor;

            if (emailEnviado) {

                mensagemColor =
                        new Color(
                                80,
                                220,
                                120
                        );

            } else {

                mensagemColor =
                        new Color(
                                220,
                                70,
                                70
                        );
            }

            int mensagemX =
                    getCenteredTextX(
                            g,
                            mensagemValidacao,
                            screenWidth / 2
                    );

            int mensagemY = 445;

            drawOutlinedText(
                    g,
                    mensagemValidacao,
                    mensagemX,
                    mensagemY,
                    mensagemColor
            );
        }

        // =========================
        // BOTÃO CONFIRMAR
        // =========================

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
    }
}