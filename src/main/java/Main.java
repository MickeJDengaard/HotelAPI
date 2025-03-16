import app.dao.HotelDAO;
import app.dao.RoomDAO;
import app.dto.HotelDTO;
import app.dto.RoomDTO;
import app.models.HotelController;
import app.models.RoomController;
import app.routes.Routes;
import app.security.controllers.AuthController;
import app.security.controllers.UserController;
import app.security.dao.UserDAO;
import app.security.services.AuthService;
import app.security.services.UserService;
import app.services.HotelService;
import app.services.HotelServiceInterface;
import io.javalin.Javalin;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Initialiser DAOs og services
        HotelDAO hotelDAO = new HotelDAO();
        RoomDAO roomDAO = new RoomDAO();
        HotelService hotelService = new HotelService(hotelDAO, roomDAO);
        UserDAO userDAO = new UserDAO();

        // Opret controllers
        HotelController hotelController = new HotelController(hotelService);
        RoomController roomController = new RoomController(hotelService);

        // Start Javalin-server
        Javalin app = Javalin.create().start(7070);

        // Registrer ruter via Routes-klassen
        new Routes(hotelController, roomController).registerRoutes(app);
        UserService userService = new UserService(new UserDAO());
        UserController userController = new UserController(userService);
        AuthService authService = new AuthService(userDAO);
        AuthController authController = new AuthController(authService);


        app.post("/register", userController::register);
        app.post("/login", authController::login);

        // Opret testdata
        /*HotelDTO hotel1 = hotelService.createHotel(new HotelDTO(null, "Grand Hotel", "123 Main Street", List.of()));
        HotelDTO hotel2 = hotelService.createHotel(new HotelDTO(null, "Cozy Inn", "456 Side Street", List.of()));

        if (hotel1 != null) {
            hotelService.addRoom(hotel1.getId(), new RoomDTO(null, null, "Room 1", 500.50));
        }*/

        System.out.println("Server running on http://localhost:7070");
    }
}


