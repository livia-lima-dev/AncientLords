package org.example;

import de.gurkenlabs.litiengine.Game;

import java.awt.*;

public class Main {

    public static void main(String[] args) {

        Game.init(args);

        Game.window().setTitle("Ancient Lords");

        Game.window()
                .getRenderComponent()
                .setPreferredSize(
                        new Dimension(1280, 720)
                );


        Game.screens().add(
                new LoginScreen()
        );
        Game.screens().add(
                new InicioScreen()
        );
        Game.screens().add(
                new ConfiguracoesScreen()
        );
        Game.screens().add(
                new CadastroScreen()
        );
        Game.screens().add(
                new DerrotaScreen()
        );
        Game.screens().add(
                new VitoriaScreen()
        );
        Game.screens().add(
                new PerfilScreen()
        );

        Game.screens().display("login");

        Game.start();
    }
}