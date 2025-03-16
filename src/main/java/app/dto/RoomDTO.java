package app.dto;

import app.entities.Room;
import app.entities.Hotel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomDTO {
    private Long id;
    private Long hotelId;
    private String number;
    private Double price;

    // Convert from Entity to DTO
    public static RoomDTO fromEntity(Room room) {
        if (room == null) return null;
        Long hotelId = room.getHotel() != null ? room.getHotel().getId() : null;
        return new RoomDTO(room.getId(), hotelId, room.getNumber(), room.getPrice());
    }

    // Convert from DTO to Entity (nu med hotel som parameter)
    public Room toEntity(Hotel hotel) {
        Room room = new Room();
        room.setId(this.id);
        room.setNumber(this.number);
        room.setPrice(this.price);
        room.setHotel(hotel); // Sætter hotellet her i stedet
        return room;
    }
}
