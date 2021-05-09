package com.eldho.sampleapp.model

import java.io.Serializable

class ViewItem(id: Int, drawable: Int) : Serializable {
    var imageId: Int = id
        internal set
    var imageDrawable: Int = drawable
        internal set

}