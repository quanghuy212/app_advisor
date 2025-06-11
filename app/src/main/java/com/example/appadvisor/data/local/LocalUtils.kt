package com.example.appadvisor.data.local

import android.content.Context
import android.content.res.Configuration
import java.util.Locale

object LocaleUtils {
    fun updateLocale(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        config.setLayoutDirection(locale)

        return context.createConfigurationContext(config)
    }
}
