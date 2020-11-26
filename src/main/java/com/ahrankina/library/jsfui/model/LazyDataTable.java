package com.ahrankina.library.jsfui.model;

import com.ahrankina.library.jsfui.controller.AbstractController;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class LazyDataTable<T> extends LazyDataModel<T> {

    private AbstractController<T> abstractController;

    public LazyDataTable(AbstractController<T> abstractController) {
        this.abstractController = abstractController;
    }

    @Override
    public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        int pageNumber = first / pageSize;

        Sort.Direction sortDirection = Sort.Direction.ASC;

        if (sortOrder != null) {
            switch (sortOrder) {
                case DESCENDING:
                    sortDirection = Sort.Direction.DESC;
                    break;
            }
        }

        Page<T> searchResult = abstractController.search(pageNumber, pageSize, sortField, sortDirection);

        this.setRowCount((int) searchResult.getTotalElements());

        return searchResult.getContent();
    }

}


