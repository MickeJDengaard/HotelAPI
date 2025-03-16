package app.routes;


import app.models.HotelController;
import app.models.RoomController;
import io.javalin.apibuilder.ApiBuilder;
import io.javalin.Javalin;

public class Routes {

    private final HotelController hotelController;
    private final RoomController roomController;

    public Routes(HotelController hotelController, RoomController roomController) {
        this.hotelController = hotelController;
        this.roomController = roomController;
    }

    public void registerRoutes(Javalin app) {
        app.routes(() -> {
            ApiBuilder.path("hotel", () -> {
                ApiBuilder.get(hotelController::getAllHotels);
                ApiBuilder.post(hotelController::createHotel);
                ApiBuilder.path("{id}", () -> {
                    ApiBuilder.get(hotelController::getHotelById);
                    ApiBuilder.put(hotelController::updateHotel);
                    ApiBuilder.delete(hotelController::deleteHotel);
                    ApiBuilder.path("rooms", () -> {
                        ApiBuilder.get(hotelController::getRoomsForHotel);
                        ApiBuilder.post(roomController::addRoom);
                    });
                });
            });
            ApiBuilder.path("hotel/{hotelId}/rooms/{roomId}", () ->
                    ApiBuilder.delete(roomController::removeRoom)
            );
        });
    }
}
