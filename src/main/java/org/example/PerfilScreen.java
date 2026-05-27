package org.example;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.gui.screens.Screen;
import de.gurkenlabs.litiengine.input.Input;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class PerfilScreen extends Screen {

    private BufferedImage background;

    private Font medievalFont;
    private Font medievalSmallFont;

    private String editingUsernameText = PlayerData.nome;

    private boolean editingUsername = false;

    private boolean mostrarMensagemSalva = false;
    private long tempoMensagemSalva = 0;

    private Rectangle usernameRect;
    private Rectangle salvarRect;
    private Rectangle voltarRect;

    public PerfilScreen() {
        super("perfil");
        loadImages();
        setupKeyboardInput();
        setupMouseInput();
    }

    private void loadImages() {
        try {
            background = ImageIO.read(
                    Objects.requireNonNull(
                            getClass().getResourceAsStream("/assets/perfil/fundo_tela_perfil.png")
                    )
            );

            medievalFont = Font.createFont(
                    Font.TRUETYPE_FONT,
                    Objects.requireNonNull(
                            getClass().getResourceAsStream("/assets/fontes/Cinzel-Bold.ttf")
                    )
            ).deriveFont(30f);

            medievalSmallFont = Font.createFont(
                    Font.TRUETYPE_FONT,
                    Objects.requireNonNull(
                            getClass().getResourceAsStream("/assets/fontes/Cinzel-Regular.ttf")
                    )
            ).deriveFont(24f);

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

            ge.registerFont(medievalFont);
            ge.registerFont(medievalSmallFont);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupKeyboardInput() {
        Input.keyboard().onKeyTyped(event -> {
            if (!Game.screens().current().getName().equals("perfil")) {
                return;
            }

            if (!editingUsername) {
                return;
            }

            char c = event.getKeyChar();

            if (c == KeyEvent.CHAR_UNDEFINED || Character.isISOControl(c)) {
                return;
            }

            if (editingUsernameText.length() < 18) {
                editingUsernameText += c;
            }
        });

        Input.keyboard().onKeyPressed(event -> {
            if (!Game.screens().current().getName().equals("perfil")) {
                return;
            }

            if (!editingUsername) {
                return;
            }

            if (event.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                if (!editingUsernameText.isEmpty()) {
                    editingUsernameText = editingUsernameText.substring(0, editingUsernameText.length() - 1);
                }
            }

            if (event.getKeyCode() == KeyEvent.VK_ENTER) {
                salvarNome();
            }

            if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
                editingUsernameText = PlayerData.nome;
                editingUsername = false;
            }
        });
    }

    private void setupMouseInput() {
        Input.mouse().onClicked(event -> {
            if (!Game.screens().current().getName().equals("perfil")) {
                return;
            }

            Point mousePosition = event.getPoint();

            if (voltarRect != null && voltarRect.contains(mousePosition)) {
                editingUsername = false;
                editingUsernameText = PlayerData.nome;
                Game.screens().display("inicio");
                return;
            }

            if (usernameRect != null && usernameRect.contains(mousePosition)) {
                editingUsername = true;
                editingUsernameText = PlayerData.nome;
                return;
            }

            if (salvarRect != null && salvarRect.contains(mousePosition)) {
                salvarNome();
            }
        });
    }

    private void salvarNome() {
        String nomeDigitado = editingUsernameText.trim();

        if (!nomeDigitado.isEmpty()) {
            PlayerData.nome = nomeDigitado;
        }

        editingUsernameText = PlayerData.nome;
        editingUsername = false;

        mostrarMensagemSalva = true;
        tempoMensagemSalva = System.currentTimeMillis();
    }

    private void drawOutlinedText(Graphics2D g, String text, int x, int y, Color textColor) {
        g.setColor(Color.BLACK);
        g.drawString(text, x - 1, y - 1);
        g.drawString(text, x + 1, y - 1);
        g.drawString(text, x - 1, y + 1);
        g.drawString(text, x + 1, y + 1);

        g.setColor(textColor);
        g.drawString(text, x, y);
    }

    @Override
    public void render(Graphics2D g) {
        super.render(g);

        int screenWidth = Game.window().getWidth();
        int screenHeight = Game.window().getHeight();

        g.drawImage(background, 0, 0, screenWidth, screenHeight, null);

        renderBotaoVoltar(g);
        renderPerfil(g, screenWidth);
        renderMensagemSalva(g, screenWidth);
    }

    private void renderBotaoVoltar(Graphics2D g) {
        g.setFont(medievalFont.deriveFont(22f));

        String texto = "‹ Voltar";

        FontMetrics metrics = g.getFontMetrics();

        int x = 55;
        int y = 75;

        drawOutlinedText(g, texto, x, y, Color.WHITE);

        voltarRect = new Rectangle(
                x - 10,
                y - 30,
                metrics.stringWidth(texto) + 20,
                40
        );
    }

    private void renderPerfil(Graphics2D g, int screenWidth) {
        int centerX = screenWidth / 2;
        int startY = 210;
        int spacing = 48;

        g.setFont(medievalFont);

        FontMetrics metrics = g.getFontMetrics();

        String nomeTexto = "Nome: " + editingUsernameText;

        int nomeX = centerX - metrics.stringWidth(nomeTexto) / 2;
        int nomeY = startY;

        drawOutlinedText(g, nomeTexto, nomeX, nomeY, Color.WHITE);

        usernameRect = new Rectangle(
                nomeX - 10,
                nomeY - 35,
                metrics.stringWidth(nomeTexto) + 20,
                45
        );

        if (editingUsername && (System.currentTimeMillis() / 500) % 2 == 0) {
            int cursorX = nomeX + metrics.stringWidth(nomeTexto) + 5;
            drawOutlinedText(g, "|", cursorX, nomeY, Color.WHITE);
        }

        renderSalvar(g, centerX, nomeY);

        g.setFont(medievalFont);
        metrics = g.getFontMetrics();

        String nivelTexto = "Nível: " + PlayerData.nivel;
        int nivelX = centerX - metrics.stringWidth(nivelTexto) / 2;
        drawOutlinedText(g, nivelTexto, nivelX, startY + 20 + spacing * 2, Color.WHITE);

        String classeTexto = "Classe favorita: " + PlayerData.classeMaisJogada;
        int classeX = centerX - metrics.stringWidth(classeTexto) / 2;
        drawOutlinedText(g, classeTexto, classeX, startY + spacing * 3 + 20, Color.WHITE);

        String partidasTexto = "Total de partidas: " + PlayerData.totalPartidas;
        int partidasX = centerX - metrics.stringWidth(partidasTexto) / 2;
        drawOutlinedText(g, partidasTexto, partidasX, startY + spacing * 4 + 20, Color.WHITE);

        String vitoriasTexto = "Vitórias: " + PlayerData.vitorias;
        int vitoriasX = centerX - metrics.stringWidth(vitoriasTexto) / 2;
        drawOutlinedText(g, vitoriasTexto, vitoriasX, startY + spacing * 5 + 20, Color.WHITE);

        String derrotasTexto = "Derrotas: " + PlayerData.derrotas;
        int derrotasX = centerX - metrics.stringWidth(derrotasTexto) / 2;
        drawOutlinedText(g, derrotasTexto, derrotasX, startY + spacing * 6 + 20, Color.WHITE);
    }

    private void renderSalvar(Graphics2D g, int centerX, int nomeY) {
        g.setFont(medievalSmallFont);

        FontMetrics salvarMetrics = g.getFontMetrics();

        String salvarTexto = "Salvar alteração";

        int salvarX = centerX - salvarMetrics.stringWidth(salvarTexto) / 2;
        int salvarY = nomeY + 60;

        int buttonPaddingX = 25;
        int boxX = salvarX - buttonPaddingX;
        int boxY = salvarY - 30;
        int boxWidth = salvarMetrics.stringWidth(salvarTexto) + buttonPaddingX * 2;
        int boxHeight = 45;

        g.setColor(new Color(60, 40, 20, 220));
        g.fillRoundRect(boxX, boxY, boxWidth, boxHeight, 15, 15);

        g.setColor(new Color(180, 140, 60));
        g.setStroke(new BasicStroke(3));
        g.drawRoundRect(boxX, boxY, boxWidth, boxHeight, 15, 15);

        drawOutlinedText(g, salvarTexto, salvarX, salvarY, Color.WHITE);

        salvarRect = new Rectangle(
                boxX,
                boxY,
                boxWidth,
                boxHeight
        );
    }

    private void renderMensagemSalva(Graphics2D g, int screenWidth) {
        if (!mostrarMensagemSalva) {
            return;
        }

        if (System.currentTimeMillis() - tempoMensagemSalva > 2000) {
            mostrarMensagemSalva = false;
            return;
        }

        g.setFont(medievalSmallFont.deriveFont(20f));

        String mensagem = "Alterações salvas!";

        FontMetrics metrics = g.getFontMetrics();

        int x = (screenWidth - metrics.stringWidth(mensagem)) / 2;
        int y = 620;

        g.setColor(new Color(40, 120, 40, 220));
        g.fillRoundRect(
                x - 20,
                y - 28,
                metrics.stringWidth(mensagem) + 40,
                40,
                15,
                15
        );

        g.setColor(new Color(180, 220, 180));
        g.setStroke(new BasicStroke(2));
        g.drawRoundRect(
                x - 20,
                y - 28,
                metrics.stringWidth(mensagem) + 40,
                40,
                15,
                15
        );

        drawOutlinedText(g, mensagem, x, y, Color.WHITE);
    }
}