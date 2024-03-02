package org.choongang.commons;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * List 와 Pagination
 * @param <T>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListData<T> {
    private List<T> items;
    private Pagination pagination;
}
