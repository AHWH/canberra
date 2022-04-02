package com.sg.slightlyred.canberra.service.adapter

import com.sg.slightlyred.canberra.constants.AppConstants
import org.junit.Assert.*
import org.junit.Test
import java.time.LocalTime

class LocalTimeAdapterTest {
    @Test
    fun name() {
        assertNotNull(LocalTime.parse("0000", AppConstants.LTA_DATAMALL_TIME_FORMATTER))
    }
}