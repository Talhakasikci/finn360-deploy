package com.talhakasikci.finn360fe.utils

import android.media.MediaCodec
import android.util.Patterns
import java.util.regex.Pattern

fun isValidEmail(email: String): Boolean{
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}