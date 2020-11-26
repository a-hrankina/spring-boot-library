package com.ahrankina.library.jsfui.converter;

import com.ahrankina.library.dao.PublisherDao;
import com.ahrankina.library.domain.Publisher;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = Publisher.class)
@Component
public class PublisherConverter implements Converter {

    @Autowired
    private PublisherDao publisherDao;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (Strings.isNullOrEmpty(value)) {
            return null;
        }
        return publisherDao.get(Integer.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return null;
        }
        return ((Publisher) value).getId().toString();
    }
}
