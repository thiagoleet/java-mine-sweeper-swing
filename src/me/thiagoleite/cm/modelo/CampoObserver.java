package me.thiagoleite.cm.modelo;

@FunctionalInterface
public interface CampoObserver {
    public void eventoOcorreu(Campo campo, CampoEvento evento);
}
