package com.ahrankina.library.jsfui.locale;

import com.ahrankina.library.jsfui.util.CookieHelper;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.Locale;

@ManagedBean(eager = true)
@SessionScoped
public class LocaleChanger implements Serializable {
    private Locale currentLocale = new Locale("ru");

    public LocaleChanger() {
        if (CookieHelper.getCookie(CookieHelper.COOKIE_LANG) == null) {
            return;
        }

        String cookieLang = CookieHelper.getCookie(CookieHelper.COOKIE_LANG).getValue();
        if (cookieLang != null) {
            currentLocale = new Locale(cookieLang);
        }
    }

    public void changeLocale(String localeCode) {
        currentLocale = new Locale(localeCode);
        CookieHelper.setCookie(CookieHelper.COOKIE_LANG, currentLocale.getLanguage(), 3600);

    }

    public Locale getCurrentLocale() {
        return currentLocale;
    }
}
