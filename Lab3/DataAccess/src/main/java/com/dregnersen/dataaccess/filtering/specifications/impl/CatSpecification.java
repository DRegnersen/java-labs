package com.dregnersen.dataaccess.filtering.specifications.impl;

import com.dregnersen.dataaccess.entities.Cat;
import com.dregnersen.dataaccess.filtering.Filter;
import com.dregnersen.dataaccess.filtering.specifications.FilterableSpecification;

import java.util.List;

public class CatSpecification extends FilterableSpecification<Cat> {
    public CatSpecification(List<Filter> filters) {
        super(filters);
    }
}
