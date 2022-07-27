package me.mohekkus.olsera_testproject.ui.list

data class StaticDataHome(
    val noFilter: Filter,
    val filter1: Filter,
    val filter2: Filter
)

data class Filter(
    val text: String,
    val selected: Boolean
)