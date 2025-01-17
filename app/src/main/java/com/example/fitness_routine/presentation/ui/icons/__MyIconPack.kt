package com.example.fitness_routine.presentation.ui.icons

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.fitness_routine.presentation.ui.icons.myiconpack.Google
import kotlin.collections.List as ____KtList

public object MyIconPack

private var __AllIcons: ____KtList<ImageVector>? = null

public val MyIconPack.AllIcons: ____KtList<ImageVector>
  get() {
    if (__AllIcons != null) {
      return __AllIcons!!
    }
    __AllIcons= listOf(Google)
    return __AllIcons!!
  }
