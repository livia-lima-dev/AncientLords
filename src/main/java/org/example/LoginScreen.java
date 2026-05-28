package org.example;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.gui.screens.Screen;
import de.gurkenlabs.litiengine.input.Input;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class LoginScreen extends Screen {

    private BufferedImage background;
    private BufferedImage usuarioInput;
    private BufferedImage senhaInput;
    private BufferedImage confirmarButton;

    private String username = "";
    private String password = "";

    private String mensagemPopup = "";
    private boolean mostrarPopup = false;
    private boolean popupSucesso = false;
    private long tempoPopup = 0;

    private Font pixelFont;

    private long lastBlinkTime = 0;
    private boolean showCursor = true;
    private boolean typingUsername = false;
    private boolean typingPassword = false;

    private Rectangle cadastrarRect;
    private Rectangle esqueceuSenhaRect;
    private Rectangle confirmarRect;

    private int screenWidth;
    private int screenHeight;

    private int inputWidth;
    private int inputHeight;
    private int inputX;

    private int confirmWidth;
    private int confirmHeight;
    private int confirmX;

    private int startY;
    private int senhaY;
    private int esqueceuY;
    private int confirmarY;

    private Rectangle usuarioRect;
    private Rectangle senhaRect;

    public LoginScreen() {

        super("login");

        loadImages();

        setupKeyboardInput();

        setupMouseInput();
    }

    @Override
    public void prepare() {

        super.prepare();

        resetarTela();
    }

    private void resetarTela() {

        username = "";
        password = "";

        mensagemPopup = "";
        mostrarPopup = false;
        popupSucesso = false;
        tempoPopup = 0;

        typingUsername = false;
        typingPassword = false;

        showCursor = true;
        lastBlinkTime = System.currentTimeMillis();
    }

    private void loadImages() {

        try {

            background = loadImage("/assets/login/fundo_tela_login.png");

            usuarioInput = loadImage("/assets/login/ct_usuario.png");

            senhaInput = loadImage("/assets/login/ct_senha.png");

            confirmarButton = loadImage("/assets/login/btn_confirmar.png");

            pixelFont = Font.createFont(
                    Font.TRUETYPE_FONT,
                    Objects.requireNonNull(
                            getClass().getResourceAsStream(
                                    "/assets/fontes/Pixel.ttf"
                            )
                    )
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

    private void setupKeyboardInput() {

        Input.keyboard().onKeyTyped(event -> {

            if (!Game.screens().current().getName().equals(getName())) {
                return;
            }

            char c = event.getKeyChar();

            if (Character.isISOControl(c)) {
                return;
            }

            if (typingUsername) {

                username += c;

            } else if (typingPassword) {

                password += c;
            }
        });

        Input.keyboard().onKeyReleased(event -> {

            if (!Game.screens().current().getName().equals(getName())) {
                return;
            }

            if (event.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                apagarUltimoCaractere();
            }

            if (event.getKeyCode() == KeyEvent.VK_TAB) {
                alternarCampo();
            }
        });
    }

    private void apagarUltimoCaractere() {

        if (typingUsername && username.length() > 0) {

            username =
                    username.substring(
                            0,
                            username.length() - 1
                    );

        } else if (typingPassword && password.length() > 0) {

            password =
                    password.substring(
                            0,
                            password.length() - 1
                    );
        }
    }

    private void alternarCampo() {

        typingUsername = !typingUsername;

        typingPassword = !typingPassword;
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
                    usuarioRect != null
                            && usuarioRect.contains(mousePosition)
            ) {

                typingUsername = true;

                typingPassword = false;
            }

            if (
                    senhaRect != null
                            && senhaRect.contains(mousePosition)
            ) {

                typingUsername = false;

                typingPassword = true;
            }

            if (
                    esqueceuSenhaRect != null
                            && esqueceuSenhaRect.contains(mousePosition)
            ) {

                Game.screens().display("esqueceuSenha");
            }

            if (
                    cadastrarRect != null
                            && cadastrarRect.contains(mousePosition)
            ) {

                Game.screens().display("cadastro");
            }

            if (
                    confirmarRect != null
                            && confirmarRect.contains(mousePosition)
            ) {

                validarLogin();
            }
        });
    }

    private void updateCursor(Point mousePosition) {

        if (
                (
                        usuarioRect != null
                                && usuarioRect.contains(mousePosition)
                )
                        ||
                        (
                                senhaRect != null
                                        && senhaRect.contains(mousePosition)
                        )
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
                                cadastrarRect != null
                                        && cadastrarRect.contains(mousePosition)
                        )
                        ||
                        (
                                esqueceuSenhaRect != null
                                        && esqueceuSenhaRect.contains(mousePosition)
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

    private void validarLogin() {

        if (
                username.equals("admin")
                        && password.equals("123")
        ) {

            Game.screens().display("inicio");

        } else {

            mostrarPopup(
                    "Usuário ou senha inválidos",
                    false
            );
        }
    }

    private void mostrarPopup(String mensagem, boolean sucesso) {

        mensagemPopup = mensagem;

        popupSucesso = sucesso;

        mostrarPopup = true;

        tempoPopup = System.currentTimeMillis();
    }

    @Override
    public void render(Graphics2D g) {

        super.render(g);

        if (
                System.currentTimeMillis()
                        - lastBlinkTime > 500
        ) {

            showCursor = !showCursor;

            lastBlinkTime =
                    System.currentTimeMillis();
        }

        calcularMedidas();

        renderBackground(g);

        renderLogo(g);

        renderCampoUsuario(g);

        renderCampoSenha(g);

        renderEsqueceuSenha(g);

        renderBotaoConfirmar(g);

        renderPopup(g);

        renderCadastrar(g);
    }

    private void calcularMedidas() {

        screenWidth =
                Game.window().getWidth();

        screenHeight =
                Game.window().getHeight();

        inputWidth =
                (int) (screenWidth * 0.34);

        inputHeight =
                (int) (inputWidth * 0.22);

        inputX =
                (screenWidth - inputWidth) / 2;

        confirmWidth =
                (int) (inputWidth * 0.72);

        confirmHeight =
                (int) (confirmWidth * 0.22);

        confirmX =
                (screenWidth - confirmWidth) / 2;

        int spacing =
                (int) (screenHeight * 0.025);

        startY =
                (int) (screenHeight * 0.24);

        senhaY =
                startY + inputHeight + spacing;

        esqueceuY =
                senhaY + inputHeight + 25;

        confirmarY =
                esqueceuY + 20;
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

    private void renderCampoUsuario(Graphics2D g) {

        g.drawImage(
                usuarioInput,
                inputX,
                startY,
                inputWidth,
                inputHeight,
                null
        );

        usuarioRect =
                new Rectangle(
                        inputX,
                        startY,
                        inputWidth,
                        inputHeight
                );

        g.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        20
                )
        );

        String displayedUsername =
                limitarTexto(
                        g,
                        username,
                        inputWidth - 180
                );

        int textX =
                inputX + 190;

        int textY =
                startY + (inputHeight / 2) + 8;

        drawOutlinedText(
                g,
                displayedUsername,
                textX,
                textY,
                Color.WHITE
        );

        if (
                typingUsername
                        && showCursor
        ) {

            int cursorX =
                    textX
                            + g.getFontMetrics()
                            .stringWidth(displayedUsername)
                            + 3;

            drawOutlinedText(
                    g,
                    "|",
                    cursorX,
                    textY,
                    Color.WHITE
            );
        }
    }

    private void renderCampoSenha(Graphics2D g) {

        g.drawImage(
                senhaInput,
                inputX,
                senhaY,
                inputWidth,
                inputHeight,
                null
        );

        senhaRect =
                new Rectangle(
                        inputX,
                        senhaY,
                        inputWidth,
                        inputHeight
                );

        g.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        20
                )
        );

        String hiddenPassword =
                "*".repeat(password.length());

        String displayedPassword =
                limitarTexto(
                        g,
                        hiddenPassword,
                        inputWidth - 180
                );

        int textX =
                inputX + 190;

        int textY =
                senhaY + (inputHeight / 2) + 8;

        drawOutlinedText(
                g,
                displayedPassword,
                textX,
                textY,
                Color.WHITE
        );

        if (
                typingPassword
                        && showCursor
        ) {

            int cursorX =
                    textX
                            + g.getFontMetrics()
                            .stringWidth(displayedPassword)
                            + 3;

            drawOutlinedText(
                    g,
                    "|",
                    cursorX,
                    textY,
                    Color.WHITE
            );
        }
    }

    private void renderEsqueceuSenha(Graphics2D g) {

        g.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        20
                )
        );

        String texto =
                "Esqueceu a senha?";

        FontMetrics metrics =
                g.getFontMetrics();

        int x =
                (
                        screenWidth
                                - metrics.stringWidth(texto)
                ) / 2;

        esqueceuSenhaRect =
                new Rectangle(
                        x,
                        esqueceuY - 20,
                        metrics.stringWidth(texto),
                        30
                );

        drawOutlinedText(
                g,
                texto,
                x,
                esqueceuY,
                Color.WHITE
        );
    }

    private void renderBotaoConfirmar(Graphics2D g) {

        g.drawImage(
                confirmarButton,
                confirmX,
                confirmarY,
                confirmWidth,
                confirmHeight,
                null
        );

        confirmarRect =
                new Rectangle(
                        confirmX,
                        confirmarY,
                        confirmWidth,
                        confirmHeight
                );
    }

    private void renderPopup(Graphics2D g) {

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

        int y =
                confirmarY
                        + confirmHeight
                        + 30;

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

    private void renderCadastrar(Graphics2D g) {

        g.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        20
                )
        );

        String texto =
                "Cadastre-se";

        FontMetrics metrics =
                g.getFontMetrics();

        int y =
                confirmarY
                        + confirmHeight
                        + 65;

        int x =
                (
                        screenWidth
                                - metrics.stringWidth(texto)
                ) / 2;

        cadastrarRect =
                new Rectangle(
                        x,
                        y - 20,
                        metrics.stringWidth(texto),
                        30
                );

        drawOutlinedText(
                g,
                texto,
                x,
                y,
                Color.WHITE
        );
    }

    private String limitarTexto(
            Graphics2D g,
            String texto,
            int larguraMaxima
    ) {

        while (
                g.getFontMetrics()
                        .stringWidth(texto)
                        > larguraMaxima
        ) {

            texto =
                    texto.substring(1);
        }

        return texto;
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