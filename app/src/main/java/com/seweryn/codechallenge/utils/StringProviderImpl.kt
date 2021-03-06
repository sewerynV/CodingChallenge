package com.seweryn.codechallenge.utils

import android.content.res.Resources

class StringProviderImpl(private val resources: Resources) : StringProvider {
    override fun getString(stringResId: Int): String = resources.getString(stringResId)
}