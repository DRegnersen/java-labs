package com.dregnersen.dataaccess.filtering.specifications;

import com.dregnersen.dataaccess.filtering.Filter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public abstract class FilterableSpecification<T> implements Specification<T> {
    private final List<Filter> filters;

    public FilterableSpecification(List<Filter> filters) {
        this.filters = filters;
    }
    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        for (Filter filter : filters) {
            switch (filter.filteringType()) {
                case FIND ->
                        predicates.add(criteriaBuilder.equal(root.get(filter.field()), filter.value()));
                case LIKE ->
                        predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(filter.field())), "%" + filter.value() + "%"));
            }
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
