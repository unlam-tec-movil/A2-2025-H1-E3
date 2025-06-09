package ar.edu.unlam.mobile.scaffolding.utils

import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

fun String.encode(): String = URLEncoder.encode(this, StandardCharsets.UTF_8.toString())

fun String.decode(): String = URLDecoder.decode(this, StandardCharsets.UTF_8.toString())
