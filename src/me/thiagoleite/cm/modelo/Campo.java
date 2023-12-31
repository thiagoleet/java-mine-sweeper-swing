package me.thiagoleite.cm.modelo;

import java.util.ArrayList;
import java.util.List;
// import java.util.function.BiConsumer;

public class Campo {
    // Flags
    private boolean aberto = false;
    private boolean minado = false;
    private boolean marcado = false;

    // Coordenadas
    private final int linha;
    private final int coluna;

    // Interação com arredores
    private List<Campo> vizinhos = new ArrayList<Campo>();
    // Pode ser usado também um BiConsumer
    private List<CampoObserver> observers = new ArrayList<>();
    // private List<BiConsumer<Campo, CampoEvento>> observers =new ArrayList<>()

    Campo(int linha, int coluna) {
        this.coluna = coluna;
        this.linha = linha;
    }

    public void registrarObservador(CampoObserver observer) {
        observers.add(observer);
    }

    private void notificarObservadores(CampoEvento evento) {
        observers.stream()
                .forEach(obs -> obs.eventoOcorreu(this, evento));
    }

    boolean adicionarVizinho(Campo vizinho) {
        boolean linhaDiferente = linha != vizinho.linha;
        boolean colunaDiferente = coluna != vizinho.coluna;
        boolean diagonal = linhaDiferente && colunaDiferente;

        int deltaLinha = Math.abs(linha - vizinho.linha);
        int deltaColuna = Math.abs(coluna - vizinho.coluna);
        int deltaGeral = deltaColuna + deltaLinha;

        if (deltaGeral == 1 && !diagonal) {
            vizinhos.add(vizinho);
            return true;
        } else if (deltaGeral == 2 && diagonal) {
            vizinhos.add(vizinho);
            return true;
        } else {
            return false;
        }
    }

    public void alternarMarcacao() {
        if (!aberto) {
            marcado = !marcado;

            if(marcado) {
                notificarObservadores(CampoEvento.MARCAR);
            } else {
                notificarObservadores(CampoEvento.DESMARCAR);
            }
        }
    }

    public boolean abrir() {
        if (!aberto && !marcado) {
            if (minado) {
                notificarObservadores(CampoEvento.EXPLODIR);
                return true;
            }
            setAberto(true);

            if (vizinhancaSegura()) {
                // recursividade
                vizinhos.forEach(vizinho -> vizinho.abrir());
            }

            return true;
        } else {
            return false;
        }

    }

    public boolean vizinhancaSegura() {
        return vizinhos.stream()
                .noneMatch(vizinho -> vizinho.minado);
    }

    void minar() {
        minado = true;
    }

    public boolean isMarcado() {
        return marcado;
    }

    public boolean isAberto() {
        return aberto;
    }

    public boolean isFechado() {
        return !isAberto();
    }


    void setAberto(boolean aberto) {
        this.aberto = aberto;

        if(aberto) {
            notificarObservadores(CampoEvento.ABRIR);
        }
    }

    public boolean isMinado() {
        return minado;
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }

    boolean objetivoAlcancado() {
        boolean desvendado = !minado && aberto;
        boolean protegido = minado && marcado;

        return desvendado || protegido;
    }

    public int minasNaVizinhanca() {
        return (int) vizinhos.stream()
                .filter(vizinho -> vizinho.minado)
                .count();
    }

    void reiniciar() {
        aberto = false;
        minado = false;
        marcado = false;

        notificarObservadores(CampoEvento.REINICIAR);
    }
}
