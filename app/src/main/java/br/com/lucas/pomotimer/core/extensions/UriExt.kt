package br.com.lucas.pomotimer.core.extensions

import android.content.ContentResolver
import android.net.Uri

fun Uri?.getName(contentResolver: ContentResolver?, columnIndex: Int): String? = runCatching {
    getName(contentResolver, column = null, columnIndex = columnIndex)
}.getOrNull()

fun Uri?.getName(contentResolver: ContentResolver?, column: String): String? = runCatching {
    getName(contentResolver, column = column, columnIndex = null)
}.getOrNull()

private fun Uri?.getName(
    contentResolver: ContentResolver?,
    column: String? = null,
    columnIndex: Int? = null
): String? = runCatching {
    when (this?.scheme) {
        ContentResolver.SCHEME_CONTENT -> getColumnValue(contentResolver, column, columnIndex)
        else -> null
    }
}.getOrNull()

private fun Uri?.getColumnValue(
    contentResolver: ContentResolver?,
    column: String? = null,
    columnIndex: Int? = null
) = this?.let {
    contentResolver?.query(it, null, null, null, null)
}?.use {
    val index = columnIndex ?: it.getColumnIndex(column)
    it.moveToFirst()
    it.getString(index)
}.orEmpty()