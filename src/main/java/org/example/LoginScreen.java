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
    private BufferedImage logo;
    private BufferedImage usuarioInput;
    private BufferedImage senhaInput;
    private BufferedImage confirmarButton;

    private String username = "";
    private String password = "";
    private String mensagemErro = "";

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

    private void loadImages() {

        try {

            background = loadImage("/assets/login/fundo_tela_login.png");

            logo = loadImage("/assets/login/logo.png");

            usuarioInput = loadImage("/assets/login/ct_usuario.png");

            senhaInput = loadImage("/assets/login/ct_senha.png");

            confirmarButton = loadImage("/assets/login/btn_confirmar.png");

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

            // ========================================
            // IGNORA INPUT SE A TELA NÃO ESTIVER ATIVA
            // ========================================

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

            // ========================================
            // IGNORA INPUT SE A TELA NÃO ESTIVER ATIVA
            // ========================================

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

        Input.mouse().onClicked(event -> {

            // ========================================
            // IGNORA INPUT SE A TELA NÃO ESTIVER ATIVA
            // ========================================

            if (!Game.screens().current().getName().equals(getName())) {
                return;
            }

            Point mousePosition = event.getPoint();

            // ========================================
            // USUÁRIO
            // ========================================

            if (
                    usuarioRect != null
                            && usuarioRect.contains(mousePosition)
            ) {

                typingUsername = true;

                typingPassword = false;
            }

            // ========================================
            // SENHA
            // ========================================

            if (
                    senhaRect != null
                            && senhaRect.contains(mousePosition)
            ) {

                typingUsername = false;

                typingPassword = true;
            }

            // ========================================
            // ESQUECEU SENHA
            // ========================================

            if (
                    esqueceuSenhaRect != null
                            && esqueceuSenhaRect.contains(mousePosition)
            ) {

                System.out.println("CLICOU EM ESQUECEU SENHA");
            }

            // ========================================
            // CADASTRAR
            // ========================================

            if (
                    cadastrarRect != null
                            && cadastrarRect.contains(mousePosition)
            ) {

                mensagemErro = "";

                Game.screens().display("cadastro");
            }

            // ========================================
            // CONFIRMAR
            // ========================================

            if (
                    confirmarRect != null
                            && confirmarRect.contains(mousePosition)
            ) {

                validarLogin();
            }
        });
    }

    private void validarLogin() {

        if (
                username.equals("admin")
                        && password.equals("123")
        ) {

            mensagemErro = "";

            Game.screens().display("inicio");

        } else {

            mensagemErro = "Usuário ou senha inválidos";
        }
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

        renderMensagemErro(g);

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

        int logoWidth =
                (int) (screenWidth * 0.45);

        int logoHeight =
                (int) (logoWidth * 0.28);

        int logoX =
                (screenWidth - logoWidth) / 2;

        int logoY =
                (int) (screenHeight * 0.03);

        g.drawImage(
                logo,
                logoX,
                logoY,
                logoWidth,
                logoHeight,
                null
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

    private void renderMensagemErro(Graphics2D g) {

        if (mensagemErro.isEmpty()) {
            return;
        }

        g.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )
        );

        int x =
                centralizarTextoX(
                        g,
                        mensagemErro,
                        0,
                        screenWidth
                );

        int y =
                confirmarY
                        + confirmHeight
                        + 30;

        g.setColor(Color.BLACK);

        g.drawString(
                mensagemErro,
                x - 1,
                y - 1
        );

        g.drawString(
                mensagemErro,
                x + 1,
                y - 1
        );

        g.drawString(
                mensagemErro,
                x - 1,
                y + 1
        );

        g.drawString(
                mensagemErro,
                x + 1,
                y + 1
        );

        g.setColor(Color.RED);

        g.drawString(
                mensagemErro,
                x,
                y
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
                "Cadastrar-se";

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