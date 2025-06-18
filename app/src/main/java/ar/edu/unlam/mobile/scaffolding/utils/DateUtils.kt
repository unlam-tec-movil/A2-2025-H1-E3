package ar.edu.unlam.mobile.scaffolding.utils

import java.text.SimpleDateFormat
import java.util.Locale

// fun formatFriendlyDate(isoDate: String?): String =
//    try {
//        val formatter = DateTimeFormatter.ISO_DATE_TIME
//        val dateTime = LocalDateTime.parse(isoDate, formatter)
//
//        val outputFormatter =
//            DateTimeFormatter.ofPattern(
//                "d 'de' MMMM 'de' yyyy 'a las' HH:mm",
//                Locale("es"),
//            )
//        dateTime.format(outputFormatter)
//    } catch (e: Exception) {
//        "Fecha inválida"
//    }

// fun formatFriendlyDate(isoDate: String?): String {
//    return try {
//        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
//        val date = parser.parse(isoDate ?: "") ?: return "Fecha inválida"
//        val formatter = SimpleDateFormat("d 'de' MMMM 'de' yyyy 'a las' HH:mm", Locale("es"))
//        formatter.format(date)
//    } catch (e: Exception) {
//        "Fecha inválida"
//    }
// }

fun formatFriendlyDate(isoDate: String?): String {
    return try {
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val date = parser.parse(isoDate ?: "") ?: return "Fecha inválida"
        val formatter = SimpleDateFormat("d MMM yyyy, HH:mm", Locale("es"))
        formatter.format(date)
    } catch (e: Exception) {
        "Fecha inválida"
    }
}
