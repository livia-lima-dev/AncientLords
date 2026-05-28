package org.example;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.gui.screens.Screen;
import de.gurkenlabs.litiengine.input.Input;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class ConfiguracoesScreen extends Screen {

    // =========================
    // IMAGENS
    // =========================

    private BufferedImage background;

    private BufferedImage confirmarButtonImage;

    // =========================
    // FONTES
    // =========================

    private Font medievalFont;

    private Font medievalSmallFont;

    // =========================
    // INPUTS SENHA
    // =========================

    private String senhaAtual = "";

    private String novaSenha = "";

    private String confirmarSenha = "";

    // =========================
    // VALIDAÇÃO SENHA
    // =========================

    private boolean senhasDiferentes = false;

    private boolean senhaAlteradaComSucesso = false;

    // =========================
    // CONTROLE INPUT
    // =========================

    private int campoSelecionado = 0;

    /*
        0 = nenhum
        1 = senha atual
        2 = nova senha
        3 = confirmar senha
     */

    // =========================
    // DIFICULDADE
    // =========================

    private String dificuldadeSelecionada = "Fácil";

    // =========================
    // ÁREAS CLICÁVEIS
    // =========================

    private Rectangle facilRect;

    private Rectangle medioRect;

    private Rectangle dificilRect;

    private Rectangle senhaAtualRect;

    private Rectangle novaSenhaRect;

    private Rectangle confirmarSenhaRect;

    private Rectangle confirmarRect;

    private Rectangle voltarRect;

    // =========================
    // POSICIONAMENTO
    // =========================

    private final int leftSectionX = 430;

    private final int rightSectionX = 1030;

    private final int sectionTitleY = 200;

    public ConfiguracoesScreen() {

        super("configuracoes");

        loadAssets();

        setupKeyboardInput();

        setupMouseInput();
    }

    // =========================
    // CARREGAR ASSETS
    // =========================

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

    // =========================
    // INPUT TECLADO
    // =========================

    private void setupKeyboardInput() {

        Input.keyboard().onKeyTyped(event -> {

            // IGNORA INPUT SE A TELA NÃO ESTIVER ATIVA
            if (!Game.screens().current().getName().equals(getName())) {
                return;
            }

            if (campoSelecionado == 0) {
                return;
            }

            char c = event.getKeyChar();

            // =========================
            // IGNORA CARACTERES INVÁLIDOS
            // =========================

            if (
                    c == KeyEvent.CHAR_UNDEFINED
                            || Character.isISOControl(c)
                            || Character.isWhitespace(c)
            ) {
                return;
            }

            // =========================
            // EVITA QUADRADINHOS
            // =========================

            if (
                    !Character.isLetterOrDigit(c)
                            && "@._-!?#$%&*".indexOf(c) == -1
            ) {
                return;
            }

            // =========================
            // CAMPO 1
            // =========================

            if (campoSelecionado == 1) {

                if (senhaAtual.length() >= 18) {
                    return;
                }

                senhaAtual += c;
            }

            // =========================
            // CAMPO 2
            // =========================

            if (campoSelecionado == 2) {

                if (novaSenha.length() >= 18) {
                    return;
                }

                novaSenha += c;
            }

            // =========================
            // CAMPO 3
            // =========================

            if (campoSelecionado == 3) {

                if (confirmarSenha.length() >= 18) {
                    return;
                }

                confirmarSenha += c;
            }
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

            // senha atual
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

            // nova senha
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

            // confirmar senha
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

                Game.screens().display("inicio");

                return;
            }

            // =========================
            // DIFICULDADE
            // =========================

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

            // =========================
            // INPUTS
            // =========================

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

            // =========================
            // CONFIRMAR
            // =========================

            if (
                    confirmarRect != null
                            && confirmarRect.contains(mousePosition)
            ) {

                // =========================
                // VALIDAR SENHAS
                // =========================

                if (
                        !novaSenha.equals(confirmarSenha)
                ) {

                    senhasDiferentes = true;

                    senhaAlteradaComSucesso = false;

                } else {

                    senhasDiferentes = false;

                    senhaAlteradaComSucesso = true;

                    System.out.println("Senha alterada");
                }
            }
        });
    }

    // =========================
    // CURSOR
    // =========================

    private void updateCursor(Point mousePosition) {

        // ========================================
        // INPUTS → CURSOR DE TEXTO
        // ========================================

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

        // ========================================
        // BOTÕES → MÃOZINHA
        // ========================================

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

        // ========================================
        // CURSOR NORMAL
        // ========================================

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
            String titulo,
            String texto,
            boolean selecionado
    ) {

        // título
        g.setFont(
                medievalSmallFont.deriveFont(22f)
        );

        drawOutlinedText(
                g,
                titulo,
                rect.x,
                rect.y - 15
        );

        // fundo
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

        // borda
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

        // texto
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

        // cursor
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

        // fundo
        g.drawImage(
                background,
                0,
                0,
                screenWidth,
                screenHeight,
                null
        );

        renderBotaoVoltar(g);

        // =====================================================
        // DIFICULDADE
        // =====================================================

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

        // fácil
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

        // médio
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

        // difícil
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

        // =====================================================
        // REDEFINIR SENHA
        // =====================================================

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

        // =========================
        // INPUTS
        // =========================

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

        // =========================
        // AVISO SENHAS DIFERENTES
        // =========================

        if (senhasDiferentes) {

            g.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            18
                    )
            );

            g.setColor(
                    new Color(
                            220,
                            60,
                            60
                    )
            );

            String aviso =
                    "As senhas informadas não coincidem.";

            int avisoX =
                    confirmarSenhaRect.x;

            int avisoY =
                    confirmarSenhaRect.y
                            + confirmarSenhaRect.height
                            + 45;

            g.drawString(
                    aviso,
                    avisoX,
                    avisoY
            );
        }

        // =========================
        // AVISO SUCESSO
        // =========================

        if (senhaAlteradaComSucesso) {

            g.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            18
                    )
            );

            g.setColor(
                    new Color(
                            80,
                            220,
                            120
                    )
            );

            String avisoSucesso =
                    "Senha alterada com sucesso.";

            int avisoX =
                    confirmarSenhaRect.x;

            int avisoY =
                    confirmarSenhaRect.y
                            + confirmarSenhaRect.height
                            + 45;

            g.drawString(
                    avisoSucesso,
                    avisoX,
                    avisoY
            );
        }

        // =========================
        // BOTÃO CONFIRMAR
        // =========================

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
    }
}