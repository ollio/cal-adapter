package net.bozan.ical.caladapter

import net.fortuna.ical4j.data.CalendarBuilder
import net.fortuna.ical4j.model.Calendar
import org.springframework.stereotype.Component

@Component
class ICalClient {
  CalendarBuilder builder = new CalendarBuilder()

  Calendar readCalendar(icalId) {
    builder.build(URI.create("https://app.holaspirit.com/api/ics/$icalId")
        .toURL()
        .openConnection()
        .inputStream)
  }
}
