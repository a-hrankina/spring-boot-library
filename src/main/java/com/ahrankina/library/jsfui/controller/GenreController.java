package com.ahrankina.library.jsfui.controller;

import com.ahrankina.library.dao.GenreDao;
import com.ahrankina.library.domain.Genre;
import com.ahrankina.library.jsfui.model.LazyDataTable;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.List;

@ManagedBean
@SessionScoped
@Component
@Getter
@Setter
public class GenreController extends AbstractController<Genre> {
    private int rowsCount = 20;
    private int first;

    @Autowired
    private GenreDao genreDao;
    private Genre selectedGenre;
    private LazyDataTable<Genre> lazyModel;
    private Page<Genre> genrePages;

    @PostConstruct
    public void init() {
        lazyModel = new LazyDataTable(this);
    }

    @Override
    public Page<Genre> search(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection) {
        return genrePages;
    }

    @Override
    public void addAction() {

    }

    @Override
    public void editAction() {

    }

    @Override
    public void deleteAction() {

    }

    public List<Genre> find(String name) {
        return genreDao.search(name);
    }

    public void save() {
        genreDao.save(selectedGenre);
        RequestContext.getCurrentInstance().execute("PF('dialogGenre').hide()");
    }

    public List<Genre> getAll() {
        return genreDao.getAll(Sort.by(Sort.Direction.ASC, "name"));
    }

}
