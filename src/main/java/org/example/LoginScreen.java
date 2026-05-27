package org.example;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.gui.screens.Screen;
import de.gurkenlabs.litiengine.input.Input;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class LoginScreen extends Screen {

    private BufferedImage background;

    private BufferedImage logo;

    private BufferedImage usuarioInput;

    private BufferedImage senhaInput;

    private BufferedImage confirmarButton;

    // =========================
    // INPUTS
    // =========================

    private String username = "";

    private String password = "";

    private boolean typingUsername = true;

    private boolean typingPassword = false;

    // =========================
    // ÁREAS CLICÁVEIS
    // =========================

    private Rectangle cadastrarRect;

    private Rectangle esqueceuSenhaRect;

    private Rectangle confirmarRect;

    public LoginScreen() {

        super("login");

        loadImages();

        setupKeyboardInput();

        setupMouseInput();
    }

    // =========================
    // CARREGA IMAGENS
    // =========================

    private void loadImages() {

        try {

            background = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/assets/login/fundo_tela_login.png"
                    )
            );

            logo = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/assets/login/logo.png"
                    )
            );

            usuarioInput = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/assets/login/ct_usuario.png"
                    )
            );

            senhaInput = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/assets/login/ct_senha.png"
                    )
            );

            confirmarButton = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/assets/login/btn_confirmar.png"
                    )
            );

            System.out.println("IMAGENS CARREGADAS!");

        } catch (Exception e) {

            System.out.println("ERRO AO CARREGAR IMAGENS");

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

            // senha
            else if (typingPassword) {

                password += c;
            }
        });

        Input.keyboard().onKeyReleased(event -> {

            // backspace
            if (event.getKeyCode() == KeyEvent.VK_BACK_SPACE) {

                if (typingUsername && username.length() > 0) {

                    username =
                            username.substring(
                                    0,
                                    username.length() - 1
                            );
                }

                else if (typingPassword && password.length() > 0) {

                    password =
                            password.substring(
                                    0,
                                    password.length() - 1
                            );
                }
            }

            // TAB alterna campo
            if (event.getKeyCode() == KeyEvent.VK_TAB) {

                typingUsername = !typingUsername;

                typingPassword = !typingPassword;
            }
        });
    }


    private void setupMouseInput() {

        Input.mouse().onClicked(event -> {

            Point mousePosition = event.getPoint();


            if (
                    esqueceuSenhaRect != null &&
                            esqueceuSenhaRect.contains(mousePosition)
            ) {

                System.out.println(
                        "CLICOU EM ESQUECEU SENHA"
                );
            }


            if (
                    cadastrarRect != null &&
                            cadastrarRect.contains(mousePosition)
            ) {
                Game.screens().display("cadastro");
            }


            if (
                    confirmarRect != null &&
                            confirmarRect.contains(mousePosition)
            ) {

                System.out.println(
                        "CLICOU EM CONFIRMAR"
                );

                if (
                        username.equals("admin") &&
                                password.equals("123")
                ) {

                    Game.screens().display("inicio");

                } else {

                    System.out.println(
                            "Usuário ou senha inválidos"
                    );
                }
            }
        });
    }


    @Override
    public void render(Graphics2D g) {

        super.render(g);

        int screenWidth =
                Game.window().getWidth();

        int screenHeight =
                Game.window().getHeight();

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

        int logoWidth =
                (int) (screenWidth * 0.45);

        int logoHeight =
                (int) (logoWidth * 0.28);

        int inputWidth =
                (int) (screenWidth * 0.34);

        int inputHeight =
                (int) (inputWidth * 0.22);

        int confirmWidth =
                (int) (inputWidth * 0.72);

        int confirmHeight =
                (int) (confirmWidth * 0.22);

        int spacing =
                (int) (screenHeight * 0.025);

        // =========================
        // POSIÇÕES
        // =========================

        int inputX =
                (screenWidth - inputWidth) / 2;

        int confirmX =
                (screenWidth - confirmWidth) / 2;

        int logoX =
                (screenWidth - logoWidth) / 2;

        int logoY =
                (int) (screenHeight * 0.03);

        int startY =
                (int) (screenHeight * 0.24);

        // =========================
        // LOGO
        // =========================

        g.drawImage(
                logo,
                logoX,
                logoY,
                logoWidth,
                logoHeight,
                null
        );

        // =========================
        // INPUT USUÁRIO
        // =========================

        g.drawImage(
                usuarioInput,
                inputX,
                startY,
                inputWidth,
                inputHeight,
                null
        );

        g.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        20
                )
        );

        // limita tamanho do texto
        String displayedUsername = username;

        while (
                g.getFontMetrics().stringWidth(displayedUsername)
                        > inputWidth - 180
        ) {

            displayedUsername =
                    displayedUsername.substring(1);
        }

        // posição centralizada
        int usernameTextX =
                inputX +
                        (inputWidth / 2) -
                        (g.getFontMetrics().stringWidth(displayedUsername) / 2);

        int usernameTextY =
                startY + (inputHeight / 2) + 8;

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
        // INPUT SENHA
        // =========================

        int senhaY =
                startY + inputHeight + spacing;

        g.drawImage(
                senhaInput,
                inputX,
                senhaY,
                inputWidth,
                inputHeight,
                null
        );

        // senha escondida
        String hiddenPassword =
                "*".repeat(password.length());

        // limita tamanho
        String displayedPassword =
                hiddenPassword;

        while (
                g.getFontMetrics().stringWidth(displayedPassword)
                        > inputWidth - 180
        ) {

            displayedPassword =
                    displayedPassword.substring(1);
        }

        // posição centralizada
        int passwordTextX =
                inputX +
                        (inputWidth / 2) -
                        (g.getFontMetrics().stringWidth(displayedPassword) / 2);

        int passwordTextY =
                senhaY + (inputHeight / 2) + 8;

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
        // ESQUECEU SENHA
        // =========================

        int esqueceuY =
                senhaY + inputHeight + 25;

        g.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        20
                )
        );

        String esqueceuTexto =
                "Esqueceu a senha?";

        FontMetrics metrics =
                g.getFontMetrics();

        int esqueceuX =
                (screenWidth -
                        metrics.stringWidth(
                                esqueceuTexto
                        )) / 2;

        // área clicável
        esqueceuSenhaRect =
                new Rectangle(
                        esqueceuX,
                        esqueceuY - 20,
                        metrics.stringWidth(
                                esqueceuTexto
                        ),
                        30
                );

        // borda preta
        g.setColor(Color.BLACK);

        g.drawString(
                esqueceuTexto,
                esqueceuX - 1,
                esqueceuY - 1
        );

        g.drawString(
                esqueceuTexto,
                esqueceuX + 1,
                esqueceuY - 1
        );

        g.drawString(
                esqueceuTexto,
                esqueceuX - 1,
                esqueceuY + 1
        );

        g.drawString(
                esqueceuTexto,
                esqueceuX + 1,
                esqueceuY + 1
        );

        // texto principal
        g.setColor(Color.WHITE);

        g.drawString(
                esqueceuTexto,
                esqueceuX,
                esqueceuY
        );

        // =========================
        // BOTÃO CONFIRMAR
        // =========================

        int confirmarY =
                esqueceuY + 20;

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

        // =========================
        // CADASTRAR-SE
        // =========================

        int cadastroY =
                confirmarY + confirmHeight + 45;

        String cadastroTexto =
                "Cadastrar-se";

        int cadastroX =
                (screenWidth -
                        metrics.stringWidth(
                                cadastroTexto
                        )) / 2;

        // área clicável
        cadastrarRect =
                new Rectangle(
                        cadastroX,
                        cadastroY - 20,
                        metrics.stringWidth(
                                cadastroTexto
                        ),
                        30
                );

        // borda preta
        g.setColor(Color.BLACK);

        g.drawString(
                cadastroTexto,
                cadastroX - 1,
                cadastroY - 1
        );

        g.drawString(
                cadastroTexto,
                cadastroX + 1,
                cadastroY - 1
        );

        g.drawString(
                cadastroTexto,
                cadastroX - 1,
                cadastroY + 1
        );

        g.drawString(
                cadastroTexto,
                cadastroX + 1,
                cadastroY + 1
        );

        // texto principal
        g.setColor(Color.WHITE);

        g.drawString(
                cadastroTexto,
                cadastroX,
                cadastroY
        );
    }
}