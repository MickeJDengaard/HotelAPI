package app.services;

import app.dto.HotelDTO;
import app.dto.RoomDTO;

import java.util.List;

public interface HotelServiceInterface {
    List<HotelDTO> getAllHotels();
    HotelDTO getHotelById(Long id);
    HotelDTO createHotel(HotelDTO hotelDTO);
    HotelDTO updateHotel(Long id, HotelDTO hotelDTO);
    void deleteHotel(Long id);
    HotelDTO addRoom(Long hotelId, RoomDTO roomDTO);
    HotelDTO removeRoom(Long hotelId, Long roomId);
    List<RoomDTO> getRoomsForHotel(Long hotelId);
}
