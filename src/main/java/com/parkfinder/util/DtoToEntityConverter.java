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
        marker.setPriceTag(markerDTO.getPriceTag());
        marker.setDateFrom(markerDTO.getDateFrom());
        marker.setDateTo(markerDTO.getDateTo());
        marker.setLatitude(markerDTO.getLatitude());
        marker.setLongitude(markerDTO.getLongitude());
        return marker;
    }

    public static User getUserEntity(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setRoles(userDTO.getRoles());
        return user;
    }
}
