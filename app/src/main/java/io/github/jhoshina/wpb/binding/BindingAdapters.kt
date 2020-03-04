package io.github.jhoshina.wpb.binding

import androidx.databinding.BindingAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("imageUrl")
fun ImageView.bindImageUrl(url: String?) {
    val reqUrl = url ?: "https://dummyimage.com/320/cccccc/ffffff.jpg&text=NO+IMAGE"
    val metrics = context.resources.displayMetrics
    val width = (120 * metrics.density).toInt()
    val height = (120 * metrics.density).toInt()
    val option = RequestOptions()
        .centerCrop()
        .override(width, height)
//        .error(R.drawable.icon)
    Glide.with(context)
        .load(reqUrl)
        .apply(option)
        .into(this)
}

@BindingAdapter("dateText")
fun TextView.dateText(date: Date) {
    text = SimpleDateFormat("MM/dd HH:mm", Locale.JAPAN).format(date)
}
