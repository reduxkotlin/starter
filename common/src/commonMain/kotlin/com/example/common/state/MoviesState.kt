package com.example.common.state

import com.example.common.models.*

data class MoviesState(
    var movies: Map<Int, Movie> = mapOf(),
    var moviesList: Map<MoviesMenu, List<Int>> = mapOf(),
    var recommended: Map<Int, List<Int>> = mapOf(),
    var similar: Map<Int, List<Int>> = mapOf(),
    var search: Map<String, List<Int>> = mapOf(),
    var searchKeywords: Map<String, List<Keyword>> = mapOf(),
    var recentSearches: Set<String> = setOf(),
    var moviesUserMeta: Map<Int, MovieUserMeta> = mapOf(),
    var discover: List<Int> = listOf(),
    var discoverFilter: DiscoverFilter? = null,
    var savedDiscoverFilters: List<DiscoverFilter> = listOf(),
    var wishlist: Set<Int> = setOf(),
    var seenlist: Set<Int> = setOf(),
    var withGenre: Map<Int, List<Int>> = mapOf(),
    var withKeywords: Map<Int, List<Int>> = mapOf(),
    var withCrew: Map<Int, List<Int>> = mapOf(),
    var reviews: Map<Int, List<Review>> = mapOf(),
    var customLists: Map<Int, CustomList> = mapOf(),
    var genres: List<Genre> = listOf()
) {
    enum class CodingKeys(val rawValue: String) {
        movies("movies"),
        wishlist("wishlist"),
        seenlist("seenlist"),
        customLists("customLists"),
        moviesUserMeta(
            "moviesUserMeta"
        ),
        savedDiscoverFilters("savedDiscoverFilters");

        companion object {
            operator fun invoke(rawValue: String) =
                CodingKeys.values().firstOrNull { it.rawValue == rawValue }
        }
    }
}