package us.cartisan.spring.reactor.sample.service;

import us.cartisan.spring.reactor.sample.model.Shipment;

public interface ShipmentService {
    void shipmentLocationUpdate(Shipment shipment) throws InterruptedException;
}
