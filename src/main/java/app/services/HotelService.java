package app.services;

import app.dao.HotelDAO;
import app.dao.RoomDAO;
import app.dto.HotelDTO;
import app.dto.RoomDTO;
import app.entities.Hotel;
import app.entities.Room;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class HotelService implements HotelServiceInterface {

    private final HotelDAO hotelDAO;
    private final RoomDAO roomDAO;

    public HotelService(HotelDAO hotelDAO, RoomDAO roomDAO) {
        this.hotelDAO = hotelDAO;
        this.roomDAO = roomDAO;
    }

    @Override
    public List<HotelDTO> getAllHotels() {
        return hotelDAO.findAll().stream()
                .map(HotelDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public HotelDTO getHotelById(Long id) {
        Hotel hotel = hotelDAO.findById(id);
        return hotel != null ? HotelDTO.fromEntity(hotel) : null;
    }

    @Override
    public HotelDTO createHotel(HotelDTO hotelDTO) {
        Hotel hotel = hotelDTO.toEntity();
        return HotelDTO.fromEntity(hotelDAO.save(hotel));
    }

    @Override
    public HotelDTO updateHotel(Long id, HotelDTO hotelDTO) {
        Hotel hotel = hotelDAO.findById(id);
        if (hotel == null) return null;
        hotel.setName(hotelDTO.getName());
        hotel.setAddress(hotelDTO.getAddress());
        return HotelDTO.fromEntity(hotelDAO.save(hotel));
    }

    @Override
    public void deleteHotel(Long id) {
        Hotel hotel = hotelDAO.findById(id);
        if (hotel != null) {
            hotelDAO.delete(hotel);
        }
    }

    @Override
    public HotelDTO addRoom(Long hotelId, RoomDTO roomDTO) {
        Hotel hotel = hotelDAO.findById(hotelId);
        if (hotel == null) return null;
        Room room = roomDTO.toEntity(hotel);
        room.setHotel(hotel);
        hotel.getRooms().add(roomDAO.save(room));
        return HotelDTO.fromEntity(hotelDAO.save(hotel));
    }

    @Override
    public HotelDTO removeRoom(Long hotelId, Long roomId) {
        Hotel hotel = hotelDAO.findById(hotelId);
        if (hotel == null) return null;

        Room roomToRemove = hotel.getRooms().stream()
                .filter(room -> room.getId().equals(roomId))
                .findFirst()
                .orElse(null);

        if (roomToRemove != null) {
            hotel.getRooms().remove(roomToRemove);
            roomDAO.delete(roomToRemove);
            hotelDAO.save(hotel);
        }

        return HotelDTO.fromEntity(hotel);
    }

    @Override
    public List<RoomDTO> getRoomsForHotel(Long hotelId) {
        Hotel hotel = hotelDAO.findById(hotelId);
        if (hotel == null) return Collections.emptyList();
        return hotel.getRooms().stream()
                .map(RoomDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
