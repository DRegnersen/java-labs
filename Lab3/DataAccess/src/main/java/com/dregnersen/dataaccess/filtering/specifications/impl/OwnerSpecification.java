package com.dregnersen.dataaccess.filtering.specifications.impl;

import com.dregnersen.dataaccess.entities.Owner;
import com.dregnersen.dataaccess.filtering.Filter;
import com.dregnersen.dataaccess.filtering.specifications.FilterableSpecification;

import java.util.List;

public class OwnerSpecification extends FilterableSpecification<Owner> {

    public OwnerSpecification(List<Filter> filters) {
        super(filters);
    }
}
