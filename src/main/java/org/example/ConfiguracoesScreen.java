package org.example;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.gui.screens.Screen;
import de.gurkenlabs.litiengine.input.Input;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class ConfiguracoesScreen extends Screen {

    private BufferedImage background;

    private BufferedImage confirmarButtonImage;

    private Font medievalFont;

    private Font medievalSmallFont;

    private String senhaAtual = "";

    private String novaSenha = "";

    private String confirmarSenha = "";

    private String mensagemPopup = "";

    private boolean mostrarPopup = false;

    private boolean popupSucesso = false;

    private long tempoPopup = 0;

    private int campoSelecionado = 0;

    /*
        0 = nenhum
        1 = senha atual
        2 = nova senha
        3 = confirmar senha
     */

    private String dificuldadeSelecionada = "Fácil";

    private Rectangle facilRect;

    private Rectangle medioRect;

    private Rectangle dificilRect;

    private Rectangle senhaAtualRect;

    private Rectangle novaSenhaRect;

    private Rectangle confirmarSenhaRect;

    private Rectangle confirmarRect;

    private Rectangle voltarRect;

    private final int leftSectionX = 430;

    private final int rightSectionX = 1030;

    private final int sectionTitleY = 200;

    public ConfiguracoesScreen() {

        super("configuracoes");

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

        senhaAtual = "";
        novaSenha = "";
        confirmarSenha = "";

        mensagemPopup = "";
        mostrarPopup = false;
        popupSucesso = false;
        tempoPopup = 0;

        campoSelecionado = 0;
    }

    private void loadAssets() {

        try {

            background = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/assets/configuracoes/fundo_tela_configuracoes.png"
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

    private void setupKeyboardInput() {

        Input.keyboard().onKeyTyped(event -> {

            if (!Game.screens().current().getName().equals(getName())) {
                return;
            }

            if (campoSelecionado == 0) {
                return;
            }

            char c = event.getKeyChar();

            if (
                    c == KeyEvent.CHAR_UNDEFINED
                            || Character.isISOControl(c)
                            || Character.isWhitespace(c)
            ) {
                return;
            }

            if (
                    !Character.isLetterOrDigit(c)
                            && "@._-!?#$%&*".indexOf(c) == -1
            ) {
                return;
            }

            if (campoSelecionado == 1) {

                if (senhaAtual.length() >= 18) {
                    return;
                }

                senhaAtual += c;
            }

            if (campoSelecionado == 2) {

                if (novaSenha.length() >= 18) {
                    return;
                }

                novaSenha += c;
            }

            if (campoSelecionado == 3) {

                if (confirmarSenha.length() >= 18) {
                    return;
                }

                confirmarSenha += c;
            }
        });

        Input.keyboard().onKeyReleased(event -> {

            if (!Game.screens().current().getName().equals(getName())) {
                return;
            }

            if (event.getKeyCode() != KeyEvent.VK_BACK_SPACE) {
                return;
            }

            if (
                    campoSelecionado == 1
                            && senhaAtual.length() > 0
            ) {

                senhaAtual =
                        senhaAtual.substring(
                                0,
                                senhaAtual.length() - 1
                        );
            }

            if (
                    campoSelecionado == 2
                            && novaSenha.length() > 0
            ) {

                novaSenha =
                        novaSenha.substring(
                                0,
                                novaSenha.length() - 1
                        );
            }

            if (
                    campoSelecionado == 3
                            && confirmarSenha.length() > 0
            ) {

                confirmarSenha =
                        confirmarSenha.substring(
                                0,
                                confirmarSenha.length() - 1
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

                Game.screens().display("inicio");

                return;
            }

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

            campoSelecionado = 0;

            if (
                    senhaAtualRect != null
                            && senhaAtualRect.contains(mousePosition)
            ) {

                campoSelecionado = 1;
            }

            if (
                    novaSenhaRect != null
                            && novaSenhaRect.contains(mousePosition)
            ) {

                campoSelecionado = 2;
            }

            if (
                    confirmarSenhaRect != null
                            && confirmarSenhaRect.contains(mousePosition)
            ) {

                campoSelecionado = 3;
            }

            if (
                    confirmarRect != null
                            && confirmarRect.contains(mousePosition)
            ) {

                validarSenha();
            }
        });
    }

    private void validarSenha() {

        if (
                senhaAtual.trim().isEmpty()
                        || novaSenha.trim().isEmpty()
                        || confirmarSenha.trim().isEmpty()
        ) {

            mostrarPopup(
                    "Preencha todos os campos",
                    false
            );

            return;
        }

        if (!novaSenha.equals(confirmarSenha)) {

            mostrarPopup(
                    "As senhas informadas não coincidem.",
                    false
            );

            return;
        }

        mostrarPopup(
                "Senha alterada com sucesso.",
                true
        );

        System.out.println("Senha alterada");
    }

    private void mostrarPopup(String mensagem, boolean sucesso) {

        mensagemPopup = mensagem;

        popupSucesso = sucesso;

        mostrarPopup = true;

        tempoPopup = System.currentTimeMillis();
    }

    private void updateCursor(Point mousePosition) {

        if (
                (
                        senhaAtualRect != null
                                && senhaAtualRect.contains(mousePosition)
                )
                        ||
                        (
                                novaSenhaRect != null
                                        && novaSenhaRect.contains(mousePosition)
                        )
                        ||
                        (
                                confirmarSenhaRect != null
                                        && confirmarSenhaRect.contains(mousePosition)
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
                        facilRect != null
                                && facilRect.contains(mousePosition)
                )
                        ||
                        (
                                medioRect != null
                                        && medioRect.contains(mousePosition)
                        )
                        ||
                        (
                                dificilRect != null
                                        && dificilRect.contains(mousePosition)
                        )
                        ||
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
            int y
    ) {

        g.setColor(Color.BLACK);

        g.drawString(text, x - 1, y - 1);

        g.drawString(text, x + 1, y - 1);

        g.drawString(text, x - 1, y + 1);

        g.drawString(text, x + 1, y + 1);

        g.setColor(Color.WHITE);

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
                y
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
            String titulo,
            String texto,
            boolean selecionado
    ) {

        g.setFont(
                medievalSmallFont.deriveFont(22f)
        );

        drawOutlinedText(
                g,
                titulo,
                rect.x,
                rect.y - 15
        );

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
                textY
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
                    textY
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

        g.setFont(
                medievalSmallFont.deriveFont(28f)
        );

        int optionStartY = 320;

        int optionSpacing = 95;

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

        int inputWidth = 520;

        int inputHeight = 60;

        int inputX =
                rightSectionX - (inputWidth / 2);

        int firstInputY = 270;

        int inputSpacing = 110;

        senhaAtualRect =
                new Rectangle(
                        inputX,
                        firstInputY,
                        inputWidth,
                        inputHeight
                );

        novaSenhaRect =
                new Rectangle(
                        inputX,
                        firstInputY + inputSpacing,
                        inputWidth,
                        inputHeight
                );

        confirmarSenhaRect =
                new Rectangle(
                        inputX,
                        firstInputY + (inputSpacing * 2),
                        inputWidth,
                        inputHeight
                );

        drawInputBox(
                g,
                senhaAtualRect,
                "Senha atual",
                senhaAtual,
                campoSelecionado == 1
        );

        drawInputBox(
                g,
                novaSenhaRect,
                "Nova senha",
                novaSenha,
                campoSelecionado == 2
        );

        drawInputBox(
                g,
                confirmarSenhaRect,
                "Confirmar nova senha",
                confirmarSenha,
                campoSelecionado == 3
        );

        int confirmarWidth = 240;

        int confirmarHeight = 80;

        int confirmarX =
                rightSectionX - (confirmarWidth / 2);

        int confirmarY = 620;

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

        int popupCenterX =
                confirmarSenhaRect.x
                        + confirmarSenhaRect.width / 2;

        int x =
                popupCenterX
                        - metrics.stringWidth(mensagemPopup) / 2;

        int y =
                confirmarSenhaRect.y
                        + confirmarSenhaRect.height
                        + 45;

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
                y
        );
    }
}