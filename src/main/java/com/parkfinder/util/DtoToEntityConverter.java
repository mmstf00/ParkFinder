package com.parkfinder.util;

import com.parkfinder.entity.Marker;
import com.parkfinder.entity.User;
import com.parkfinder.model.MarkerDTO;
import com.parkfinder.model.UserDTO;

public class DtoToEntityConverter {

    private DtoToEntityConverter() {
        throw new UnsupportedOperationException("Cannot create instance of Utility class");
    }

    public static Marker getMarkerEntity(MarkerDTO markerDTO) {
        Marker marker = new Marker();
        marker.setAddress(markerDTO.getAddress());
        marker.setPlaceId(markerDTO.getPlaceId());
        marker.setPriceTag(markerDTO.getPriceTag());
        marker.setLatitude(markerDTO.getLatitude());
        marker.setLongitude(markerDTO.getLongitude());
        marker.setReservable(markerDTO.isReservable());
        return marker;
    }

    public static User getUserEntity(UserDTO userDTO) {
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setRoles(userDTO.getRoles());
        return user;
    }
}
