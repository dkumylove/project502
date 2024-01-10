package org.choongang.commons;


import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class Pagination {

    private int page;  // 현재 페이지
    private int total;  // 전체 레코드 갯수
    private int ranges;  // 페이지 구간 갯수
    private int limit;

    private int firstRangePage; // 구간별 첫 페이지
    private int lastRangePage;  // 구간별 마지막 페이지

    private int prevRangePage;  // 이전구간 첫페이지 번호
    private int nextRangePage;  // 다음구간 첫페이지 번호

    private int totalPages;  // 전체 페이지 갯수
    private String baesURL;  // 페이징 쿼리스트링 기본 URL

    /**
     * 페이징
     * @param page : 현재 페이지
     * @param total : 전체 레코드 갯수
     * @param ranges : 페이지 구간 갯수
     * @param limit : 1페이지당 레코드 갯수
     * @param request
     */
    public Pagination(int page, int total, int ranges, int limit, HttpServletRequest request) {

        page = Utils.onlyPostitiveNumber(page, 1);
        total = Utils.onlyPostitiveNumber(total, 0);
        ranges = Utils.onlyPostitiveNumber(ranges, 10);
        limit = Utils.onlyPostitiveNumber(limit, 20);

        // 전체 페이지 갯수(진짜끝페이지) =
        // 올림처리(전체 레코드 갯수/ (더블)1페이지당 레코드 갯수)
        int totalPages = (int) Math.ceil(total / (double)limit);

        // 구간 번호
        int rangeCnt = (page - 1) / ranges;
        // 시작번호 (구간번호 * 페이지구간 갯수 + 1)
        int firstRangePage = rangeCnt * ranges + 1;
        // 마지막번호 (시작번호 + 페이지구간 갯수 - 1)
        int lastRangePage = firstRangePage + ranges - 1;

        lastRangePage = lastRangePage > totalPages ? totalPages : lastRangePage;

        // 이전구간 첫 페이지
        if (rangeCnt > 0) {
            // 이전구간 첫 페이지 = 시작번호 - 페이지구간 갯수
            prevRangePage = firstRangePage - ranges;
        }

        // 마지막 구간 번호
        int lastRangeCnt = (totalPages - 1) / ranges;
        // 다음구간 첫 페이지
        if(rangeCnt < lastRangeCnt) {  // 마지막 구간이 아닌 경우 다음 구간 첫 페이지 계싼
            nextRangePage = firstRangePage + ranges;
        }

        /**
         * 쿼리스트링 값 유지 처리 - 쿼리스트링 값 중 page만 제외하고 다시조합
         * ex) ?orderStatus=CASH&name=....&age=2 ->
         *
         * &로 문자열 분리
         * {"orderStatus=CASH", "name=....", "page=2"}
         */
        if(request !=  null) {
            String queryString = request.getQueryString();
            String baseURL = "?";
            if (StringUtils.hasText(queryString)) {
                queryString = queryString.replace("?", "");
                baseURL += Arrays.stream(queryString.split("&"))
                        .filter(s -> !s.contains("page="))
                        .collect(Collectors.joining("&"));
            }
            this.baesURL = baseURL;
        }

        this.page = page;
        this.total = total;
        this.ranges = ranges;
        this.limit = limit;
        this.firstRangePage = firstRangePage;
        this.lastRangePage = lastRangePage;
        this.totalPages = totalPages;

    }

    public Pagination (int page, int total, int ranges, int limit) {
        this(page, total, ranges, limit, null);
    }

    public List<String[]> getPages() {

        // 0 : 페이지 번호, 1 : 페이지 URL - ?page=페이지번호
        return IntStream.rangeClosed(firstRangePage, lastRangePage)
                .mapToObj(p -> new String[] {String.valueOf(p),
                        baesURL + "page=" + p})
                .toList();
    }
}
