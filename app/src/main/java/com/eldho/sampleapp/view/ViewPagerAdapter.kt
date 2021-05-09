package com.eldho.sampleapp.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.eldho.sampleapp.R
import com.eldho.sampleapp.model.ViewItem

class ViewPagerAdapter(private var context: Context, private var itemList: List<ViewItem>) : PagerAdapter() {

    private var mLayoutInflater: LayoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var pos = 0

    override fun getCount(): Int {
        return Integer.MAX_VALUE
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val holder = ViewHolder()
        val itemView = mLayoutInflater.inflate(R.layout.layout_item_view_pager, container, false)
        holder.itemImage = itemView.findViewById(R.id.img_slider) as ImageView

        if (pos > this.itemList.size - 1)
            pos = 0

        holder.sliderItem = this.itemList[pos]

        Glide.with(itemView)
            .load(holder.sliderItem.imageDrawable)
            .centerCrop()
            .into(holder.itemImage)


        (container as ViewPager).addView(itemView)

        holder.itemImage.scaleType = ImageView.ScaleType.CENTER_CROP

        pos++
        return itemView
    }

    internal class ViewHolder {
        lateinit var sliderItem: ViewItem
        lateinit var itemImage: ImageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }

    override fun isViewFromObject(arg0: View, arg1: Any): Boolean {
        return arg0 === arg1 as View
    }

    override fun destroyItem(arg0: View, arg1: Int, arg2: Any) {
        (arg0 as ViewPager).removeView(arg2 as View)
    }
}