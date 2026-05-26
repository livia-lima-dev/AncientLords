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
    // CAMPOS SELECIONADOS
    // =========================

    private boolean typingUsername = true;

    private boolean typingEmail = false;

    private boolean typingPassword = false;

    // =========================
    // BOTÃO CLICÁVEL
    // =========================

    private Rectangle confirmarRect;

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

            if (Character.isISOControl(c)) {
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

        Input.keyboard().onKeyPressed(event -> {

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

            // TAB alterna campos
            if (event.getKeyCode() == KeyEvent.VK_TAB) {

                // usuário -> email
                if (typingUsername) {

                    typingUsername = false;

                    typingEmail = true;
                }

                // email -> senha
                else if (typingEmail) {

                    typingEmail = false;

                    typingPassword = true;
                }

                // senha -> usuário
                else if (typingPassword) {

                    typingPassword = false;

                    typingUsername = true;
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

        // TEXTO USUÁRIO

        g.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        20
                )
        );

        String displayedUsername = username;

        while (
                g.getFontMetrics().stringWidth(displayedUsername)
                        > fieldWidth - 180
        ) {

            displayedUsername =
                    displayedUsername.substring(1);
        }

        int usernameTextX =
                xField +
                        (fieldWidth / 2) -
                        (
                                g.getFontMetrics()
                                        .stringWidth(displayedUsername)
                                        / 2
                        );

        int usernameTextY =
                startY + (fieldHeight / 2) + 8;

        // borda preta
        g.setColor(Color.BLACK);

        g.drawString(
                displayedUsername,
                usernameTextX - 1,
                usernameTextY - 1
        );

        g.drawString(
                displayedUsername,
                usernameTextX + 1,
                usernameTextY - 1
        );

        g.drawString(
                displayedUsername,
                usernameTextX - 1,
                usernameTextY + 1
        );

        g.drawString(
                displayedUsername,
                usernameTextX + 1,
                usernameTextY + 1
        );

        // texto branco
        g.setColor(Color.WHITE);

        g.drawString(
                displayedUsername,
                usernameTextX,
                usernameTextY
        );

        // =========================
        // CAMPO EMAIL
        // =========================

        int emailY =
                startY + fieldHeight + spacing;

        g.drawImage(
                emailField,
                xField,
                emailY,
                fieldWidth,
                fieldHeight,
                null
        );

        // TEXTO EMAIL

        String displayedEmail = email;

        while (
                g.getFontMetrics().stringWidth(displayedEmail)
                        > fieldWidth - 180
        ) {

            displayedEmail =
                    displayedEmail.substring(1);
        }

        int emailTextX =
                xField +
                        (fieldWidth / 2) -
                        (
                                g.getFontMetrics()
                                        .stringWidth(displayedEmail)
                                        / 2
                        );

        int emailTextY =
                emailY + (fieldHeight / 2) + 8;

        // borda preta
        g.setColor(Color.BLACK);

        g.drawString(
                displayedEmail,
                emailTextX - 1,
                emailTextY - 1
        );

        g.drawString(
                displayedEmail,
                emailTextX + 1,
                emailTextY - 1
        );

        g.drawString(
                displayedEmail,
                emailTextX - 1,
                emailTextY + 1
        );

        g.drawString(
                displayedEmail,
                emailTextX + 1,
                emailTextY + 1
        );

        // texto branco
        g.setColor(Color.WHITE);

        g.drawString(
                displayedEmail,
                emailTextX,
                emailTextY
        );

        // =========================
        // CAMPO SENHA
        // =========================

        int senhaY =
                startY + (fieldHeight + spacing) * 2;

        g.drawImage(
                senhaField,
                xField,
                senhaY,
                fieldWidth,
                fieldHeight,
                null
        );

        // TEXTO SENHA

        String hiddenPassword =
                "*".repeat(password.length());

        String displayedPassword =
                hiddenPassword;

        while (
                g.getFontMetrics().stringWidth(displayedPassword)
                        > fieldWidth - 180
        ) {

            displayedPassword =
                    displayedPassword.substring(1);
        }

        int passwordTextX =
                xField +
                        (fieldWidth / 2) -
                        (
                                g.getFontMetrics()
                                        .stringWidth(displayedPassword)
                                        / 2
                        );

        int passwordTextY =
                senhaY + (fieldHeight / 2) + 8;

        // borda preta
        g.setColor(Color.BLACK);

        g.drawString(
                displayedPassword,
                passwordTextX - 1,
                passwordTextY - 1
        );

        g.drawString(
                displayedPassword,
                passwordTextX + 1,
                passwordTextY - 1
        );

        g.drawString(
                displayedPassword,
                passwordTextX - 1,
                passwordTextY + 1
        );

        g.drawString(
                displayedPassword,
                passwordTextX + 1,
                passwordTextY + 1
        );

        // texto branco
        g.setColor(Color.WHITE);

        g.drawString(
                displayedPassword,
                passwordTextX,
                passwordTextY
        );

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

        // área clicável
        confirmarRect =
                new Rectangle(
                        xButton,
                        confirmarY,
                        buttonWidth,
                        buttonHeight
                );
    }
}