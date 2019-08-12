package com.example.common.state

import com.example.common.models.*

data class MoviesState(
    var movies: MutableMap<Int, Movie> = mutableMapOf(),
    var moviesList: MutableMap<MoviesMenu, List<Int>> = mutableMapOf(),
    var recommended: MutableMap<Int, List<Int>> = mutableMapOf(),
    var similar: MutableMap<Int, List<Int>> = mutableMapOf(),
    var search: MutableMap<String, MutableList<Int>> = mutableMapOf(),
    var searchKeywords: MutableMap<String, List<Keyword>> = mutableMapOf(),
    var recentSearches: Set<String> = setOf(),
    var moviesUserMeta: MutableMap<Int, MovieUserMeta> = mutableMapOf(),
    var discover: MutableList<Int> = mutableListOf(),
    var discoverFilter: DiscoverFilter? = null,
    var savedDiscoverFilters: MutableList<DiscoverFilter> = mutableListOf(),
    var wishlist: Set<Int> = setOf(),
    var seenlist: Set<Int> = setOf(),
    var withGenre: MutableMap<Int, MutableList<Int>> = mutableMapOf(),
    var withKeywords: MutableMap<Int, MutableList<Int>> = mutableMapOf(),
    var withCrew: MutableMap<Int, List<Int>> = mutableMapOf(),
    var reviews: MutableMap<Int, List<Review>> = mutableMapOf(),
    var customLists: MutableMap<Int, CustomList> = mutableMapOf(),
    var genres: MutableList<Genre> = mutableListOf()
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