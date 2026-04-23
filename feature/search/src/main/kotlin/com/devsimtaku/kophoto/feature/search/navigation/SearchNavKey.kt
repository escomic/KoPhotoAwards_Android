package com.devsimtaku.kophoto.feature.search.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data class SearchNavKey(
    val query: String? = null
) : NavKey
