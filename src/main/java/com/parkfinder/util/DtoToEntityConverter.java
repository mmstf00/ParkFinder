package com.parkfinder.util;

import com.parkfinder.entity.Marker;
import com.parkfinder.entity.User;
import com.parkfinder.model.dto.MarkerDTO;
import com.parkfinder.model.UpdateRequest;
import com.parkfinder.model.dto.UserDTO;

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
        marker.setDetailedInformation(markerDTO.getDetailedInformation());
        return marker;
    }

    public static Marker getUpdatedMarker(Marker marker, UpdateRequest updateRequest) {
        marker.setAddress(updateRequest.getAddress());
        marker.setDetailedInformation(updateRequest.getDetails());
        marker.setPriceTag(updateRequest.getPrice());
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
