package modelo.solicitudes;

import enums.TipoServicio;
import modelo.abstractas.Solicitud;

public class TareaMantenimiento extends Solicitud {

    private TipoServicio tipoServicio;

    public TipoServicio getTipoServicio() { return tipoServicio; }
    public void setTipoServicio(TipoServicio tipoServicio) { this.tipoServicio = tipoServicio; }
}