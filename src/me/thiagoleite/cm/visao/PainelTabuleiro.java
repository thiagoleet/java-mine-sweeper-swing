package me.thiagoleite.cm.visao;

import me.thiagoleite.cm.modelo.Tabuleiro;

import javax.swing.JPanel;
import java.awt.GridLayout;

public class PainelTabuleiro extends JPanel {

    public PainelTabuleiro(Tabuleiro tabuleiro) {
        setLayout(new GridLayout(tabuleiro.getLinhas(), tabuleiro.getColunas()));

        tabuleiro.paraCadaCampo(c -> add(new BotaoCampo(c)));
        tabuleiro.registrarObservador(e -> {
            // TODO: mostrar resultado pro usuário

        });
    }
}
