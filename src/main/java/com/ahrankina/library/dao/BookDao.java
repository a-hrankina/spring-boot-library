package com.ahrankina.library.dao;

import com.ahrankina.library.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface BookDao extends GeneralDAO<Book> {

    List<Book> findTopBooks(int limit);

    byte[] getContent(long id);

    Page<Book> findByGenre(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection, long genreId);

    void updateViewCount(long viewCount, long id);

    void updateRating(long totalRating, long totalVoteCount, int avgRating, long id);

}