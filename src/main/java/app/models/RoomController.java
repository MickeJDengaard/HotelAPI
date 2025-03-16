package app.models;

import app.dto.HotelDTO;
import app.dto.RoomDTO;
import app.services.HotelServiceInterface;
import io.javalin.http.Context;

public class RoomController {

    private final HotelServiceInterface hotelService;

    public RoomController(HotelServiceInterface hotelService) {
        this.hotelService = hotelService;
    }

    // Eksempel: Tilføj et værelse til et hotel
    // Forventet endpoint: POST /hotel/{hotelId}/rooms
    public void addRoom(Context ctx) {
        Long hotelId = Long.parseLong(ctx.pathParam("hotelId"));
        if (ctx.body() == null || ctx.body().isEmpty()) {
            ctx.status(400).result("Empty request body");
            return;
        }
        RoomDTO roomDTO = ctx.bodyAsClass(RoomDTO.class);
        HotelDTO updatedHotel = hotelService.addRoom(hotelId, roomDTO);
        if (updatedHotel == null) {
            ctx.status(404).result("Hotel not found");
        } else {
            ctx.status(201).json(updatedHotel);
        }
    }

    // Eksempel: Fjern et værelse fra et hotel
    // Forventet endpoint: DELETE /hotel/{hotelId}/rooms/{roomId}
    public void removeRoom(Context ctx) {
        Long hotelId = Long.parseLong(ctx.pathParam("hotelId"));
        Long roomId = Long.parseLong(ctx.pathParam("roomId"));
        HotelDTO updatedHotel = hotelService.removeRoom(hotelId, roomId);
        if (updatedHotel == null) {
            ctx.status(404).result("Hotel or room not found");
        } else {
            ctx.json(updatedHotel);
        }
    }
}
