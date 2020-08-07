package com.alessandro.co2chart.ui.chart

import android.content.Context
import android.graphics.Typeface
import android.text.SpannableString
import android.text.style.StyleSpan
import com.alessandro.co2chart.R
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import kotlinx.android.synthetic.main.marker_co2_data.view.*
import java.util.*

class Co2MarkerView(context: Context) : MarkerView(context, R.layout.marker_co2_data) {

    override fun refreshContent(entry: Entry?, highlight: Highlight?) {
        entry?.let { validEntry ->
            val co2 = "${validEntry.y.toInt()} ppm"

            val calendar = Calendar.getInstance()
            calendar.timeInMillis = validEntry.x.toLong()
            val date = getFormattedDate(calendar)

            val text = "$co2 - $date"
            val spannableString = SpannableString(text)
            spannableString.setSpan(
                StyleSpan(Typeface.BOLD),
                text.indexOf(co2),
                co2.length,
                SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            co2MarkerTextView.text = spannableString
        }
        super.refreshContent(entry, highlight)
    }

    override fun getOffset(): MPPointF {
        return MPPointF(-width.toFloat(), -height.toFloat())
    }

    private fun getFormattedDate(calendar: Calendar): String {
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = String.format("%02d", calendar.get(Calendar.MINUTE))
        return "$day $month, $hour:$minute"
    }
}
