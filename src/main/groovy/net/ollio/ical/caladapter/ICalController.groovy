package net.ollio.ical.caladapter

import groovy.util.logging.Log
import net.fortuna.ical4j.data.CalendarOutputter
import net.fortuna.ical4j.model.Calendar
import net.fortuna.ical4j.model.property.CalScale
import net.fortuna.ical4j.model.property.Method
import net.fortuna.ical4j.model.property.ProdId
import net.fortuna.ical4j.model.property.Version
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

import javax.servlet.http.HttpServletResponse

@Log
@RestController
class ICalController {

  @Autowired
  ICalClient client

  @GetMapping(['/4outlook/{id}', '/4outlook/{id}.ical'])
  void getOutlookICal(@PathVariable String id, HttpServletResponse response) {
    log.info("Call ical ID: $id")

    Calendar calendar = new Calendar()
    calendar.properties.add(new ProdId("-//ollio//cal-adapter-0.0.2//EN"))
    calendar.properties.add(Version.VERSION_2_0)
    calendar.properties.add(CalScale.GREGORIAN)
    calendar.properties.add(Method.PUBLISH)

    client.readCalendar(id).components.each {
      calendar.components.add(it)
    }

    CalendarOutputter outputter = new CalendarOutputter()
    outputter.output(calendar, response.getOutputStream())
  }

}
