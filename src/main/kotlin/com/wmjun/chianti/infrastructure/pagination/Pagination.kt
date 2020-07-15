package com.wmjun.chianti.infrastructure.pagination

const val DEFAULT_PAGE_SIZE = 15
const val DEFAULT_PAGE = 1

data class Pagination(val currentPage: Int,
                      val pageSize: Int,
                      val totalCount: Int,
                      val hasNext: Boolean) {
    val totalPage: Int
        get() {
            return if (totalCount % pageSize == 0)
                totalCount / pageSize
            else
                (totalCount / pageSize) + 1
        }
}