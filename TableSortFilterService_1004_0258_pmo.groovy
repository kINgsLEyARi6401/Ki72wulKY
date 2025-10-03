// 代码生成时间: 2025-10-04 02:58:24
package com.example.service

import grails.transaction.Transactional
import groovy.transform.TypeCheckingMode

/**
 * Service class to handle table sorting and filtering functionality.
 *
 * @author Your Name
 * @since 1.0
 */
@Transactional
class TableSortFilterService {

    /**
     * Sorts a list of domain objects based on the specified column and direction.
     *
     * @param domainClass The class of the domain objects.
     * @param list The list of domain objects to sort.
     * @param sortColumn The column to sort by.
     * @param sortOrder The direction of the sort ('asc' for ascending, 'desc' for descending).
     * @return A sorted list of domain objects.
     */
    List sortList(Class domainClass, List list, String sortColumn, String sortOrder) {
        if (!list) {
            throw new IllegalArgumentException('List cannot be null.')
        }
        if (!sortColumn) {
            throw new IllegalArgumentException('Sort column cannot be null or empty.')
        }
        if (!sortOrder || !(sortOrder.equalsIgnoreCase('asc') || sortOrder.equalsIgnoreCase('desc'))) {
            throw new IllegalArgumentException("Sort order must be 'asc' or 'desc'.")
        }

        def sortedList = list.sort { a, b ->
            a."${sortColumn}" <=> b."${sortColumn}"
        }

        if (sortOrder.equalsIgnoreCase('desc')) {
            sortedList.reverse()
        }

        sortedList
    }

    /**
     * Filters a list of domain objects based on the specified filter criteria.
     *
     * @param domainClass The class of the domain objects.
     * @param list The list of domain objects to filter.
     * @param filterCriteria The criteria to filter by.
     * @return A filtered list of domain objects.
     */
    @TypeChecked(TypeCheckingMode.SKIP)
    List filterList(Class domainClass, List list, Map filterCriteria) {
        if (!list) {
            throw new IllegalArgumentException('List cannot be null.')
        }
        if (!filterCriteria) {
            throw new IllegalArgumentException('Filter criteria cannot be null.')
        }

        List filteredList = list.findAll {
            filterCriteria.every { key, value ->
                it."${key}" == value
            }
        }

        filteredList
    }

    /**
     * Combines sorting and filtering functionality to process a list of domain objects.
     *
     * @param domainClass The class of the domain objects.
     * @param list The list of domain objects to process.
     * @param sortColumn The column to sort by.
     * @param sortOrder The direction of the sort.
     * @param filterCriteria The criteria to filter by.
     * @return A processed list of domain objects.
     */
    List processList(Class domainClass, List list, String sortColumn, String sortOrder, Map filterCriteria) {
        List filteredList = filterList(domainClass, list, filterCriteria)
        if (sortColumn && sortOrder) {
            return sortList(domainClass, filteredList, sortColumn, sortOrder)
        }

        filteredList
    }
}
