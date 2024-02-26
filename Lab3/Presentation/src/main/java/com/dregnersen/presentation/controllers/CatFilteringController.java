package com.dregnersen.presentation.controllers;

import com.dregnersen.dataaccess.filtering.Filter;
import com.dregnersen.dataaccess.filtering.FilteringType;

import java.util.ArrayList;
import java.util.List;

public abstract class CatFilteringController {
    protected static List<Filter> manageFilters(String name, String color, String breed, FilteringType filteringType) {
        List<Filter> filters = new ArrayList<>();

        if (name != null) {
            filters.add(new Filter("name", name, filteringType));
        }
        if (color != null) {
            filters.add(new Filter("color", color, filteringType));
        }
        if (breed != null) {
            filters.add(new Filter("breed", breed, filteringType));
        }

        return filters;
    }
}
