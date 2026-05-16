package com.example.neodinary_hackaton.global.common.paging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> {

    private List<T> items;
    private PageInfo pageInfo;

    public static <T> PageResponse<T> of(List<T> items, Page<?> page) {
        return PageResponse.<T>builder()
                .items(items)
                .pageInfo(PageInfo.fromOffset(page))
                .build();
    }
}
