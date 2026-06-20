package tests;

import patrones.strategy.CanalEmail;
import patrones.strategy.CanalInterno;
import patrones.strategy.CanalSMS;
import patrones.strategy.Notificacion;

public class StrategyTest {

    public static void main(String[] args) {


        Notificacion notificacion =
                new Notificacion(new CanalEmail());

        System.out.println();
        System.out.println("Usando estrategia EMAIL:");

        notificacion.actualizar(
                "Su reclamo fue registrado.");

        System.out.println();
        System.out.println("Cambiando estrategia a SMS:");

        notificacion.setCanal(
                new CanalSMS());

        notificacion.actualizar(
                "Su reclamo esta en curso.");

        System.out.println();
        System.out.println("Cambiando estrategia a SISTEMA INTERNO:");

        notificacion.setCanal(
                new CanalInterno());

        notificacion.actualizar(
                "Su reclamo fue resuelto.");

        System.out.println();
        System.out.println("Ultimo mensaje almacenado:");
        System.out.println(notificacion.getMensaje());

        System.out.println();
        System.out.println("Fecha:");
        System.out.println(notificacion.getFecha());

    }
}