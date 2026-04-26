package org.example.webaimanagement.service.impl;

import org.example.webaimanagement.mapper.LibraryBookMapper;
import org.example.webaimanagement.mapper.LibraryCoVisualizationllectMapper;
import org.example.webaimanagement.mapper.LibraryCollectMapper;
import org.example.webaimanagement.pojo.LibraryAllChart;
import org.example.webaimanagement.pojo.LibraryAllMap;
import org.example.webaimanagement.pojo.LibraryBook;
import org.example.webaimanagement.service.LibraryCollectService;
import org.example.webaimanagement.service.LibraryVisualizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryVisualizationServiceImpl implements LibraryVisualizationService {
   @Autowired
    private LibraryCoVisualizationllectMapper libraryCoVisualizationllectMapper;


    @Override
    public List<LibraryAllChart> getAllDataChart() {
        return List.of();
    }

    @Override
    public List<LibraryAllMap> getAllMap() {
        return List.of();
    }
}
