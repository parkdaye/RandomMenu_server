package com.boostcamp.randommenu.service;

import com.boostcamp.randommenu.dto.DefaultRes;

public interface MapService {
    DefaultRes getNearPoints(final double lat, final double lon);

    DefaultRes getNearMenu(final double lat, final double lon, final int radius, final int category);
}
