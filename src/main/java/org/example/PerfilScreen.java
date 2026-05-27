package org.example;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.gui.screens.Screen;
import de.gurkenlabs.litiengine.input.Input;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class PerfilScreen extends Screen {

    // =========================
    // IMAGEM
    // =========================

    private BufferedImage background;

    // =========================
    // FONTE MEDIEVAL
    // =========================

    private Font medievalFont;

    private Font medievalSmallFont;

    // =========================
    // DADOS DO PERFIL
    // =========================

    private String savedUsername = "Jogador";

    private String editingUsernameText = "Jogador";

    private int nivel = 15;

    private String classeMaisJogada = "Mago";

    private int totalPartidas = 52;

    private int vitorias = 34;

    private int derrotas = 18;

    // =========================
    // CONTROLE
    // =========================

    private boolean editingUsername = false;

    // =========================
    // ÁREAS CLICÁVEIS
    // =========================

    private Rectangle usernameRect;

    private Rectangle salvarRect;

    private Rectangle voltarRect;

    public PerfilScreen() {

        super("perfil");

        loadImages();

        setupKeyboardInput();

        setupMouseInput();
    }

    // =========================
    // CARREGA IMAGEM + FONTE
    // =========================

    private void loadImages() {

        try {

            background = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/assets/perfil/background_2.png"
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

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // =========================
    // TECLADO
    // =========================

    private void setupKeyboardInput() {

        Input.keyboard().onKeyTyped(event -> {

            if (!editingUsername) {
                return;
            }

            char c = event.getKeyChar();

            if (
                    c == KeyEvent.CHAR_UNDEFINED
                            || Character.isISOControl(c)
            ) {
                return;
            }

            editingUsernameText += c;
        });

        Input.keyboard().onKeyPressed(event -> {

            if (!editingUsername) {
                return;
            }

            // backspace
            if (event.getKeyCode() == KeyEvent.VK_BACK_SPACE) {

                if (editingUsernameText.length() > 0) {

                    editingUsernameText =
                            editingUsernameText.substring(
                                    0,
                                    editingUsernameText.length() - 1
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

            // clicar no nome
            if (
                    usernameRect != null &&
                            usernameRect.contains(mousePosition)
            ) {

                editingUsername = true;

                System.out.println(
                        "EDITANDO NOME"
                );
            }

            // salvar alteração
            if (
                    salvarRect != null &&
                            salvarRect.contains(mousePosition)
            ) {

                savedUsername = editingUsernameText;

                editingUsername = false;

                System.out.println(
                        "NOME SALVO: "
                                + savedUsername
                );
            }

            // voltar
            if (
                    voltarRect != null &&
                            voltarRect.contains(mousePosition)
            ) {

                System.out.println(
                        "CLICOU EM VOLTAR"
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

        // =========================
        // CONFIGURAÇÕES TEXTO
        // =========================

        g.setFont(medievalFont);

        FontMetrics metrics =
                g.getFontMetrics();

        int centerX =
                screenWidth / 2;

        int startY = 150;

        int spacing = 55;

        // =========================
        // NOME EDITÁVEL
        // =========================

        String nomeTexto =
                "Nome: " + editingUsernameText;

        int nomeX =
                centerX -
                        (
                                metrics.stringWidth(nomeTexto) / 2
                        );

        int nomeY = startY;

        drawOutlinedText(
                g,
                nomeTexto,
                nomeX,
                nomeY
        );

        // área clicável
        usernameRect =
                new Rectangle(
                        nomeX - 10,
                        nomeY - 35,
                        metrics.stringWidth(nomeTexto) + 20,
                        45
                );

        // cursor visual
        if (editingUsername) {

            int cursorX =
                    nomeX +
                            metrics.stringWidth(nomeTexto)
                            + 3;

            drawOutlinedText(
                    g,
                    "|",
                    cursorX,
                    nomeY
            );
        }

        // =========================
        // BOTÃO SALVAR
        // =========================

        g.setFont(medievalSmallFont);

        String salvarTexto =
                "Salvar alteração";

        FontMetrics salvarMetrics =
                g.getFontMetrics();

        int salvarX =
                centerX -
                        (
                                salvarMetrics.stringWidth(
                                        salvarTexto
                                ) / 2
                        );

        int salvarY =
                nomeY + 60;

        // =========================
        // CAIXA DO BOTÃO
        // =========================

        int buttonPaddingX = 25;

        int boxX =
                salvarX - buttonPaddingX;

        int boxY =
                salvarY - 30;

        int boxWidth =
                salvarMetrics.stringWidth(
                        salvarTexto
                ) + (buttonPaddingX * 2);

        int boxHeight = 45;

        // fundo da caixa
        g.setColor(
                new Color(
                        60,
                        40,
                        20,
                        220
                )
        );

        g.fillRoundRect(
                boxX,
                boxY,
                boxWidth,
                boxHeight,
                15,
                15
        );

        // borda dourada
        g.setColor(
                new Color(
                        180,
                        140,
                        60
                )
        );

        g.setStroke(
                new BasicStroke(3)
        );

        g.drawRoundRect(
                boxX,
                boxY,
                boxWidth,
                boxHeight,
                15,
                15
        );

        // texto
        drawOutlinedText(
                g,
                salvarTexto,
                salvarX,
                salvarY
        );

        // área clicável
        salvarRect =
                new Rectangle(
                        boxX,
                        boxY,
                        boxWidth,
                        boxHeight
                );

        // =========================
        // RESTO DOS DADOS
        // =========================

        g.setFont(medievalFont);

        metrics = g.getFontMetrics();

        // nível
        String nivelTexto =
                "Nível: " + nivel;

        int nivelX =
                centerX -
                        (
                                metrics.stringWidth(
                                        nivelTexto
                                ) / 2
                        );

        drawOutlinedText(
                g,
                nivelTexto,
                nivelX,
                startY + 20 + spacing * 2
        );

        // classe
        String classeTexto =
                "Classe favorita: "
                        + classeMaisJogada;

        int classeX =
                centerX -
                        (
                                metrics.stringWidth(
                                        classeTexto
                                ) / 2
                        );

        drawOutlinedText(
                g,
                classeTexto,
                classeX,
                startY + spacing * 3 + 20
        );

        // partidas
        String partidasTexto =
                "Total de partidas: "
                        + totalPartidas;

        int partidasX =
                centerX -
                        (
                                metrics.stringWidth(
                                        partidasTexto
                                ) / 2
                        );

        drawOutlinedText(
                g,
                partidasTexto,
                partidasX,
                startY + spacing * 4 + 20
        );

        // vitórias
        String vitoriasTexto =
                "Vitórias: " + vitorias;

        int vitoriasX =
                centerX -
                        (
                                metrics.stringWidth(
                                        vitoriasTexto
                                ) / 2
                        );

        drawOutlinedText(
                g,
                vitoriasTexto,
                vitoriasX,
                startY + spacing * 5 + 20
        );

        // derrotas
        String derrotasTexto =
                "Derrotas: " + derrotas;

        int derrotasX =
                centerX -
                        (
                                metrics.stringWidth(
                                        derrotasTexto
                                ) / 2
                        );

        drawOutlinedText(
                g,
                derrotasTexto,
                derrotasX,
                startY + spacing * 6 + 20
        );

        // =========================
        // BOTÃO VOLTAR
        // =========================

        g.setFont(medievalFont.deriveFont(26f));

        String voltarTexto =
                "Voltar";

        FontMetrics voltarMetrics =
                g.getFontMetrics();

        int voltarX =
                centerX -
                        (
                                voltarMetrics.stringWidth(
                                        voltarTexto
                                ) / 2
                        );

        int voltarY =
                screenHeight - 100;

        drawOutlinedText(
                g,
                voltarTexto,
                voltarX,
                voltarY
        );

        voltarRect =
                new Rectangle(
                        voltarX - 10,
                        voltarY - 30,
                        voltarMetrics.stringWidth(
                                voltarTexto
                        ) + 20,
                        40
                );
    }
}