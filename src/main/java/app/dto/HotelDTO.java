package app.dto;

import app.entities.Hotel;
import app.entities.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelDTO {
    private Long id;
    private String name;
    private String address;
    private List<RoomDTO> rooms;

    // Convert from Entity to DTO
    public static HotelDTO fromEntity(Hotel hotel) {
        if (hotel == null) return null;
        List<RoomDTO> roomDTOs = hotel.getRooms() != null
                ? hotel.getRooms().stream().map(RoomDTO::fromEntity).collect(Collectors.toList())
                : List.of();
        return new HotelDTO(hotel.getId(), hotel.getName(), hotel.getAddress(), roomDTOs);
    }

    // Convert from DTO to Entity
    public Hotel toEntity() {
        Hotel hotel = new Hotel();
        hotel.setId(this.id);
        hotel.setName(this.name);
        hotel.setAddress(this.address);

        if (this.rooms != null) {
            List<Room> roomEntities = this.rooms.stream()
                    .map(roomDTO -> roomDTO.toEntity(hotel)) // Flyttet logik til RoomDTO
                    .collect(Collectors.toList());
            hotel.setRooms(roomEntities);
        }

        return hotel;
    }
}
