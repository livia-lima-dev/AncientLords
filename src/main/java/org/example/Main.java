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

        // =========================
        // CRIAR TELAS UMA ÚNICA VEZ
        // =========================

        LoginScreen loginScreen =
                new LoginScreen();

        InicioScreen inicioScreen =
                new InicioScreen();

        ConfiguracoesScreen configuracoesScreen =
                new ConfiguracoesScreen();

        CadastroScreen cadastroScreen =
                new CadastroScreen();

        DerrotaScreen derrotaScreen =
                new DerrotaScreen();

        VitoriaScreen vitoriaScreen =
                new VitoriaScreen();

        PerfilScreen perfilScreen =
                new PerfilScreen();

        EsqueceuSenhaScreen esqueceuSenhaScreen =
                new EsqueceuSenhaScreen();

        MenuScreen menuScreen =
                new MenuScreen();

        // =========================
        // REGISTRAR TELAS
        // =========================

        Game.screens().add(loginScreen);

        Game.screens().add(inicioScreen);

        Game.screens().add(configuracoesScreen);

        Game.screens().add(cadastroScreen);

        Game.screens().add(derrotaScreen);

        Game.screens().add(vitoriaScreen);

        Game.screens().add(perfilScreen);

        Game.screens().add(esqueceuSenhaScreen);

        Game.screens().add(menuScreen);

        // =========================
        // PRIMEIRA TELA
        // =========================

        Game.screens().display("menu");

        Game.start();
    }
}