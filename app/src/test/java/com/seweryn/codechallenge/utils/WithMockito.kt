package com.seweryn.codechallenge.utils

import org.mockito.ArgumentMatchers

interface WithMockito {
    fun <T>any(): T = ArgumentMatchers.any<T>()
}