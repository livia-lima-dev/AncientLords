package org.example;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.gui.screens.Screen;
import de.gurkenlabs.litiengine.input.Input;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class CadastroScreen extends Screen {

    private BufferedImage background;

    private BufferedImage usuarioField;

    private BufferedImage emailField;

    private BufferedImage senhaField;

    private BufferedImage confirmarButton;

    // =========================
    // TEXTOS
    // =========================

    private String username = "";

    private String email = "";

    private String password = "";


    // =========================
    // FONTE MEDIEVAL
    // =========================

    private Font medievalFont;

    // =========================
    // CAMPOS SELECIONADOS
    // =========================

    private boolean typingUsername = false;

    private boolean typingEmail = false;

    private boolean typingPassword = false;

    // =========================
    // ÁREAS CLICÁVEIS
    // =========================

    private Rectangle confirmarRect;

    private Rectangle usernameRect;

    private Rectangle emailRect;

    private Rectangle passwordRect;

    public CadastroScreen() {

        super("cadastro");

        loadImages();

        setupKeyboardInput();

        setupMouseInput();
    }

    // =========================
    // CARREGA IMAGENS
    // =========================

    private void loadImages() {

        try {

            background = javax.imageio.ImageIO.read(
                    getClass().getResourceAsStream(
                            "/assets/cadastro/fundo_tela_cadastro.png"
                    )
            );

            usuarioField = javax.imageio.ImageIO.read(
                    getClass().getResourceAsStream(
                            "/assets/cadastro/ct_usuario.png"
                    )
            );

            emailField = javax.imageio.ImageIO.read(
                    getClass().getResourceAsStream(
                            "/assets/cadastro/ct_email.png"
                    )
            );

            senhaField = javax.imageio.ImageIO.read(
                    getClass().getResourceAsStream(
                            "/assets/cadastro/ct_senha.png"
                    )
            );

            confirmarButton = javax.imageio.ImageIO.read(
                    getClass().getResourceAsStream(
                            "/assets/cadastro/btn_confirmar.png"
                    )
            );

            medievalFont = Font.createFont(
                    Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream(
                            "/assets/fontes/Cinzel-Bold.ttf"
                    )
            );

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // =========================
    // TECLADO
    // =========================

    private void setupKeyboardInput() {

        Input.keyboard().onKeyTyped(event -> {

            char c = event.getKeyChar();

            if (
                    c == KeyEvent.CHAR_UNDEFINED
                            || Character.isISOControl(c)
            ) {
                return;
            }

            // usuário
            if (typingUsername) {

                username += c;
            }

            // email
            else if (typingEmail) {

                email += c;
            }

            // senha
            else if (typingPassword) {

                password += c;
            }
        });

        Input.keyboard().onKeyReleased(event -> {

            // backspace
            if (event.getKeyCode() == KeyEvent.VK_BACK_SPACE) {

                // usuário
                if (typingUsername && username.length() > 0) {

                    username =
                            username.substring(
                                    0,
                                    username.length() - 1
                            );
                }

                // email
                else if (typingEmail && email.length() > 0) {

                    email =
                            email.substring(
                                    0,
                                    email.length() - 1
                            );
                }

                // senha
                else if (typingPassword && password.length() > 0) {

                    password =
                            password.substring(
                                    0,
                                    password.length() - 1
                            );
                }
            }
        });
    }

    // =========================
    // MOUSE
    // =========================

    private void setupMouseInput() {

        Input.mouse().onClicked(event -> {

            Point mousePosition = event.getPoint();

            // =========================
            // CAMPO USUÁRIO
            // =========================

            if (
                    usernameRect != null &&
                            usernameRect.contains(mousePosition)
            ) {

                typingUsername = true;

                typingEmail = false;

                typingPassword = false;
            }

            // =========================
            // CAMPO EMAIL
            // =========================

            else if (
                    emailRect != null &&
                            emailRect.contains(mousePosition)
            ) {

                typingUsername = false;

                typingEmail = true;

                typingPassword = false;
            }

            // =========================
            // CAMPO SENHA
            // =========================

            else if (
                    passwordRect != null &&
                            passwordRect.contains(mousePosition)
            ) {

                typingUsername = false;

                typingEmail = false;

                typingPassword = true;
            }

            // =========================
            // BOTÃO CONFIRMAR
            // =========================

            if (
                    confirmarRect != null &&
                            confirmarRect.contains(mousePosition)
            ) {

                System.out.println(
                        "CLICOU EM CONFIRMAR"
                );

                System.out.println(
                        "USUÁRIO: " + username
                );

                System.out.println(
                        "EMAIL: " + email
                );

                System.out.println(
                        "SENHA: " + password
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

        // borda preta
        g.setColor(Color.BLACK);

        g.drawString(text, x - 1, y - 1);

        g.drawString(text, x + 1, y - 1);

        g.drawString(text, x - 1, y + 1);

        g.drawString(text, x + 1, y + 1);

        // texto branco
        g.setColor(Color.WHITE);

        g.drawString(text, x, y);
    }

    // =========================
    // RENDER
    // =========================

    @Override
    public void render(Graphics2D g) {

        super.render(g);

        int screenWidth = Game.window().getWidth();

        int screenHeight = Game.window().getHeight();

        // fundo
        g.drawImage(
                background,
                0,
                0,
                screenWidth,
                screenHeight,
                null
        );

        // =========================
        // TAMANHOS
        // =========================

        int fieldWidth = 620;

        int fieldHeight = 105;

        int buttonWidth = 420;

        int buttonHeight = 95;

        // =========================
        // POSIÇÕES
        // =========================

        int xField = (screenWidth - fieldWidth) / 2;

        int xButton = (screenWidth - buttonWidth) / 2;

        int spacing = 22;

        int startY = 250;

        // =========================
        // FONTE DOS INPUTS
        // =========================

        Font inputFont =
                new Font(
                        "Arial",
                        Font.BOLD,
                        20
                );

        g.setFont(inputFont);

        FontMetrics metrics =
                g.getFontMetrics();

        // =========================
        // FONTE DOS TÍTULOS
        // =========================

        Font titleFont =
                medievalFont.deriveFont(20f);

        // =========================
        // TÍTULO USUÁRIO
        // =========================

        g.setFont(titleFont);

        String usuarioTitulo = "Usuário";

        FontMetrics titleMetrics =
                g.getFontMetrics();

        int usuarioTituloX =
                (screenWidth -
                        titleMetrics.stringWidth(
                                usuarioTitulo
                        )) / 2;

        int usuarioTituloY =
                startY - 10;

        drawOutlinedText(
                g,
                usuarioTitulo,
                usuarioTituloX,
                usuarioTituloY
        );

        // volta fonte do input
        g.setFont(inputFont);

        metrics = g.getFontMetrics();

        // =========================
        // CAMPO USUÁRIO
        // =========================

        g.drawImage(
                usuarioField,
                xField,
                startY,
                fieldWidth,
                fieldHeight,
                null
        );

        usernameRect =
                new Rectangle(
                        xField,
                        startY,
                        fieldWidth,
                        fieldHeight
                );

        String displayedUsername = username;

        while (
                metrics.stringWidth(displayedUsername)
                        > fieldWidth - 180
        ) {

            displayedUsername =
                    displayedUsername.substring(1);
        }

        int usernameTextX =
                xField +
                        (fieldWidth / 2) -
                        (
                                metrics.stringWidth(displayedUsername)
                                        / 2
                        );

        int usernameTextY =
                startY + (fieldHeight / 2) + 8;

        drawOutlinedText(
                g,
                displayedUsername,
                usernameTextX,
                usernameTextY
        );

        // cursor
        if (typingUsername) {

            int cursorX =
                    usernameTextX +
                            metrics.stringWidth(
                                    displayedUsername
                            ) + 3;

            drawOutlinedText(
                    g,
                    "|",
                    cursorX,
                    usernameTextY
            );
        }

        // =========================
        // CAMPO EMAIL
        // =========================

        int emailY =
                startY + fieldHeight + spacing;

        // título email
        g.setFont(titleFont);

        String emailTitulo = "E-mail";

        titleMetrics =
                g.getFontMetrics();

        int emailTituloX =
                (screenWidth -
                        titleMetrics.stringWidth(
                                emailTitulo
                        )) / 2;

        int emailTituloY =
                emailY - 4;

        drawOutlinedText(
                g,
                emailTitulo,
                emailTituloX,
                emailTituloY
        );

        // volta fonte do input
        g.setFont(inputFont);

        metrics = g.getFontMetrics();

        g.drawImage(
                emailField,
                xField,
                emailY,
                fieldWidth,
                fieldHeight,
                null
        );

        emailRect =
                new Rectangle(
                        xField,
                        emailY,
                        fieldWidth,
                        fieldHeight
                );

        String displayedEmail = email;

        while (
                metrics.stringWidth(displayedEmail)
                        > fieldWidth - 180
        ) {

            displayedEmail =
                    displayedEmail.substring(1);
        }

        int emailTextX =
                xField +
                        (fieldWidth / 2) -
                        (
                                metrics.stringWidth(displayedEmail)
                                        / 2
                        );

        int emailTextY =
                emailY + (fieldHeight / 2) + 8;

        drawOutlinedText(
                g,
                displayedEmail,
                emailTextX,
                emailTextY
        );

        // cursor
        if (typingEmail) {

            int cursorX =
                    emailTextX +
                            metrics.stringWidth(
                                    displayedEmail
                            ) + 3;

            drawOutlinedText(
                    g,
                    "|",
                    cursorX,
                    emailTextY
            );
        }

        // =========================
        // CAMPO SENHA
        // =========================

        int senhaY =
                startY + (fieldHeight + spacing) * 2;

        // título senha
        g.setFont(titleFont);

        String senhaTitulo = "Criar senha";

        titleMetrics =
                g.getFontMetrics();

        int senhaTituloX =
                (screenWidth -
                        titleMetrics.stringWidth(
                                senhaTitulo
                        )) / 2;

        int senhaTituloY =
                senhaY - 4;

        drawOutlinedText(
                g,
                senhaTitulo,
                senhaTituloX,
                senhaTituloY
        );

        // volta fonte do input
        g.setFont(inputFont);

        metrics = g.getFontMetrics();

        g.drawImage(
                senhaField,
                xField,
                senhaY,
                fieldWidth,
                fieldHeight,
                null
        );

        passwordRect =
                new Rectangle(
                        xField,
                        senhaY,
                        fieldWidth,
                        fieldHeight
                );

        String hiddenPassword =
                "*".repeat(password.length());

        String displayedPassword =
                hiddenPassword;

        while (
                metrics.stringWidth(displayedPassword)
                        > fieldWidth - 180
        ) {

            displayedPassword =
                    displayedPassword.substring(1);
        }

        int passwordTextX =
                xField +
                        (fieldWidth / 2) -
                        (
                                metrics.stringWidth(displayedPassword)
                                        / 2
                        );

        int passwordTextY =
                senhaY + (fieldHeight / 2) + 8;

        drawOutlinedText(
                g,
                displayedPassword,
                passwordTextX,
                passwordTextY
        );

        // cursor
        if (typingPassword) {

            int cursorX =
                    passwordTextX +
                            metrics.stringWidth(
                                    displayedPassword
                            ) + 3;

            drawOutlinedText(
                    g,
                    "|",
                    cursorX,
                    passwordTextY
            );
        }

        // =========================
        // BOTÃO CONFIRMAR
        // =========================

        int confirmarY =
                startY + (fieldHeight + spacing) * 3 + 10;

        g.drawImage(
                confirmarButton,
                xButton,
                confirmarY,
                buttonWidth,
                buttonHeight,
                null
        );

        confirmarRect =
                new Rectangle(
                        xButton,
                        confirmarY,
                        buttonWidth,
                        buttonHeight
                );
    }
}