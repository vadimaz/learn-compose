package com.blogspot.vadim.navigation.internal

import com.blogspot.vadim.navigation.ScreenResponseReceiver
import kotlin.reflect.KClass

internal object EmptyScreenResponseReceiver: ScreenResponseReceiver {
    override fun <T : Any> get(clazz: KClass<T>): T? = null
}