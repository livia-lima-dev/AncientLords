package org.example;

import de.gurkenlabs.litiengine.Game;

import java.awt.*;

public class Main {

    public static void main(String[] args) {

        // inicia engine
        Game.init(args);

        // título da janela
        Game.window().setTitle("Ancient Lords");

        // resolução inicial
        Game.window()
                .getRenderComponent()
                .setPreferredSize(
                        new Dimension(1280, 720)
                );

        // adiciona tela de login
        Game.screens().add(
                new ConfiguracoesScreen()
        );

        // exibe tela
        Game.screens().display("login");

        // inicia jogo
        Game.start();
    }
}