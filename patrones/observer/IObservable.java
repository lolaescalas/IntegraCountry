package patrones.observer;

public interface IObservable {
    void agregar(IObservador o);
    void eliminar(IObservador o);
    void notificar();
}