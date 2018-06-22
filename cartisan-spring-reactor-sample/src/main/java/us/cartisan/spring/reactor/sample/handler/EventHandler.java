package us.cartisan.spring.reactor.sample.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.bus.Event;
import reactor.fn.Consumer;
import us.cartisan.spring.reactor.sample.model.Shipment;
import us.cartisan.spring.reactor.sample.service.ShipmentService;

@Service
public class EventHandler implements Consumer<Event<Shipment>> {

    private final ShipmentService shipmentService;

    @Autowired
    public EventHandler(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @Override
    public void accept(Event<Shipment> shipmentEvent) {
        Shipment shipment = shipmentEvent.getData();
        try {
            shipmentService.shipmentLocationUpdate(shipment);
        } catch (InterruptedException e) {
            //do something as bad things have happened
        }
    }
}
