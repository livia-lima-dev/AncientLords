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

    // =====================================================
    // IMAGENS
    // =====================================================

    private BufferedImage background;

    private BufferedImage painelAudio;

    private BufferedImage btnContinuar;

    private BufferedImage btnSair;

    private BufferedImage sliderBarra;

    private BufferedImage sliderPreenchido;

    private BufferedImage sliderKnob;

    private BufferedImage iconSomOn;

    private BufferedImage iconSomOff;

    // =====================================================
    // FONTES
    // =====================================================

    private Font titleFont;

    private Font textFont;

    // =====================================================
    // BOTÕES
    // =====================================================

    private Rectangle continuarBounds;

    private Rectangle sairBounds;

    // =====================================================
    // SLIDERS
    // =====================================================

    private Rectangle musicaSliderBounds;

    private Rectangle efeitosSliderBounds;

    // =====================================================
    // ÍCONES SOM
    // =====================================================

    private Rectangle musicaMuteBounds;

    private Rectangle efeitosMuteBounds;

    // =====================================================
    // ESTADOS
    // =====================================================

    private boolean mouseSobreElemento = false;

    private boolean arrastandoMusica = false;

    private boolean arrastandoEfeitos = false;

    private boolean musicaMutada = false;

    private boolean efeitosMutados = false;

    // =====================================================
    // VOLUMES
    // =====================================================

    private int volumeMusica = 80;

    private int volumeEfeitos = 80;

    public MenuScreen() {

        super("menu");

        // =====================================================
        // CARREGAR IMAGENS
        // =====================================================

        this.background =
                Resources.images().get("assets/menu/fundo_tela_menu.png");

        this.painelAudio =
                Resources.images().get("assets/menu/painel_audio.png");

        this.btnContinuar =
                Resources.images().get("assets/menu/btn_continuar.png");

        this.btnSair =
                Resources.images().get("assets/menu/btn_sair.png");

        this.sliderBarra =
                Resources.images().get("assets/menu/slider_barra.png");

        this.sliderPreenchido =
                Resources.images().get("assets/menu/slider_preenchido.png");

        this.sliderKnob =
                Resources.images().get("assets/menu/slider_knob.png");

        this.iconSomOn =
                Resources.images().get("assets/menu/icon_som_on.png");

        this.iconSomOff =
                Resources.images().get("assets/menu/icon_som_off.png");

        // =====================================================
        // FONTES
        // =====================================================

        try {

            this.titleFont = Font.createFont(
                    Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("/assets/fontes/Pixel.ttf")
            ).deriveFont(Font.PLAIN, 72f);

        } catch (Exception e) {

            this.titleFont = new Font(
                    "Arial",
                    Font.BOLD,
                    72
            );
        }

        this.textFont = new Font(
                "Arial",
                Font.BOLD,
                24
        );

        // =====================================================
        // TAMANHO DA TELA
        // =====================================================

        int larguraTela =
                Game.window().getResolution().width;

        // =====================================================
        // BOTÃO CONTINUAR
        // =====================================================

        int continuarX =
                (larguraTela / 2)
                        - (btnContinuar.getWidth() / 2);

        int continuarY = 600;

        continuarBounds = new Rectangle(
                continuarX,
                continuarY,
                btnContinuar.getWidth(),
                btnContinuar.getHeight()
        );

        // =====================================================
        // BOTÃO SAIR
        // =====================================================

        int sairX =
                (larguraTela / 2)
                        - (btnSair.getWidth() / 2);

        int sairY = 720;

        sairBounds = new Rectangle(
                sairX,
                sairY,
                btnSair.getWidth(),
                btnSair.getHeight()
        );

        // =====================================================
        // SLIDER MÚSICA
        // =====================================================

        musicaSliderBounds = new Rectangle(
                490,
                285,
                300,
                200
        );

        // =====================================================
        // SLIDER EFEITOS
        // =====================================================

        efeitosSliderBounds = new Rectangle(
                490,
                425,
                300,
                200
        );

        // =====================================================
        // ÍCONES MUTE
        // =====================================================

        musicaMuteBounds = new Rectangle(
                820,
                300,
                60,
                40
        );

        efeitosMuteBounds = new Rectangle(
                820,
                440,
                60,
                40
        );

        // =====================================================
        // CLICK MOUSE
        // =====================================================

        Input.mouse().onPressed(mouseEvent -> {

            Point mouse =
                    mouseEvent.getPoint();

            // =====================================================
            // CONTINUAR
            // =====================================================

            if (continuarBounds.contains(mouse)) {

                System.out.println("Continuar");

                // FUTURAMENTE:
                // fechar menu e voltar ao jogo
            }

            // =====================================================
            // SAIR
            // =====================================================

            if (sairBounds.contains(mouse)) {

                Game.exit();
            }

            // =====================================================
            // MUTE MÚSICA
            // =====================================================

            if (musicaMuteBounds.contains(mouse)) {

                musicaMutada = !musicaMutada;
            }

            // =====================================================
            // MUTE EFEITOS
            // =====================================================

            if (efeitosMuteBounds.contains(mouse)) {

                efeitosMutados = !efeitosMutados;
            }

            // =====================================================
            // SLIDER MÚSICA
            // =====================================================

            if (musicaSliderBounds.contains(mouse)) {

                arrastandoMusica = true;

                atualizarVolumeMusica(mouse.x);
            }

            // =====================================================
            // SLIDER EFEITOS
            // =====================================================

            if (efeitosSliderBounds.contains(mouse)) {

                arrastandoEfeitos = true;

                atualizarVolumeEfeitos(mouse.x);
            }
        });

        // =====================================================
        // SOLTAR MOUSE
        // =====================================================

        Input.mouse().onReleased(mouseEvent -> {

            arrastandoMusica = false;

            arrastandoEfeitos = false;
        });

        // =====================================================
        // ARRASTAR MOUSE
        // =====================================================

        Input.mouse().onMoved(mouseEvent -> {

            Point mouse =
                    mouseEvent.getPoint();

            if (arrastandoMusica) {

                atualizarVolumeMusica(mouse.x);
            }

            if (arrastandoEfeitos) {

                atualizarVolumeEfeitos(mouse.x);
            }
        });

        // =====================================================
        // ESC
        // =====================================================

        Input.keyboard().onKeyTyped(
                KeyEvent.VK_ESCAPE,
                event -> {

                    System.out.println("Fechar menu");

                    // FUTURAMENTE:
                    // fechar apenas o menu
                }
        );
    }

    // =====================================================
    // VOLUME MÚSICA
    // =====================================================

    private void atualizarVolumeMusica(int mouseX) {

        int relativeX =
                mouseX - musicaSliderBounds.x;

        volumeMusica =
                (int) ((relativeX / 300.0) * 100);

        volumeMusica =
                Math.max(0,
                        Math.min(100, volumeMusica));
    }

    // =====================================================
    // VOLUME EFEITOS
    // =====================================================

    private void atualizarVolumeEfeitos(int mouseX) {

        int relativeX =
                mouseX - efeitosSliderBounds.x;

        volumeEfeitos =
                (int) ((relativeX / 300.0) * 100);

        volumeEfeitos =
                Math.max(0,
                        Math.min(100, volumeEfeitos));
    }

    @Override
    public void render(Graphics2D g) {

        super.render(g);

        // =====================================================
        // BACKGROUND
        // =====================================================

        ImageRenderer.render(
                g,
                background,
                0,
                0
        );

        // =====================================================
        // OVERLAY ESCURO
        // =====================================================

        g.setColor(
                new Color(0, 0, 0, 140)
        );

        g.fillRect(
                0,
                0,
                Game.window().getResolution().width,
                Game.window().getResolution().height
        );

        // =====================================================
        // PAINEL
        // =====================================================

        ImageRenderer.render(
                g,
                painelAudio,
                390,
                210
        );

        // =====================================================
        // TÍTULO
        // =====================================================

        g.setFont(titleFont);

        g.setColor(Color.WHITE);

        String titulo = "MENU";

        FontMetrics metrics =
                g.getFontMetrics(titleFont);

        int tituloX =
                (Game.window().getResolution().width / 2)
                        - (metrics.stringWidth(titulo) / 2);

        g.drawString(
                titulo,
                tituloX,
                120
        );

        // =====================================================
        // FONTES
        // =====================================================

        g.setFont(textFont);

        // =====================================================
        // TEXTO MÚSICA
        // =====================================================

        g.drawString(
                "Musica",
                505,
                285
        );

        // =====================================================
        // TEXTO EFEITOS
        // =====================================================

        g.drawString(
                "Efeitos Sonoros",
                505,
                425
        );

        // =====================================================
        // SLIDER MÚSICA
        // =====================================================

        desenharSlider(
                g,
                musicaSliderBounds,
                volumeMusica
        );

        // =====================================================
        // SLIDER EFEITOS
        // =====================================================

        desenharSlider(
                g,
                efeitosSliderBounds,
                volumeEfeitos
        );

        // =====================================================
        // ÍCONE MÚSICA
        // =====================================================

        ImageRenderer.render(
                g,
                musicaMutada
                        ? iconSomOff
                        : iconSomOn,
                musicaMuteBounds.x,
                musicaMuteBounds.y
        );

        // =====================================================
        // ÍCONE EFEITOS
        // =====================================================

        ImageRenderer.render(
                g,
                efeitosMutados
                        ? iconSomOff
                        : iconSomOn,
                efeitosMuteBounds.x,
                efeitosMuteBounds.y
        );

        // =====================================================
        // PORCENTAGENS
        // =====================================================

        g.drawString(
                volumeMusica + "%",
                930,
                340
        );

        g.drawString(
                volumeEfeitos + "%",
                930,
                480
        );

        // =====================================================
        // BOTÃO CONTINUAR
        // =====================================================

        desenharBotaoHover(
                g,
                btnContinuar,
                continuarBounds
        );

        // =====================================================
        // BOTÃO SAIR
        // =====================================================

        desenharBotaoHover(
                g,
                btnSair,
                sairBounds
        );

        // =====================================================
        // CURSOR
        // =====================================================

        Point mousePosition = new Point(
                (int) Input.mouse().getLocation().getX(),
                (int) Input.mouse().getLocation().getY()
        );

        boolean sobreElemento =
                continuarBounds.contains(mousePosition)
                        || sairBounds.contains(mousePosition)
                        || musicaSliderBounds.contains(mousePosition)
                        || efeitosSliderBounds.contains(mousePosition)
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

    // =====================================================
    // DESENHAR SLIDER
    // =====================================================

    private void desenharSlider(
            Graphics2D g,
            Rectangle bounds,
            int volume
    ) {

        // =====================================================
        // BARRA VAZIA
        // =====================================================

        ImageRenderer.render(
                g,
                sliderBarra,
                bounds.x,
                bounds.y
        );

        // =====================================================
        // PARTE PREENCHIDA
        // =====================================================

        int filledWidth =
                (int) ((volume / 100.0)
                        * bounds.width);

        g.drawImage(
                sliderPreenchido,

                bounds.x,
                bounds.y,

                bounds.x + filledWidth,
                bounds.y + bounds.height,

                0,
                0,

                filledWidth,
                bounds.height,

                null
        );

        // =====================================================
        // KNOB
        // =====================================================

        int knobX =
                bounds.x
                        + filledWidth
                        - (sliderKnob.getWidth() / 2);

        int knobY =
                bounds.y
                        + 80;

        ImageRenderer.render(
                g,
                sliderKnob,
                knobX,
                knobY
        );
    }

    // =====================================================
    // HOVER BOTÃO
    // =====================================================

    private void desenharBotaoHover(
            Graphics2D g,
            BufferedImage image,
            Rectangle bounds
    ) {

        Point mousePosition = new Point(
                (int) Input.mouse().getLocation().getX(),
                (int) Input.mouse().getLocation().getY()
        );

        boolean hover =
                bounds.contains(mousePosition);

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

            ImageRenderer.render(
                    g,
                    image,
                    bounds.x,
                    bounds.y
            );
        }
    }
}