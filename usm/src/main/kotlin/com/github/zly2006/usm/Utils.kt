package com.github.zly2006.usm

import java.util.*

fun <A : Any, B> Collection<Pair<A, B>>.toMultiMap() =
    fold(mutableMapOf<A, MutableList<B>>()) { acc, (a, b) ->
        acc.getOrPut(a) { mutableListOf() }.add(b)
        acc
    }

fun String.toUUID(): UUID = UUID.fromString(this)
