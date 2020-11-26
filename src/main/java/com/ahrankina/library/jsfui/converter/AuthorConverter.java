package com.ahrankina.library.jsfui.converter;

import com.ahrankina.library.dao.AuthorDao;
import com.ahrankina.library.domain.Author;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = Author.class)
@Component
public class AuthorConverter implements Converter {

    @Autowired
    private AuthorDao authorDao;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (Strings.isNullOrEmpty(value)) {
            return null;
        }
        return authorDao.get(Integer.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if (value == null) {
            return null;
        }
        return ((Author) value).getId().toString();
    }
}
