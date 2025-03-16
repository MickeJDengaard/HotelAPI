package app.models;

import app.dto.HotelDTO;
import app.dto.RoomDTO;
import app.services.HotelServiceInterface;
import io.javalin.http.Context;

import java.util.List;

public class HotelController {

    private final HotelServiceInterface hotelService;

    public HotelController(HotelServiceInterface hotelService) {
        this.hotelService = hotelService;
    }

    // GET /hotel
    public void getAllHotels(Context ctx) {
        List<HotelDTO> hotels = hotelService.getAllHotels();
        ctx.json(hotels);
    }

    // GET /hotel/{id}
    public void getHotelById(Context ctx) {
        Long id = Long.parseLong(ctx.pathParam("id"));
        HotelDTO hotelDTO = hotelService.getHotelById(id);
        if (hotelDTO == null) {
            ctx.status(404).result("Hotel not found");
        } else {
            ctx.json(hotelDTO);
        }
    }

    // GET /hotel/{id}/rooms
    public void getRoomsForHotel(Context ctx) {
        Long id = Long.parseLong(ctx.pathParam("id"));
        List<RoomDTO> rooms = hotelService.getRoomsForHotel(id);
        ctx.json(rooms);
    }

    // POST /hotel
    public void createHotel(Context ctx) {
        if (ctx.body() == null || ctx.body().isEmpty()) {
            ctx.status(400).result("Empty request body");
            return;
        }
        HotelDTO hotelDTO = ctx.bodyAsClass(HotelDTO.class);
        HotelDTO createdHotel = hotelService.createHotel(hotelDTO);
        ctx.status(201).json(createdHotel);
    }

    // PUT /hotel/{id}
    public void updateHotel(Context ctx) {
        Long id = Long.parseLong(ctx.pathParam("id"));
        if (ctx.body() == null || ctx.body().isEmpty()) {
            ctx.status(400).result("Empty request body");
            return;
        }
        HotelDTO hotelDTO = ctx.bodyAsClass(HotelDTO.class);
        HotelDTO updatedHotel = hotelService.updateHotel(id, hotelDTO);
        if (updatedHotel == null) {
            ctx.status(404).result("Hotel not found");
        } else {
            ctx.json(updatedHotel);
        }
    }

    // DELETE /hotel/{id}
    public void deleteHotel(Context ctx) {
        Long id = Long.parseLong(ctx.pathParam("id"));
        HotelDTO hotelDTO = hotelService.getHotelById(id);
        if (hotelDTO == null) {
            ctx.status(404).result("Hotel not found");
        } else {
            hotelService.deleteHotel(id);
            ctx.json(hotelDTO);
        }
    }
}
