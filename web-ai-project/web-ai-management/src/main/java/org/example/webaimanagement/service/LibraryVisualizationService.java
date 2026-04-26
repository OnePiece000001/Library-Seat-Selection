package org.example.webaimanagement.service;

import org.example.webaimanagement.pojo.LibraryAllChart;
import org.example.webaimanagement.pojo.LibraryAllMap;
import org.example.webaimanagement.pojo.LibraryBook;

import java.util.List;

public interface LibraryVisualizationService {







    List<LibraryAllChart> getAllDataChart();

    List<LibraryAllMap> getAllMap();
}
