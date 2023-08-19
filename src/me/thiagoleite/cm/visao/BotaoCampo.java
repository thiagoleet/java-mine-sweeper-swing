package me.thiagoleite.cm.visao;

import me.thiagoleite.cm.modelo.Campo;
import me.thiagoleite.cm.modelo.CampoEvento;
import me.thiagoleite.cm.modelo.CampoObserver;

import javax.swing.JButton;
import javax.swing.BorderFactory;
import java.awt.Color;

public class BotaoCampo extends JButton implements CampoObserver {
    private Campo campo;
    private final Color BG_PADRAO = new Color(184,184,184);
    private final Color BG_MARCADO = new Color(8,179,247);
    private final Color BG_EXPLODIR = new Color(189,66,68);
    private final Color TEXTO_VERDE = new Color(0,100,0);
    public BotaoCampo(Campo campo) {
        this.campo = campo;

        setBackground(BG_PADRAO);
        setBorder(BorderFactory.createBevelBorder(0));

        campo.registrarObservador(this);
    }

    @Override
    public void eventoOcorreu(Campo campo, CampoEvento evento) {
        switch (evento) {
            case ABRIR:
                aplicarEstiloAbrir();
                break;
            case MARCAR:
                aplicarEstiloMarcar();
                break;
            case EXPLODIR:
                aplicarEstiloExplodir();
                break;
            default:
                aplicarEstiloPadrao();
                break;
        }
    }

    private void aplicarEstiloPadrao() {
    }

    private void aplicarEstiloExplodir() {
    }

    private void aplicarEstiloMarcar() {
    }

    private void aplicarEstiloAbrir() {
    }
}
