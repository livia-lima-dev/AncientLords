package org.example;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.graphics.ImageRenderer;
import de.gurkenlabs.litiengine.gui.screens.Screen;
import de.gurkenlabs.litiengine.input.Input;
import de.gurkenlabs.litiengine.resources.Resources;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class MenuScreen extends Screen {

    private BufferedImage background;
    private BufferedImage painelAudio;
    private BufferedImage btnSair;
    private BufferedImage sliderBarra;
    private BufferedImage sliderPreenchido;
    private BufferedImage sliderKnob;
    private BufferedImage iconSomOn;
    private BufferedImage iconSomOff;

    private Font labelFont;
    private Font percentFont;
    private Font voltarFont;

    private Rectangle voltarBounds;
    private Rectangle sairBounds;

    private Rectangle musicaSliderBounds;
    private Rectangle efeitosSliderBounds;

    private Rectangle musicaTrackBounds;
    private Rectangle efeitosTrackBounds;

    private Rectangle musicaMuteBounds;
    private Rectangle efeitosMuteBounds;

    private boolean mouseSobreElemento = false;
    private boolean arrastandoMusica = false;
    private boolean arrastandoEfeitos = false;
    private boolean musicaMutada = false;
    private boolean efeitosMutados = false;

    private int volumeMusica = 80;
    private int volumeEfeitos = 80;

    private static final int PAINEL_W = 720;
    private static final int PAINEL_H = 360;

    private static final int SLIDER_W = 300;
    private static final int SLIDER_H = 200;

    private static final int TRACK_OFFSET_X = 34;
    private static final int TRACK_OFFSET_Y = 90;
    private static final int TRACK_W = 232;
    private static final int TRACK_H = 38;

    private static final int KNOB_W = 60;
    private static final int KNOB_H = 40;

    private static final int ICON_W = 64;
    private static final int ICON_H = 44;

    private static final int BOTAO_SAIR_W = 360;
    private static final int BOTAO_SAIR_H = 110;

    public MenuScreen() {
        super("menu");

        loadImages();
        loadFonts();
        setupInput();
    }

    private void loadImages() {
        background = Resources.images().get("assets/menu/fundo_tela_menu.png");
        painelAudio = Resources.images().get("assets/menu/painel_audio.png");
        btnSair = Resources.images().get("assets/menu/btn_sair.png");
        sliderBarra = Resources.images().get("assets/menu/slider_barra.png");
        sliderPreenchido = Resources.images().get("assets/menu/slider_preenchido.png");
        sliderKnob = Resources.images().get("assets/menu/slider_knob.png");
        iconSomOn = Resources.images().get("assets/menu/icon_som_on.png");
        iconSomOff = Resources.images().get("assets/menu/icon_som_off.png");
    }

    private void loadFonts() {
        try {
            Font cinzelBold = Font.createFont(
                    Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("/assets/fontes/Cinzel-Bold.ttf")
            );

            labelFont = cinzelBold.deriveFont(Font.BOLD, 18f);
            percentFont = cinzelBold.deriveFont(Font.BOLD, 17f);
            voltarFont = cinzelBold.deriveFont(Font.BOLD, 18f);

        } catch (Exception e) {
            labelFont = new Font("Arial", Font.BOLD, 18);
            percentFont = new Font("Arial", Font.BOLD, 17);
            voltarFont = new Font("Arial", Font.BOLD, 18);
        }
    }

    private int getPainelX() {
        int larguraTela = Game.window().getResolution().width;
        return larguraTela / 2 - PAINEL_W / 2;
    }

    private int getPainelY() {
        return 125;
    }

    private void atualizarBounds() {
        int larguraTela = Game.window().getResolution().width;
        int centroX = larguraTela / 2;

        int painelX = getPainelX();
        int painelY = getPainelY();

        int sliderX = painelX + 145;

        musicaSliderBounds = new Rectangle(
                sliderX,
                painelY + 12,
                SLIDER_W,
                SLIDER_H
        );

        efeitosSliderBounds = new Rectangle(
                sliderX,
                painelY + 130,
                SLIDER_W,
                SLIDER_H
        );

        musicaTrackBounds = new Rectangle(
                musicaSliderBounds.x + TRACK_OFFSET_X,
                musicaSliderBounds.y + TRACK_OFFSET_Y,
                TRACK_W,
                TRACK_H
        );

        efeitosTrackBounds = new Rectangle(
                efeitosSliderBounds.x + TRACK_OFFSET_X,
                efeitosSliderBounds.y + TRACK_OFFSET_Y,
                TRACK_W,
                TRACK_H
        );

        int iconX = musicaTrackBounds.x + musicaTrackBounds.width + 20;

        musicaMuteBounds = new Rectangle(
                iconX,
                musicaTrackBounds.y + (musicaTrackBounds.height / 2) - (ICON_H / 2),
                ICON_W,
                ICON_H
        );

        efeitosMuteBounds = new Rectangle(
                iconX,
                efeitosTrackBounds.y + (efeitosTrackBounds.height / 2) - (ICON_H / 2),
                ICON_W,
                ICON_H
        );

        int voltarX = 65;
        int voltarY = 55;

        voltarBounds = new Rectangle(
                voltarX - 10,
                voltarY - 26,
                120,
                34
        );

        int botaoX = centroX - BOTAO_SAIR_W / 2;

        sairBounds = new Rectangle(
                botaoX,
                painelY + PAINEL_H + 45,
                BOTAO_SAIR_W,
                BOTAO_SAIR_H
        );
    }

    private void setupInput() {
        Input.mouse().onPressed(mouseEvent -> {
            atualizarBounds();

            Point mouse = mouseEvent.getPoint();

            if (voltarBounds.contains(mouse)) {
                voltarAoJogo();
            }

            if (sairBounds.contains(mouse)) {
                Game.exit();
            }

            if (musicaMuteBounds.contains(mouse)) {
                musicaMutada = !musicaMutada;
            }

            if (efeitosMuteBounds.contains(mouse)) {
                efeitosMutados = !efeitosMutados;
            }

            if (musicaTrackBounds.contains(mouse)) {
                arrastandoMusica = true;
                atualizarVolumeMusica(mouse.x);
            }

            if (efeitosTrackBounds.contains(mouse)) {
                arrastandoEfeitos = true;
                atualizarVolumeEfeitos(mouse.x);
            }
        });

        Input.mouse().onReleased(mouseEvent -> {
            arrastandoMusica = false;
            arrastandoEfeitos = false;
        });

        Input.mouse().onMoved(mouseEvent -> {
            Point mouse = mouseEvent.getPoint();

            if (arrastandoMusica) {
                atualizarVolumeMusica(mouse.x);
            }

            if (arrastandoEfeitos) {
                atualizarVolumeEfeitos(mouse.x);
            }
        });

        Input.keyboard().onKeyTyped(
                KeyEvent.VK_ESCAPE,
                event -> voltarAoJogo()
        );
    }

    private void voltarAoJogo() {
        System.out.println("Voltar ao jogo");
    }

    private void atualizarArraste() {
        Point mouse = new Point(
                (int) Input.mouse().getLocation().getX(),
                (int) Input.mouse().getLocation().getY()
        );

        if (arrastandoMusica) {
            atualizarVolumeMusica(mouse.x);
        }

        if (arrastandoEfeitos) {
            atualizarVolumeEfeitos(mouse.x);
        }
    }

    private void atualizarVolumeMusica(int mouseX) {
        int relativeX = mouseX - musicaTrackBounds.x;

        volumeMusica = (int) ((relativeX / (double) musicaTrackBounds.width) * 100);
        volumeMusica = Math.max(0, Math.min(100, volumeMusica));
    }

    private void atualizarVolumeEfeitos(int mouseX) {
        int relativeX = mouseX - efeitosTrackBounds.x;

        volumeEfeitos = (int) ((relativeX / (double) efeitosTrackBounds.width) * 100);
        volumeEfeitos = Math.max(0, Math.min(100, volumeEfeitos));
    }

    @Override
    public void render(Graphics2D g) {
        super.render(g);

        atualizarBounds();
        atualizarArraste();

        int larguraTela = Game.window().getResolution().width;
        int alturaTela = Game.window().getResolution().height;

        ImageRenderer.render(g, background, 0, 0);

        g.setColor(new Color(0, 0, 0, 125));
        g.fillRect(0, 0, larguraTela, alturaTela);

        renderBotaoVoltar(g);
        renderPainel(g);
        renderBotoes(g);
        atualizarCursor();
    }

    private void renderBotaoVoltar(Graphics2D g) {
        g.setFont(voltarFont);

        String texto = "‹ VOLTAR";

        FontMetrics metrics = g.getFontMetrics();

        int x = 65;
        int y = 55;

        g.setColor(new Color(0, 0, 0, 140));
        g.drawString(texto, x + 2, y + 2);

        g.setColor(new Color(95, 55, 20));
        g.drawString(texto, x + 1, y + 1);

        g.setColor(Color.WHITE);
        g.drawString(texto, x, y);

        voltarBounds = new Rectangle(
                x - 10,
                y - 26,
                metrics.stringWidth(texto) + 20,
                34
        );
    }

    private void renderPainel(Graphics2D g) {
        int painelX = getPainelX();
        int painelY = getPainelY();

        g.drawImage(
                painelAudio,
                painelX,
                painelY,
                PAINEL_W,
                PAINEL_H,
                null
        );

        desenharTextoCentralizado(
                g,
                "MUSICA",
                musicaSliderBounds.x,
                musicaSliderBounds.y + 70,
                SLIDER_W
        );

        desenharTextoCentralizado(
                g,
                "EFEITOS SONOROS",
                efeitosSliderBounds.x,
                efeitosSliderBounds.y + 70,
                SLIDER_W
        );

        desenharSlider(g, musicaSliderBounds, musicaTrackBounds, volumeMusica);
        desenharSlider(g, efeitosSliderBounds, efeitosTrackBounds, volumeEfeitos);

        desenharIconeAudio(g, musicaMutada, musicaMuteBounds);
        desenharIconeAudio(g, efeitosMutados, efeitosMuteBounds);

        desenharPorcentagem(g, volumeMusica, musicaMuteBounds);
        desenharPorcentagem(g, volumeEfeitos, efeitosMuteBounds);
    }

    private void desenharTextoCentralizado(Graphics2D g, String texto, int x, int y, int largura) {
        g.setFont(labelFont);
        g.setColor(Color.WHITE);

        FontMetrics metrics = g.getFontMetrics();
        int textoX = x + largura / 2 - metrics.stringWidth(texto) / 2;

        g.drawString(texto, textoX, y);
    }

    private void desenharSlider(Graphics2D g, Rectangle sliderBounds, Rectangle trackBounds, int volume) {
        ImageRenderer.render(
                g,
                sliderBarra,
                sliderBounds.x,
                sliderBounds.y
        );

        int filledWidth = (int) ((volume / 100.0) * trackBounds.width);
        filledWidth = Math.max(0, Math.min(trackBounds.width, filledWidth));

        if (filledWidth > 0) {
            int origemX1 = 0;
            int origemY1 = 0;
            int origemX2 = TRACK_OFFSET_X + filledWidth;
            int origemY2 = SLIDER_H;

            int limiteSemKnobDoAsset = SLIDER_W - 45;
            origemX2 = Math.min(origemX2, limiteSemKnobDoAsset);

            int destinoX1 = sliderBounds.x;
            int destinoY1 = sliderBounds.y;
            int destinoX2 = sliderBounds.x + origemX2;
            int destinoY2 = sliderBounds.y + SLIDER_H;

            g.drawImage(
                    sliderPreenchido,
                    destinoX1,
                    destinoY1,
                    destinoX2,
                    destinoY2,
                    origemX1,
                    origemY1,
                    origemX2,
                    origemY2,
                    null
            );
        }

        int knobX = trackBounds.x + filledWidth - (KNOB_W / 2);
        int knobY = sliderBounds.y + 80;

        g.drawImage(
                sliderKnob,
                knobX,
                knobY,
                KNOB_W,
                KNOB_H,
                null
        );
    }

    private void desenharIconeAudio(Graphics2D g, boolean mutado, Rectangle bounds) {
        g.drawImage(
                mutado ? iconSomOff : iconSomOn,
                bounds.x,
                bounds.y,
                ICON_W,
                ICON_H,
                null
        );
    }

    private void desenharPorcentagem(Graphics2D g, int volume, Rectangle iconBounds) {
        g.setFont(percentFont);
        g.setColor(Color.WHITE);

        g.drawString(
                volume + "%",
                iconBounds.x + ICON_W + 4,
                iconBounds.y + 31
        );
    }

    private void renderBotoes(Graphics2D g) {
        desenharBotaoHover(g, btnSair, sairBounds);
    }

    private void desenharBotaoHover(Graphics2D g, BufferedImage image, Rectangle bounds) {
        Point mousePosition = new Point(
                (int) Input.mouse().getLocation().getX(),
                (int) Input.mouse().getLocation().getY()
        );

        boolean hover = bounds.contains(mousePosition);

        if (hover) {
            g.drawImage(
                    image,
                    bounds.x - 5,
                    bounds.y - 5,
                    bounds.width + 10,
                    bounds.height + 10,
                    null
            );
        } else {
            g.drawImage(
                    image,
                    bounds.x,
                    bounds.y,
                    bounds.width,
                    bounds.height,
                    null
            );
        }
    }

    private void atualizarCursor() {
        Point mousePosition = new Point(
                (int) Input.mouse().getLocation().getX(),
                (int) Input.mouse().getLocation().getY()
        );

        boolean sobreElemento =
                voltarBounds.contains(mousePosition)
                        || sairBounds.contains(mousePosition)
                        || musicaTrackBounds.contains(mousePosition)
                        || efeitosTrackBounds.contains(mousePosition)
                        || musicaMuteBounds.contains(mousePosition)
                        || efeitosMuteBounds.contains(mousePosition);

        if (sobreElemento && !mouseSobreElemento) {
            Game.window().getHostControl().setCursor(
                    Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
            );

            mouseSobreElemento = true;
        } else if (!sobreElemento && mouseSobreElemento) {
            Game.window().getHostControl().setCursor(
                    Cursor.getDefaultCursor()
            );

            mouseSobreElemento = false;
        }
    }
}