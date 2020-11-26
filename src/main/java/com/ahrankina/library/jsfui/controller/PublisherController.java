package com.ahrankina.library.jsfui.controller;

import com.ahrankina.library.dao.PublisherDao;
import com.ahrankina.library.domain.Publisher;
import com.ahrankina.library.jsfui.model.LazyDataTable;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import java.util.List;

@Named
@SessionScoped
@Component
@Getter
@Setter
public class PublisherController extends AbstractController<Publisher> {

    private int rowsCount = 20;
    private int first;
    private Page<Publisher> publisherPages;

    @Autowired
    private PublisherDao publisherDao;
    private Publisher selectedPublisher;
    private LazyDataTable<Publisher> lazyModel;

    @PostConstruct
    public void init() {
        lazyModel = new LazyDataTable(this);

    }

    public void save() {
        publisherDao.save(selectedPublisher);
        RequestContext.getCurrentInstance().execute("PF('dialogPublisher').hide()");
    }

    @Override
    public Page<Publisher> search(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection) {
        return publisherPages;
    }

    @Override
    public void addAction() {
        selectedPublisher = new Publisher();
        showEditDialog();
    }

    @Override
    public void editAction() {
        showEditDialog();
    }

    @Override
    public void deleteAction() {
        publisherDao.delete(selectedPublisher);
    }

    private void showEditDialog() {
        RequestContext.getCurrentInstance().execute("PF('dialogPublisher').show()");
    }

    public List<Publisher> find(String name) {
        return publisherDao.search(name);
    }

}
