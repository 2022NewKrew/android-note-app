package com.survivalcoding.note_app.core.extension

import androidx.fragment.app.Fragment
import com.survivalcoding.note_app.App

val Fragment.app: App
    get() = requireActivity().application as App